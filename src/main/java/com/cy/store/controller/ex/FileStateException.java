package com.cy.store.controller.ex;
//上传文件状态异常——当对方发送一个文件，你刚开始读取的时候，对方给撤回了。
public class FileStateException extends FileUpLoadException{
    public FileStateException() {
        super();
    }

    public FileStateException(String message) {
        super(message);
    }

    public FileStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStateException(Throwable cause) {
        super(cause);
    }

    protected FileStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
