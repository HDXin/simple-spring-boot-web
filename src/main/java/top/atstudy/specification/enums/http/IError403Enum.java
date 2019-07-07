package top.atstudy.specification.enums.http;

import top.atstudy.specification.enums.base.IErrorEnum;

public interface IError403Enum<T extends Enum<T>> extends IErrorEnum<T> {
    default Integer configHttpCode() {
        return 403;
    }

    default String configReason() {
        return "[Forbidden] - 服务器拒绝执行该请求";
    }
}
