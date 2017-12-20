package com.getusroi.location.exceptions;


public class WMSLiteServiceException extends WMSLiteException {
	
	public WMSLiteServiceException() {
		super();
    }

    public WMSLiteServiceException(String errorCode) throws Exception {
        super( errorCode );
    }

    public WMSLiteServiceException(String errorCode, Object[] params) {
        super( errorCode , params );
    }
    
    public WMSLiteServiceException(String errorCode, String errorDesc) {
        super( errorCode , errorDesc );
    }
    
    public WMSLiteServiceException(String message, Throwable cause) {
        super( message, cause );
    }
    public WMSLiteServiceException(Throwable cause) {
        super( cause );
    }
    
    public WMSLiteServiceException(Throwable cause, String exceptionUUID) {
        super(cause, exceptionUUID);
    }
/*    public WMSLiteServiceException(String message, String exceptionUUID) {
        super(message, exceptionUUID);
    }*/

    public WMSLiteServiceException(String message, Throwable cause, String exceptionUUID) {
        super(message, cause, exceptionUUID);
    }
    
	public String getErrorCode() {
		return super.getErrorCode();
	}
	
	public String getErrorMessage() {
		return super.getErrorMessage();
	}
}