package com.galvanize.gmdb.model;

public class GmdbExceptionResponse {
    private String errorMsg;

    public GmdbExceptionResponse(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
