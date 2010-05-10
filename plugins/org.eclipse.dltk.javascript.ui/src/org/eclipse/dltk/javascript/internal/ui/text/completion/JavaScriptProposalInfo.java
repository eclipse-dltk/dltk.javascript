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
package org.eclipse.dltk.javascript.internal.ui.text.completion;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.internal.javascript.typeinference.IReference;
import org.eclipse.dltk.javascript.scriptdoc.ScriptDocumentationProvider;
import org.eclipse.dltk.javascript.typeinfo.model.Element;
import org.eclipse.dltk.ui.text.completion.ProposalInfo;

class JavaScriptProposalInfo extends ProposalInfo {

	private final Object ref;

	JavaScriptProposalInfo(Object ref) {
		super(null);
		this.ref = ref;
	}

	public String getInfo(IProgressMonitor monitor) {
		if (ref instanceof IReference) {
			List<IModelElement> ms = new ArrayList<IModelElement>();
			((IReference) ref).addModelElements(ms);
			if (ms.size() > 0) {
				Reader contentReader = new ScriptDocumentationProvider()
						.getInfo((IMember) ms.get(0), true, true);
				if (contentReader != null) {
					String string = getString(contentReader);
					return string;
				}
			}
		} else if (ref instanceof IMember) {
			Reader contentReader = new ScriptDocumentationProvider().getInfo(
					(IMember) ref, true, true);
			if (contentReader != null) {
				String string = getString(contentReader);
				return string;
			}
		} else if (ref instanceof String) {
			return (String) ref;
		} else if (ref instanceof Element) {
			return ((Element) ref).getDescription();
		}
		return null;
	}

	/**
	 * Gets the reader content as a String
	 */
	private String getString(Reader reader) {
		StringBuffer buf = new StringBuffer();
		char[] buffer = new char[1024];
		int count;
		try {
			while ((count = reader.read(buffer)) != -1)
				buf.append(buffer, 0, count);
		} catch (IOException e) {
			return null;
		}
		return buf.toString();
	}
}