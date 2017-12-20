package com.getusroi.location.exceptions;

public class WMSLiteDAOException extends WMSLiteException {
	
	public WMSLiteDAOException() {
		super();
    }

    public WMSLiteDAOException(String message) throws Exception {
        super( message );
    }

    public WMSLiteDAOException(String message, Throwable cause) {
        super( message, cause );
    }
    public WMSLiteDAOException(Throwable cause) {
        super( cause );
    }
    
    public WMSLiteDAOException(Throwable cause, String exceptionUUID) {
        super(cause, exceptionUUID);
    }
    public WMSLiteDAOException(String message, String exceptionUUID) {
        super(message, exceptionUUID);
    }

    public WMSLiteDAOException(String message, Throwable cause, String exceptionUUID) {
        super(message, cause, exceptionUUID);
    }
}