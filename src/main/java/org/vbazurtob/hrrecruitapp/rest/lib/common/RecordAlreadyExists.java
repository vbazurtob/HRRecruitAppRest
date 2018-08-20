package org.vbazurtob.hrrecruitapp.rest.lib.common;

public class RecordAlreadyExists extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public RecordAlreadyExists() {
	
	}

	public RecordAlreadyExists(String message) {
		super(message);
	}


}
