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

public class JobPipelineDefinitionParseException extends Exception {
	private final FrozenFilePosition start;
	private final FrozenFilePosition end;

	public JobPipelineDefinitionParseException(FrozenFilePosition start, FrozenFilePosition end, String message) {
		super(message);
		this.start = start;
		this.end = end;
	}

	public JobPipelineDefinitionParseException(
			FrozenFilePosition start, FrozenFilePosition end, String message, Throwable cause) {
		super(message, cause);
		this.start = start;
		this.end = end;
	}

	public FrozenFilePosition getStart() {
		return start;
	}

	public FrozenFilePosition getEnd() {
		return end;
	}
}
