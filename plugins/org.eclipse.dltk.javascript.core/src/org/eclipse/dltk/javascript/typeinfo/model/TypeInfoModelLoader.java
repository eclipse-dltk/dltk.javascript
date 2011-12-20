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
package org.eclipse.dltk.javascript.typeinfo.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.dltk.compiler.CharOperation;
import org.eclipse.dltk.javascript.typeinfo.TypeInfoManager;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class TypeInfoModelLoader {

	private static TypeInfoModelLoader instance = null;

	public synchronized static TypeInfoModelLoader getInstance() {
		if (instance == null) {
			instance = new TypeInfoModelLoader();
		}
		return instance;
	}

	private final TypeInfoModelResourceSet resourceSet;

	private TypeInfoModelLoader() {
		resourceSet = TypeInfoManager.loadModelResources();
	}

	private Resource[] getResources() {
		return resourceSet.resources();
	}

	public Type getType(String typeName) {
		return getType(typeName, false);
	}

	public Type getType(String typeName, boolean all) {
		return resourceSet.getType(typeName, all);
	}

	public String translateTypeName(String typeName) {
		for (Resource resource : getResources()) {
			for (EObject object : resource.getContents()) {
				if (object instanceof TypeAlias) {
					final TypeAlias alias = (TypeAlias) object;
					if (typeName.equals(alias.getSource())) {
						if (alias.getTarget() != null) {
							return alias.getTarget().getName();
						}
						break;
					}
				}
			}
		}
		return typeName;
	}

	public Set<String> listTypes(String prefix) {
		Set<String> result = new HashSet<String>();
		for (Resource resource : getResources()) {
			for (EObject object : resource.getContents()) {
				if (object instanceof Type) {
					final Type type = (Type) object;
					if (type.isVisible()
							&& CharOperation.prefixEquals(prefix,
									type.getName())) {
						result.add(type.getName());
					}
				}
			}
		}
		return result;
	}

	/**
	 * @since 3.0
	 */
	public Member getMember(String memberName) {
		for (Resource resource : getResources()) {
			for (EObject object : resource.getContents()) {
				if (object instanceof Member) {
					final Member member = (Member) object;
					if (memberName.equals(member.getName())) {
						return member;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @since 3.0
	 */
	public Set<Member> listMembers(String prefix) {
		Set<Member> result = new HashSet<Member>();
		for (Resource resource : getResources()) {
			for (EObject object : resource.getContents()) {
				if (object instanceof Member) {
					final Member member = (Member) object;
					if (CharOperation.prefixEquals(prefix, member.getName())) {
						result.add(member);
					}
				}
			}
		}
		return result;
	}

	public boolean hasResource(Resource resource) {
		return resourceSet.getResources().contains(resource);
	}

	/**
	 * Initializes the specified {@link ResourceSet} so it can load the same
	 * resources by their permanent URIs.
	 * 
	 * @param resourceSet
	 */
	public void initializeURIMap(ResourceSet resourceSet) {
		resourceSet.getURIConverter().getURIMap()
				.putAll(this.resourceSet.getURIConverter().getURIMap());
	}

}
