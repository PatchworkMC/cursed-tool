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

package com.patchworkmc.curse;

/**
 * Base class for all exceptions within the CurseApi.
 */
public class CurseException extends Exception {
	/**
	 * Creates a new CurseException with the specified message.
	 *
	 * @param message The message of the exception
	 */
	public CurseException(String message) {
		super(message);
	}

	/**
	 * Creates a new CurseException which was caused by another exception
	 * and adds a message to it.
	 *
	 * @param message The message of the curse exception
	 * @param cause   The cause this exception was generated
	 */
	public CurseException(String message, Throwable cause) {
		super(message, cause);
	}
}
