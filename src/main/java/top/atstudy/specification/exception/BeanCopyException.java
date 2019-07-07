package top.atstudy.specification.exception;

import java.io.Serializable;

public class BeanCopyException extends RuntimeException implements Serializable {
    public BeanCopyException(Exception e) {
        super(e);
    }
}
