package top.atstudy.framework.exception;

import top.atstudy.specification.enums.base.IErrorEnum;

public class APIException extends FrameworkException {
    public APIException(IErrorEnum errorEnum) {
        super(errorEnum);
    }

    public String toString() {
        return "APIException{errorEnum=" + super.errorEnum + '}';
    }
}
