package top.atstudy.specification.enums.http;

import top.atstudy.specification.enums.base.IErrorEnum;

public interface IError401Enum<T extends Enum<T>> extends IErrorEnum<T> {
    default Integer configHttpCode() {
        return 401;
    }

    default String configReason() {
        return "[Unauthorized] - 当前请求未通过授权";
    }
}
