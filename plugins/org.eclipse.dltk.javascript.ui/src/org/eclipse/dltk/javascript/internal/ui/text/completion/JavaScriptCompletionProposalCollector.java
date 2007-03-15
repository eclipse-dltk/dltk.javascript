package org.eclipse.dltk.javascript.internal.ui.text.completion;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.core.IDLTKProject;
import org.eclipse.dltk.core.IMember;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.internal.javascript.typeinference.IReference;
import org.eclipse.dltk.javascript.scriptdoc.ScriptDocumentationProvider;
import org.eclipse.dltk.ui.text.completion.AbstractDLTKCompletionProposal;
import org.eclipse.dltk.ui.text.completion.CompletionProposalCollector;
import org.eclipse.dltk.ui.text.completion.CompletionProposalLabelProvider;
import org.eclipse.dltk.ui.text.completion.IScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ProposalInfo;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.swt.graphics.Image;

public class JavaScriptCompletionProposalCollector extends
		CompletionProposalCollector {

	protected final static char[] VAR_TRIGGER = new char[] { '\t', ' ', '=',
			';', '.' };

	public JavaScriptCompletionProposalCollector(ISourceModule module) {
		super(module);
	}

	protected CompletionProposalLabelProvider createLabelProvider() {
		return new JavaScriptCompletionProposalLabelProvider();
	}

	

	protected IScriptCompletionProposal createScriptCompletionProposal(
			CompletionProposal proposal) {
		// TODO Auto-generated method stub
		final IScriptCompletionProposal createScriptCompletionProposal2 = super.createScriptCompletionProposal(proposal);
		AbstractDLTKCompletionProposal createScriptCompletionProposal = (AbstractDLTKCompletionProposal) createScriptCompletionProposal2;
		final Object ref=(Object) proposal.extraInfo;
		
		
		ProposalInfo proposalInfo = new ProposalInfo(null){
			
			
			public  String getInfo(IProgressMonitor monitor) {
				if (ref instanceof IReference)
				{
				ArrayList ms=new ArrayList();
				((IReference) ref).addModelElements(ms);
				if (ms.size()>0);
				Reader contentReader = new ScriptDocumentationProvider().getInfo((IMember) ms.get(0), true,true);
				if (contentReader!=null) {
					String string = getString(contentReader);					
					return string;
				}
				}
				else if (ref instanceof IMember){
					Reader contentReader = new ScriptDocumentationProvider().getInfo((IMember) ref, true,true);
					if (contentReader!=null) {
						String string = getString(contentReader);					
						return string;
					}
				}
				return "Documentation not resolved";				
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
		};		
		createScriptCompletionProposal.setProposalInfo(proposalInfo);
		return createScriptCompletionProposal;
	}

	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i) {
		JavaScriptCompletionProposal javaScriptCompletionProposal = new JavaScriptCompletionProposal(completion, replaceStart,
						length, image, displayString, i);
		
		return javaScriptCompletionProposal;
	}

	protected ScriptCompletionProposal createScriptCompletionProposal(
			String completion, int replaceStart, int length, Image image,
			String displayString, int i, boolean isInDoc) {
		JavaScriptCompletionProposal javaScriptCompletionProposal = new JavaScriptCompletionProposal(completion, replaceStart,						length, image, displayString, i, isInDoc);
		return javaScriptCompletionProposal;
	}
	
	

	
	
	protected char[] getVarTrigger() {
		return VAR_TRIGGER;
	}

	protected ScriptCompletionProposal createOverrideCompletionProposal(
			IDLTKProject dltkProject, ISourceModule compilationUnit,
			String name, String[] paramTypes, int start, int length,
			String displayName, String completionProposal) {
		return new JavaScriptOverrideCompletionProposal(dltkProject, compilationUnit,
				name, paramTypes, start, length, displayName,
				completionProposal);
	}

	protected ScriptContentAssistInvocationContext createScriptContentAssistInvocationContext(
			ISourceModule sourceModule) {
		return new ScriptContentAssistInvocationContext(sourceModule) {
			protected CompletionProposalLabelProvider createLabelProvider() {
				return new JavaScriptCompletionProposalLabelProvider();
			}
		};
	}
	

	protected IScriptCompletionProposal createKeywordProposal(
			CompletionProposal proposal) {
		String completion = String.valueOf(proposal.getCompletion());
		int start = proposal.getReplaceStart();
		int length = getLength(proposal);
		String label = getLabelProvider().createSimpleLabel(proposal);
		Image img = getImage(getLabelProvider().createMethodImageDescriptor(
				proposal));
		int relevance = computeRelevance(proposal);
		return createScriptCompletionProposal(completion, start, length, img,
				label, relevance);
	}
}
