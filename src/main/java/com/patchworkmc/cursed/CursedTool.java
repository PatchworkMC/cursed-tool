/*
 * Patchwork Project
 * Copyright (C) 2019 PatchworkMC and contributors
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.patchworkmc.cursed;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.fusesource.jansi.AnsiConsole;

import com.patchworkmc.commandline.CommandlineException;
import com.patchworkmc.commandline.CommandlineParser;
import com.patchworkmc.commandline.Flag;
import com.patchworkmc.commandline.Parameter;
import com.patchworkmc.curse.CurseApi;
import com.patchworkmc.logging.LogLevel;
import com.patchworkmc.logging.Logger;
import com.patchworkmc.logging.writer.StreamWriter;
import com.patchworkmc.network.http.HttpClient;
import com.patchworkmc.task.TaskScheduler;
import com.patchworkmc.task.TaskTracker;

public class CursedTool {
	public static void main(String[] args) {
		// Make sure we have colors available on the console
		AnsiConsole.systemInstall();

		/* Set up the things we need before we have parsed the commandline,
		 * that is:
		 * - a logger in case the commandline parser fails hard
		 * - the commandline parser itself
		 */
		Logger logger = new Logger("CursedTool");
		CommandlineParser<Commandline> commandlineParser = new CommandlineParser<>(new Commandline(), args);
		Commandline commandline = null; // needed to capture the commandline outside the try block

		try {
			// Try to parse the commandline
			commandline = commandlineParser.parse();
			// and apply the logger based on the level specified on the commandline
			logger.setWriter(new StreamWriter(!commandline.noColor, System.out, System.err), commandline.logLevel);
		} catch (CommandlineException e) {
			// Something went fatally wrong while parsing the commandline,
			// this does NOT include bad arguments
			logger.setWriter(new StreamWriter(false, System.out, System.err), LogLevel.TRACE);
			logger.fatal("Error while parsing commandline!");
			logger.thrown(LogLevel.FATAL, e);
			System.exit(1);
		}

		if (!commandlineParser.parseSucceeded() || commandline.help) {
			// The user inputted wrong data or specified the help flag
			System.out.println(commandlineParser.generateHelpMessage(
					getExecutableName(logger),
					"Cursed Tool v0.1.0",
					"Tool for interacting with the CurseApi to download Minecraft mods",
					"TOOL IS IN EARLY ALPHA STATE!",
					!commandline.help, // Don't append a possible parser error if the help flag was specified
					!commandline.noColor));

			// Depending on wether the user specified the help flag, exit with 0 or 1
			System.exit(commandline.help ? 0 : 1);
		}

		logger.info("CursedTool v0.1.0");

		// Set up the things we need
		HttpClient httpClient = new HttpClient();
		TaskScheduler taskScheduler = new TaskScheduler(logger.sub("Task Scheduler"), commandline.workers);
		CurseApi curseAPI = new CurseApi(httpClient);

		// If we came this far, install a shutdown hook so we can clean up resources on exit
		Runtime.getRuntime().addShutdownHook(new Thread(
				() -> CursedTool.shutdownHook(logger, taskScheduler), "ShutdownHook"));

		// We first need to start the task scheduler, so that
		// it begins processing
		taskScheduler.start();

		// Make the compiler happy by creating a final
		// version of the commandline, else we can't use it
		// in the lambda
		final Commandline fCommandline = commandline;

		// Prepare the CursedIndex by setting the directory of it
		CursedIndex.INSTANCE.setIndexDir(new File(fCommandline.indexDir));

		AtomicInteger exitCode = new AtomicInteger(0);

		TaskTracker allDoneTracker = new TaskTracker(taskScheduler);
		// And initialize it
		CursedIndex.INSTANCE.init(logger.sub("CursedIndex"), curseAPI, taskScheduler).arm().then(() -> {
			logger.info("Init done!");
			allDoneTracker.track(CursedIndex.INSTANCE.downloadLatestAddons(
					fCommandline.batchSize, fCommandline.limit, fCommandline.gameVersion)).arm();
		});

		allDoneTracker.then(() -> {
			logger.info("All done!");
			exitCode.set(0);
		}).except(
				(e) -> {
					logger.fatal("Error in main!");
					exitCode.set(1);
				}).then(
				// Exit on a separate thread to not hang the scheduler
				() -> new Thread(() -> System.exit(exitCode.get())).start());
	}

	/**
	 * Shutdown hook executed by the JVM. This should be
	 * registered using {@link Runtime#addShutdownHook(Thread)}
	 *
	 * @param logger    The logger to use during shutdown logging
	 * @param scheduler The scheduler to shutdown
	 */
	private static void shutdownHook(Logger logger, TaskScheduler scheduler) {
		logger.trace("Cleaning up resources");

		try {
			// Shut down the scheduler
			scheduler.shutdown();

			// Give the scheduler 10 seconds to stop, usually this is enough
			if (!scheduler.awaitShutdown(10, TimeUnit.SECONDS)) {
				logger.warn("Scheduler did not terminate properly within 10 seconds, forcing shutdown!");
				scheduler.forceShutdown();

				// It is possible that the scheduler is still running if for some
				// reason the JVM decides to lock up a hanging thread. In case that
				// happens, there is no proper way to shut down the JVM
				if (scheduler.isRunning()) {
					logger.error(
							"Scheduler is running even after forcing shutdown, you may need to kill this process!");
				}
			}
		} catch (InterruptedException e) {
			logger.error("Interrupted while waiting for scheduler to shutdown!");
			logger.thrown(LogLevel.ERROR, e);
		}
	}

	/**
	 * Retrieve the full path to the jar, or "/path/to/cursed-tool.jar" if
	 * an error occurs. This is just for cosmetic reasons included in the
	 * commandline parser help message.
	 *
	 * <p><b>DO NOT RELY IN THIS TO RETURN ANYTHING USEFUL!</b>
	 *
	 * @param logger The logger to use to log if an exception occurs.
	 *               Due to the fact, that this is only a cosmetic thing,
	 *               the log level will just be {@link LogLevel#DEBUG}
	 * @return The path to the executable which can be displayed to the user
	 */
	private static String getExecutableName(Logger logger) {
		try {
			// Use the CursedTool class location as reference
			URL location = CursedTool.class.getProtectionDomain().getCodeSource().getLocation();

			// Not sure if anyone would launch this from... UH?
			if (location.getProtocol().equals("file")) {
				return location.getPath();
			}
		} catch (Exception e) {
			logger.debug("Failed to get executable name");
			logger.thrown(LogLevel.DEBUG, e);
		}

		// Just return a dummy string, lets hope the user is not
		// as dumb as this string and understands it :)
		return "/path/to/cursed-tool.jar";
	}

	/**
	 * Commandline requested from the user.
	 */
	static class Commandline {
		@Flag(names = "help", description = "Displays this message")
		public boolean help;

		@Flag(names = {"b", "batch-size"}, description = "Amount of mods to request from curse at once")
		public int batchSize = 200;

		@Flag(names = "no-color", description = "Disable colorful logging output")
		public boolean noColor;

		@Flag(names = "log-level", description = "Log level to use")
		public LogLevel logLevel = LogLevel.INFO;

		@Flag(names = "workers", description = "Amount of threads to use (default is amount of CPU cores divided by 2)")
		public int workers = Math.max(Runtime.getRuntime().availableProcessors() / 2, 1);

		@Flag(names = "limit", description = "Maximum amount of addons to download")
		public int limit;

		@Flag(names = {"d", "index-dir"}, description = "Path to the directory to store addons in (defaults to addons)")
		public String indexDir = "addons";

		@Parameter(
				position = 0,
				name = "game version",
				description = "The minecraft version for which mods should be searched"
		)
		public String gameVersion;
	}
}
