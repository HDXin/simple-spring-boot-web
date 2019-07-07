package top.atstudy.specification.enums.http;

import top.atstudy.specification.enums.base.IErrorEnum;

public interface IError400Enum<T extends Enum<T>> extends IErrorEnum<T> {
    default Integer configHttpCode() {
        return 400;
    }

    default String configReason() {
        return "[Bad Request] - 请求参数不合法";
    }
}
