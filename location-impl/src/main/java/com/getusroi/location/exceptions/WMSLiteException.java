package com.getusroi.location.exceptions;

import java.util.UUID;

import com.getusroi.location.utils.ResourceFileReader;

public class WMSLiteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String exceptionUUID = null;
	private String errorCode;
	private String errorMessage;
	
	public String getExceptionUUID() {
		return exceptionUUID;
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public WMSLiteException() {
		super();
		exceptionUUID = UUID.randomUUID().toString();
	}
	
	public WMSLiteException(String errorCode) throws Exception {
		super(ResourceFileReader.getStatusDesc(errorCode));
        this.errorCode = errorCode;
        this.errorMessage = String.format(ResourceFileReader.getStatusDesc(errorCode));
    }
	

	
	public WMSLiteException(String errorCode, Object[] params) {
        this.errorCode = errorCode;
        this.errorMessage = String.format(ResourceFileReader.getStatusDesc(errorCode), params);
        /*exceptionUUID = UUID.randomUUID().toString();*/
    }
		
	public WMSLiteException(String errorCode, String errorDesc) {
        this.errorCode = errorCode;
        this.errorMessage = errorDesc;
	}

    public WMSLiteException(String message, Throwable cause) {
        super( message, cause );
        exceptionUUID = UUID.randomUUID().toString();
    }
    
    public WMSLiteException(Throwable cause) {
        super( cause );
        exceptionUUID = UUID.randomUUID().toString();
    }
    
    public WMSLiteException(Throwable cause, String exceptionUUID) {
        super(cause);
        this.exceptionUUID = exceptionUUID;
    }
/*    public WMSLiteException(String message, String exceptionUUID) {
        super(message);
        this.exceptionUUID = exceptionUUID;
    }*/

    public WMSLiteException(String message, Throwable cause, String exceptionUUID) {
        super(message, cause);
        this.exceptionUUID = exceptionUUID;
    }

}
