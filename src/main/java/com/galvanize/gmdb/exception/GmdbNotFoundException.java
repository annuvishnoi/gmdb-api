package com.galvanize.gmdb.exception;

public class GmdbNotFoundException extends RuntimeException{
    private String errorMsg;

    public GmdbNotFoundException(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
