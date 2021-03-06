/*******************************************************************************
 * Copyright (c) 2012 NumberFour AG
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     NumberFour AG - initial API and Implementation (Alex Panchenko)
 *******************************************************************************/
package org.eclipse.dltk.javascript.typeinfo;

import org.eclipse.dltk.javascript.core.Types;
import org.eclipse.dltk.javascript.internal.core.TypeSystems;

public class E4XTypes {
	private E4XTypes() {
	}

	public static final IRSimpleType XML = (IRSimpleType) RTypes.simple(
			TypeSystems.GLOBAL, Types.XML);
}
