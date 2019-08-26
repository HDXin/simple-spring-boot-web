package top.atstudy.framework.enums;

import top.atstudy.framework.spec.enums.http.IError401Enum;

public enum Enum401Error implements IError401Enum<Enum401Error> {
    SGW_SESSION_USER_NOT_FOUND(401001, "网关session用户未找到");

    private Integer code;
    private String message;

    private Enum401Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public Integer getCode() {
        return this.code;
    }
}
