/*******************************************************************************
 * Copyright (c) 2009 xored software, Inc.  
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html  
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Vladimir Belov)
 *******************************************************************************/

package org.eclipse.dltk.javascript.ast;

import org.eclipse.core.runtime.Assert;
import org.eclipse.dltk.ast.ASTNode;

public class PropertyInitializer extends Expression {

	private Expression name;
	private Expression value;
	private int colon;

	public PropertyInitializer(ASTNode parent) {
		super(parent);
	}

	public Expression getName() {
		return this.name;
	}

	public void setName(Expression name) {
		this.name = name;
	}

	public Expression getValue() {
		return this.value;
	}

	public void setValue(Expression value) {
		this.value = value;
	}

	public int getColon() {
		return this.colon;
	}

	public void setColon(int colon) {
		this.colon = colon;
	}

	public String toSourceString(String indentationString) {

		Assert.isTrue(sourceStart() > 0);
		Assert.isTrue(sourceEnd() > 0);
		Assert.isTrue(colon > 0);

		StringBuffer buffer = new StringBuffer();

		buffer.append(name.toSourceString(indentationString));
		buffer.append(": ");
		buffer.append(value.toSourceString(indentationString));

		return buffer.toString();
	}

	public boolean isBlock() {
		return false;
	}
}
