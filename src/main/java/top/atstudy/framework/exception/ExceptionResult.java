package top.atstudy.framework.exception;

import top.atstudy.specification.enums.base.IErrorEnum;

import java.util.ArrayList;
import java.util.List;

public class ExceptionResult {
    private Integer code;
    private String message;
    private List<Error> errors = new ArrayList();

    public ExceptionResult() {
    }

    public ExceptionResult(IErrorEnum errorEnum) {
        this.message = errorEnum.getMessage();
        this.code = (Integer)errorEnum.getCode();
        this.errors.add((new ExceptionResult.Error()).setReason(errorEnum.getReason()).setMessage(errorEnum.getMessage()));
    }

    public Integer getCode() {
        return this.code;
    }

    public ExceptionResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public ExceptionResult setMessage(String message) {
        this.message = message == null ? null : message.trim();
        return this;
    }

    public List<ExceptionResult.Error> getErrors() {
        return this.errors;
    }

    public ExceptionResult setErrors(List<ExceptionResult.Error> errors) {
        this.errors = errors;
        return this;
    }

    public void addError(String reason, String message) {
        this.errors.add((new ExceptionResult.Error()).setReason(reason).setMessage(message));
    }

    public static class Error {
        private String domain;
        private String reason;
        private String message;

        public Error() {
        }

        public String getDomain() {
            return this.domain;
        }

        public ExceptionResult.Error setDomain(String domain) {
            this.domain = domain == null ? null : domain.trim();
            return this;
        }

        public String getReason() {
            return this.reason;
        }

        public ExceptionResult.Error setReason(String reason) {
            this.reason = reason == null ? null : reason.trim();
            return this;
        }

        public String getMessage() {
            return this.message;
        }

        public ExceptionResult.Error setMessage(String message) {
            this.message = message == null ? null : message.trim();
            return this;
        }
    }
}
