package org.owfs.ownet;


public class OWException extends Exception {
	private static final long serialVersionUID = -5795569282968643657L;
	
	private String path = "";
	private int errorCodeValue = 14;
	private OWErrorCodes errorCode = OWErrorCodes.EFAULT;
	
	public OWException(String message, String path,  int errorCodeValue) {
		super(message);
		this.path = path;
		this.errorCodeValue=-errorCodeValue;
		this.errorCode = OWErrorCodes.fromInt(this.errorCodeValue);
	}
	
	public String getPath() {
		return path;
	}
	
	public OWErrorCodes getErrorCode() {
		return errorCode;
	}
	
	public int getErrorCodeValue() {
		return errorCodeValue;
	}
	
	@Override
	public String toString() {
		return String.format("%s Path '%s'. Error %s (%d).", super.toString(), path, errorCode.toString(), errorCodeValue);
	}
}
