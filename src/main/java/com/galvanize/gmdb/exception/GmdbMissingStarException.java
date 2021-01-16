package com.galvanize.gmdb.exception;

public class GmdbMissingStarException extends RuntimeException{
    private String errorMsg;

    public GmdbMissingStarException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
