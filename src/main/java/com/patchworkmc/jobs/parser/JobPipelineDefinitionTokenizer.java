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

import java.util.ArrayList;
import java.util.List;

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

public class JobPipelineDefinitionTokenizer {
	private final String text;
	private final FilePosition position;
	private final StringBuilder currentTokenText;

	private int offset;

	public JobPipelineDefinitionTokenizer(String filename, String text) {
		this.text = text;
		this.position = new FilePosition(filename);
		this.currentTokenText = new StringBuilder();
	}

	public List<JobDefinitionToken> parse() throws JobPipelineDefinitionParseException {
		List<JobDefinitionToken> tokens = new ArrayList<>();

		FrozenFilePosition currentTokenStart = position.freeze();
		FrozenFilePosition lastCharPosition = position.freeze();

		while (hasNextChar()) {
			char c = nextChar();

			if (Character.isLetterOrDigit(c) || c == '.') {
				lastCharPosition = position.freeze();
				currentTokenText.append(c);
				continue;
			} else if (currentTokenText.length() < 1) {
				currentTokenStart = position.freeze();
				lastCharPosition = position.freeze();
				continue;
			}

			String tokenText = currentTokenText.toString();
			currentTokenText.setLength(0);

			switch (tokenText) {
			case "with":
				tokens.add(new WithKeyword(currentTokenStart, lastCharPosition));
				break;

			case "do":
				tokens.add(new DoKeyword(currentTokenStart, lastCharPosition));
				break;

			case "emit":
				tokens.add(new EmitKeyword(currentTokenStart, lastCharPosition));
				break;

			default:
				if (Character.isDigit(tokenText.charAt(0)) || tokenText.charAt(0) == '-') {
					try {
						if (tokenText.length() > 2 && tokenText.charAt(1) == 'x' && tokenText.charAt(0) != '-') {
							tokens.add(new Numeral(currentTokenStart, lastCharPosition,
									Long.parseLong(tokenText.substring(2, 16))));
						} else if (tokenText.contains(".")) {
							tokens.add(new DoubleNumeral(currentTokenStart, lastCharPosition,
									Double.parseDouble(tokenText)));
						} else {
							tokens.add(new Numeral(currentTokenStart, lastCharPosition, Long.parseLong(tokenText)));
						}
					} catch (NumberFormatException e) {
						throw new JobPipelineDefinitionParseException(currentTokenStart, lastCharPosition,
								tokenText + " is not a valid number");
					}
				} else {
					tokens.add(new Identifier(currentTokenStart, lastCharPosition, tokenText));
				}

				break;
			}

			switch (c) {
			case '{':
				tokens.add(new OpeningBrace(position.freeze()));
				break;

			case '}':
				tokens.add(new ClosingBrace(position.freeze()));
				break;

			case '=':
				tokens.add(new EqualsSign(position.freeze()));
				break;

			case '"':
				tokens.add(parseStringLiteral(position.freeze()));
				break;

			case ';':
				tokens.add(new Semicolon(position.freeze()));
				break;

			default:
				throw new JobPipelineDefinitionParseException(position.freeze(), position.freeze(),
						"Unknown char " + c + " in job definition");
			}
		}

		return tokens;
	}

	private StringLiteral parseStringLiteral(FrozenFilePosition startPosition) throws JobPipelineDefinitionParseException {
		StringBuilder content = new StringBuilder();
		boolean escaped = false;

		while (true) {
			char c = nextChar();

			if (escaped) {
				switch (c) {
				case 'n':
					content.append('\n');
					break;
				case 't':
					content.append('\t');
					break;
				case '\\':
					content.append('\\');
					break;
				case '"':
					content.append('"');
					break;
				}
			} else {
				if (c == '\n') {
					throw new JobPipelineDefinitionParseException(startPosition, position.freeze(),
							"Unexpected end of line while parsing string");
				} else if (c == '\\') {
					escaped = true;
				} else if (c == '"') {
					return new StringLiteral(startPosition, position.freeze(), content.toString());
				} else {
					content.append(c);
				}
			}
		}
	}

	private char nextChar() throws JobPipelineDefinitionParseException {
		if (offset >= text.length()) {
			throw new JobPipelineDefinitionParseException(position.freeze(), position.freeze(), "Unexpected end of file");
		}

		char c = text.charAt(offset++);
		position.next(c);
		return c;
	}

	private boolean hasNextChar() {
		return offset < text.length();
	}
}
