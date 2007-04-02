package org.eclipse.dltk.javascript.internal.ui.templates;

import org.eclipse.dltk.javascript.internal.ui.JavaScriptUI;
import org.eclipse.dltk.javascript.internal.ui.text.JavascriptTextTools;
import org.eclipse.dltk.javascript.internal.ui.text.SimpleJavascriptSourceViewerConfiguration;
import org.eclipse.dltk.javascript.ui.text.IJavaScriptPartitions;
import org.eclipse.dltk.ui.templates.ScriptTemplatePreferencePage;
import org.eclipse.dltk.ui.text.ScriptSourceViewerConfiguration;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class JavaScriptTemplatePreferencePage extends
		ScriptTemplatePreferencePage implements IWorkbenchPreferencePage {
	public JavaScriptTemplatePreferencePage() {
		setPreferenceStore(JavaScriptUI.getDefault().getPreferenceStore());

		setTemplateStore(JavaScriptTemplateAccess.getInstance()
				.getTemplateStore());
		setContextTypeRegistry(JavaScriptTemplateAccess.getInstance()
				.getContextTypeRegistry());
	}

	protected ScriptSourceViewerConfiguration createSourceViewerConfiguration(
			IDocument document) {
		IPreferenceStore store = JavaScriptUI.getDefault().getPreferenceStore();
		JavascriptTextTools textTools = JavaScriptUI.getDefault()
				.getTextTools();
		textTools.setupDocumentPartitioner(document,
				IJavaScriptPartitions.JS_PARTITIONING);

		return new SimpleJavascriptSourceViewerConfiguration(textTools
				.getColorManager(), store, null,
				IJavaScriptPartitions.JS_PARTITIONING, false);

	}
}
