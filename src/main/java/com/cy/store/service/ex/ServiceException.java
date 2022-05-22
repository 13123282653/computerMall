package com.cy.store.service.ex;

//业务层异常的基类
//当抛出异常 使用 throw new ServiceException()/throw new ServiceException("xxx")/
public class ServiceException extends RuntimeException {
    public ServiceException() {
        super();
    }
    //抛出异常的信息
    public ServiceException(String message) {
        super(message);
    }
    //抛出异常的信息的同时把对象也抛出
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
