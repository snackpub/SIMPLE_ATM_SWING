package com.snackpub.swing.exception;

/**
 * 服务异常捕获
 *
 * @author snackpub
 * @date 2021/1/10
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 2359767895161832954L;


    public ServiceException(String message) {
        super(message);
    }


    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 提高性能
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }
}
