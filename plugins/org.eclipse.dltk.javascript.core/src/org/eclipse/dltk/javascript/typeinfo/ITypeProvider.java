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
package org.eclipse.dltk.javascript.typeinfo;

import java.util.Set;

import org.eclipse.dltk.javascript.typeinfo.model.Type;

public interface ITypeProvider {

	public Type getType(ITypeInfoContext context, String typeName);

	/**
	 * Return the type names starting with the specified prefix
	 * 
	 * @param context
	 *            operation context
	 * @param prefix
	 *            the prefix, not <code>null</code>
	 * @return
	 */
	public Set<String> listTypes(ITypeInfoContext context, String prefix);

}
