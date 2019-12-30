package com.patchworkmc.cursed;

import java.io.File;
import java.net.URL;

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

public class CursedTool {
	public static void main(String[] args) {
		AnsiConsole.systemInstall();

		Logger logger = new Logger("CursedTool");
		CommandlineParser<Commandline> commandlineParser = new CommandlineParser<>(new Commandline(), args);
		Commandline commandline = null;

		try {
			commandline = commandlineParser.parse();
			logger.setWriter(new StreamWriter(!commandline.noColor, System.out, System.err), commandline.logLevel);
		} catch (CommandlineException e) {
			logger.setWriter(new StreamWriter(false, System.out, System.err), LogLevel.TRACE);
			logger.fatal("Error while parsing commandline!");
			logger.thrown(LogLevel.FATAL, e);
			System.exit(1);
		}

		if (!commandlineParser.parseSucceeded() || commandline.help) {
			System.out.println(commandlineParser.generateHelpMessage(
					getExecutableName(logger),
					"Cursed Tool v0.1.0",
					"Tool for interacting with the CurseApi to download Minecraft mods",
					"TOOL IS IN EARLY ALPHA STATE!",
					!commandline.noColor));
			System.exit(commandline.help ? 0 : 1);
		}

		logger.info("CursedTool v0.1.0");

		HttpClient httpClient = new HttpClient();
		TaskScheduler taskScheduler = new TaskScheduler(logger.sub("Task Scheduler"), commandline.workers);
		CurseApi curseAPI = new CurseApi(httpClient);

		taskScheduler.start();

		final Commandline fCommandline = commandline;

		CursedIndex.INSTANCE.setIndexDir(new File(fCommandline.indexDir));
		CursedIndex.INSTANCE.init(logger.sub("CursedIndex"), curseAPI, taskScheduler)
				.then(() -> {
					logger.info("Index initialized!");
					taskScheduler.expandCurrentOperation(
							CursedIndex.INSTANCE.downloadLatestAddons(
									fCommandline.batchSize,
									fCommandline.limit,
									fCommandline.gameVersion
							)
					);
				})
				.except((e) -> {
					logger.fatal("Error while initializing index!");
					logger.thrown(LogLevel.FATAL, e);
				})
				.then(() -> {
					logger.info("Shutting down");
					taskScheduler.shutdown();
				});
	}

	private static String getExecutableName(Logger logger) {
		try {
			URL location = CursedTool.class.getProtectionDomain().getCodeSource().getLocation();

			if (location.getProtocol().equals("file")) {
				return location.getPath();
			}
		} catch (Exception e) {
			logger.debug("Failed to get executable name");
			logger.thrown(LogLevel.DEBUG, e);
		}

		return "/path/to/cursed-tool.jar";
	}

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
