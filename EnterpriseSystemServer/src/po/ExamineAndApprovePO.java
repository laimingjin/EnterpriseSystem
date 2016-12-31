package po;

import java.io.Serializable;

import businesslogic.JXCDocument;


public class ExamineAndApprovePO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4059846903692909584L;
	JXCDocument document;

	public ExamineAndApprovePO(JXCDocument document) {
		super();
		this.document = document;
	}

	/**
	 * @return the document
	 */
	public JXCDocument getDocument() {
		return document;
	}
	

}
