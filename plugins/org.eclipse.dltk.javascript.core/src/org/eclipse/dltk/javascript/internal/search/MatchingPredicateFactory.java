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

import org.eclipse.dltk.core.search.SearchPattern;
import org.eclipse.dltk.core.search.matching2.FalseMatchingPredicate;
import org.eclipse.dltk.core.search.matching2.IMatchingPredicate;
import org.eclipse.dltk.internal.core.search.matching.FieldPattern;

public class MatchingPredicateFactory {

	static IMatchingPredicate<MatchingNode> create(SearchPattern pattern) {
		if (pattern instanceof FieldPattern) {
			return new FieldPredicate((FieldPattern) pattern);
		}
		return new FalseMatchingPredicate<MatchingNode>();
	}

}