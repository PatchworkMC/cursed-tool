/*
 * Patchwork Project
 * Copyright (C) 2019 PatchworkMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.patchworkmc.jobs.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.patchworkmc.jobs.JobPipeline;
import com.patchworkmc.jobs.ValueConnection;
import com.patchworkmc.jobs.ValuePool;
import com.patchworkmc.jobs.meta.JobConnectionMeta;
import com.patchworkmc.jobs.meta.JobInputCreator;
import com.patchworkmc.jobs.meta.JobMeta;
import com.patchworkmc.jobs.meta.JobRegistry;
import com.patchworkmc.jobs.meta.MetaBindException;
import com.patchworkmc.jobs.parser.token.BooleanConstant;
import com.patchworkmc.jobs.parser.token.ClosingBrace;
import com.patchworkmc.jobs.parser.token.DoKeyword;
import com.patchworkmc.jobs.parser.token.DoubleNumeral;
import com.patchworkmc.jobs.parser.token.EmitKeyword;
import com.patchworkmc.jobs.parser.token.EqualsSign;
import com.patchworkmc.jobs.parser.token.Identifier;
import com.patchworkmc.jobs.parser.token.JobDefinitionToken;
import com.patchworkmc.jobs.parser.token.Numeral;
import com.patchworkmc.jobs.parser.token.OpeningBrace;
import com.patchworkmc.jobs.parser.token.Semicolon;
import com.patchworkmc.jobs.parser.token.StringLiteral;
import com.patchworkmc.jobs.parser.token.WithKeyword;

@SuppressWarnings("unchecked")
public class JobPipelineBuilder {
	private final List<JobDefinitionToken> tokens;
	private final JobRegistry registry;
	private final Map<String, ValueConnection> variables;

	private int tokenIndex;

	public JobPipelineBuilder(List<JobDefinitionToken> tokens, JobRegistry registry) {
		this.tokens = tokens;
		this.registry = registry;
		this.variables = new HashMap<>();
		this.tokenIndex = 0;
	}

	public JobPipeline build() throws JobPipelineDefinitionParseException {
		JobPipeline pipeline = new JobPipeline();

		while (tokenIndex < tokens.size()) {
			pipeline.addJob(buildJob());
		}

		return pipeline;
	}

	private JobConnectionMeta buildJob() throws JobPipelineDefinitionParseException {
		JobConnectionMeta connectionMeta = new JobConnectionMeta();

		JobDefinitionToken doOrWith = expectToken("Expected with or do keyword",
				WithKeyword.class, DoKeyword.class);

		Map<String, ValueConnection> inputs;

		if (doOrWith instanceof WithKeyword) {
			inputs = buildInputs();
			expectToken("Expected do keyword", DoKeyword.class);

			inputs.forEach((k, v) -> connectionMeta.addInput(k, v.getUsages().get(v.getUsages().size() - 1)));
		} else {
			inputs = new HashMap<>();
		}

		StringLiteral job = expectToken("Expected string literal containing job name", StringLiteral.class);
		String jobName = job.content();

		if (!registry.isRegistered(jobName)) {
			throw new JobPipelineDefinitionParseException(job.startPosition(), job.endPosition(),
					"No job " + jobName + " registered");
		}

		JobMeta meta = registry.get(jobName);
		Supplier<JobInputCreator> jobInputCreatorSupplier;

		try {
			jobInputCreatorSupplier = meta.makeCreatorSupplier(inputs);
		} catch (MetaBindException e) {
			throw new JobPipelineDefinitionParseException(
					doOrWith.startPosition(), job.endPosition(), "Invalid inputs given", e);
		}

		Map<String, ValueConnection> staticConnections = inputs
				.entrySet()
				.stream()
				.filter(e -> e.getValue().isStatic())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		ValuePool jobPool = new ValuePool(meta.job(), jobInputCreatorSupplier, staticConnections);
		connectionMeta.setValuePool(jobPool);

		inputs
				.entrySet()
				.stream()
				.filter(e -> !e.getValue().isStatic())
				.forEach(e -> e.getValue().addConsumer((v) -> jobPool.supply(e.getKey(), v)));

		JobDefinitionToken semicolonOrEmit = expectToken("Expected keyword emit or semicolon",
				EmitKeyword.class, Semicolon.class);

		if (!(semicolonOrEmit instanceof Semicolon)) {
			Identifier output = expectToken("Expected name of output", Identifier.class);
			String outputName = output.name();

			ValueConnection outputConnection;

			if (variables.containsKey(outputName)) {
				outputConnection = variables.get(outputName);

				if (outputConnection.getValueType().isAssignableFrom(meta.job().outputType())) {
					throw new JobPipelineDefinitionParseException(output.startPosition(), output.endPosition(),
							"Output type " + meta.job().outputType().getName()
									+ " is not assignable to output type " + outputConnection.getValueType().getName()
									+ " which has already been declared");
				}
			} else {
				outputConnection = new ValueConnection(meta.job().outputType(), output);
				variables.put(outputName, outputConnection);
			}

			jobPool.onEmit(outputConnection::supply);

			connectionMeta.setOutput(output);

			expectToken("Expected semicolon", Semicolon.class);
		}

		return connectionMeta;
	}

	private Map<String, ValueConnection> buildInputs() throws JobPipelineDefinitionParseException {
		JobDefinitionToken token = expectToken(
				"Expected either opening brace for input block or identifier for single input",
				OpeningBrace.class, Identifier.class);

		Map<String, ValueConnection> inputs = new HashMap<>();

		if (token instanceof OpeningBrace) {
			while (true) {
				Identifier inputName = expectToken("Expected name of input", Identifier.class);
				expectToken("Expected equals sign", EqualsSign.class);

				JobDefinitionToken valueToken = expectToken(
						"Expected identifier, string literal, numeral or boolean constant",
						Identifier.class,
						StringLiteral.class,
						Numeral.class,
						DoubleNumeral.class,
						BooleanConstant.class);

				ValueConnection connection;

				if (valueToken instanceof Identifier) {
					String name = ((Identifier) valueToken).name();

					if (!variables.containsKey(name)) {
						throw new JobPipelineDefinitionParseException(
								valueToken.startPosition(), valueToken.endPosition(),
								"Use of undeclared variable " + name);
					}

					connection = variables.get(name);
					connection.getUsages().add(valueToken);
				} else if (valueToken instanceof StringLiteral) {
					connection = new ValueConnection(String.class, valueToken, ((StringLiteral) valueToken)::content);
				} else if (valueToken instanceof Numeral) {
					connection = new ValueConnection(long.class, valueToken, ((Numeral) valueToken)::value);
				} else if (valueToken instanceof DoubleNumeral) {
					connection = new ValueConnection(double.class, valueToken, ((DoubleNumeral) valueToken)::value);
				} else if (valueToken instanceof BooleanConstant) {
					connection = new ValueConnection(boolean.class, valueToken, ((BooleanConstant) valueToken)::value);
				} else {
					throw new AssertionError("UNREACHABLE");
				}

				inputs.put(inputName.name(), connection);
				expectToken("Expected semicolon to finish input assignment", Semicolon.class);

				if (isNextToken(ClosingBrace.class)) {
					tokenIndex++;
					break;
				}
			}
		} else {
			Identifier variableName = expectToken("Expected input variable name", Identifier.class);
			String name = variableName.name();

			if (!variables.containsKey(name)) {
				throw new JobPipelineDefinitionParseException(
						variableName.startPosition(), variableName.endPosition(), "Use of undeclared variable " + name);
			}

			return Collections.singletonMap("this", variables.get(name));
		}

		return Collections.unmodifiableMap(inputs);
	}

	private <T extends JobDefinitionToken> T expectToken(String message, Class<T> clazz) throws JobPipelineDefinitionParseException {
		JobDefinitionToken token = tokens.get(tokenIndex++);

		if (!clazz.isAssignableFrom(token.getClass())) {
			throw new JobPipelineDefinitionParseException(token.startPosition(), token.endPosition(), message);
		}

		return clazz.cast(token);
	}

	private JobDefinitionToken expectToken(String message, Class<? extends JobDefinitionToken>... classes) throws JobPipelineDefinitionParseException {
		JobDefinitionToken token = tokens.get(tokenIndex++);

		for (Class<? extends JobDefinitionToken> clazz : classes) {
			if (clazz.isAssignableFrom(token.getClass())) {
				return clazz.cast(token);
			}
		}

		throw new JobPipelineDefinitionParseException(token.startPosition(), token.endPosition(), message);
	}

	private boolean isNextToken(Class<? extends JobDefinitionToken> clazz) {
		return clazz.isAssignableFrom(tokens.get(tokenIndex).getClass());
	}
}
