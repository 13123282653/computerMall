package com.cy.store.util;

import java.io.Serializable;
//json格式的数据进行响应
public class JsonResult<E> implements Serializable {
    //状态码
    private Integer state;
    //描述信息
    private String message;
    //数据(并不确定他的类型，所以利用泛型来描述,当这个数据是泛型时，这个类也必须是泛型)
    private E data;

    //设置三个构造方法，为后期在不同场景下设置相关的值
    public JsonResult() {
    }

    public JsonResult(Integer state) {
        this.state = state;
    }

    public JsonResult(Integer state, E data) {
        this.state = state;
        this.data = data;
    }

    //声明关于异常的捕获
    public JsonResult(Throwable e) {
        this.message = e.getMessage();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
