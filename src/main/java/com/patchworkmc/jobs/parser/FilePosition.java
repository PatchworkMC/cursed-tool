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

public class FilePosition implements Cloneable {
	private final String filename;
	private int offset;
	private int line;
	private int column;

	public FilePosition(String filename) {
		this.filename = filename;
		this.offset = -1;
		this.line = 1;
		this.column = 1;
	}

	private FilePosition(String filename, int offset, int line, int column) {
		this.filename = filename;
		this.offset = offset;
		this.line = line;
		this.column = column;
	}

	public void next(char c) {
		offset++;

		if (c == '\n') {
			line++;
			column = 1;
			return;
		}

		column++;
	}

	public FrozenFilePosition freeze() {
		return new FrozenFilePosition(filename, offset, line, column);
	}

	@Override
	public FilePosition clone() {
		try {
			return (FilePosition) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
