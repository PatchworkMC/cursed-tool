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

package com.patchworkmc.jobs.meta;

import java.util.HashMap;
import java.util.Map;

import com.patchworkmc.jobs.ValuePool;
import com.patchworkmc.jobs.parser.token.Identifier;
import com.patchworkmc.jobs.parser.token.JobDefinitionToken;

public class JobConnectionMeta {
	private final Map<String, JobDefinitionToken> inputs;
	private Identifier output;
	private ValuePool valuePool;

	public JobConnectionMeta() {
		inputs = new HashMap<>();
	}

	public void addInput(String name, JobDefinitionToken declaration) {
		inputs.put(name, declaration);
	}

	public void setOutput(Identifier output) {
		this.output = output;
	}

	public void setValuePool(ValuePool valuePool) {
		this.valuePool = valuePool;
	}
}
