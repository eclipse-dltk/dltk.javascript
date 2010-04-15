/*******************************************************************************
 * Copyright (c) 2010 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.javascript.internal.search;

public enum MatchLevel {
	// /**
	// * 0%
	// */
	// IMPOSSIBLE_MATCH,

	/**
	 * 50%
	 */
	POSSIBLE_MATCH,

	/**
	 * 75%
	 */
	INACCURATE_MATCH,

	/**
	 * 100%
	 */
	EXACT_MATCH
}
