package top.atstudy.framework.exception;

import top.atstudy.framework.spec.enums.base.IErrorEnum;

public class APIException extends FrameworkException {
    public APIException(IErrorEnum errorEnum) {
        super(errorEnum);
    }

    public String toString() {
        return "APIException{errorEnum=" + super.errorEnum + '}';
    }
}
