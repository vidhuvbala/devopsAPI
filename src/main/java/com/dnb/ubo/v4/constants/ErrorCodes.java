package com.dnb.ubo.v4.constants;

//Enum class to check ErrorCodes in ExceptionControllerAdvise

public enum ErrorCodes {
    UB008("UB008"),
    UB011("UB011"),
    UB019("UB019"),
    UB012("UB012"),
    UB014("UB014"),
    UB017("UB017"),
    UB016("UB016");

    private String code;

    private ErrorCodes(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String toString() {
        return code;
    }

}
