package top.atstudy.framework.exception;

import top.atstudy.specification.enums.base.IErrorEnum;

public abstract class FrameworkException extends Exception {
    protected IErrorEnum errorEnum;

    public FrameworkException(IErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
    }

    public IErrorEnum getErrorEnum() {
        return this.errorEnum;
    }

    public FrameworkException setErrorEnum(IErrorEnum errorEnum) {
        this.errorEnum = errorEnum;
        return this;
    }

    public String toString() {
        return "FrameworkException{errorEnum=" + this.errorEnum + '}';
    }
}
