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
package org.eclipse.dltk.internal.javascript.ti;

import java.util.StringTokenizer;

import org.eclipse.dltk.javascript.ast.FunctionStatement;
import org.eclipse.dltk.javascript.typeinfo.IModelBuilder;
import org.eclipse.dltk.javascript.typeinfo.model.TypeInfoModelLoader;

/**
 * Implements support for javadocs tags .
 * 
 * @see http://jsdoc.sourceforge.net/
 * @see http://code.google.com/p/jsdoc-toolkit/wiki/TagType
 * @see http://code.google.com/p/jsdoc-toolkit/wiki/TagParam
 */
public class JSDocSupport implements IModelBuilder {

	public void processMethod(FunctionStatement statement, IMethod method) {
		if (statement.getDocumentation() == null) {
			return;
		}
		if (method.getType() != null) {
			return;
		}
		for (IParameter parameter : method.getParameters()) {
			if (parameter.getType() != null) {
				return;
			}
		}
		// parse documentation
		final String comment = statement.getDocumentation().getText();
		parseParams(method, comment);
		parseType(method, comment);
	}

	private static final String PARAM_TAG = "@param";

	private void parseParams(IMethod method, String comment) {
		int index = comment.indexOf(PARAM_TAG);
		while (index != -1) {
			int endLineIndex = comment.indexOf("\n", index);
			StringTokenizer st = new StringTokenizer(comment.substring(index
					+ PARAM_TAG.length(), endLineIndex));
			String type = null;
			while (st.hasMoreTokens()) {
				final String token = st.nextToken();
				if (token.startsWith("{") && token.endsWith("}")) {
					type = token.substring(1, token.length() - 1);
				} else if (type != null) {
					IParameter parameter = method.getParameter(token);
					if (parameter != null) {
						parameter.setType(TypeInfoModelLoader.getInstance()
								.getType(type));
					}
					break;
				}
			}
			index = comment.indexOf(PARAM_TAG, endLineIndex);
		}
	}

	private static final String TYPE_TAG = "@type";

	private void parseType(IMethod method, String comment) {
		int index = comment.indexOf(TYPE_TAG);
		if (index != -1) {
			int endLineIndex = comment.indexOf("\n", index);
			if (endLineIndex == -1) {
				endLineIndex = comment.length();
			}
			StringTokenizer st = new StringTokenizer(comment.substring(index
					+ TYPE_TAG.length(), endLineIndex));
			if (st.hasMoreTokens()) {
				final String typeToken = st.nextToken();
				method.setType(TypeInfoModelLoader.getInstance().getType(
						typeToken));
			}
		}
	}
}
