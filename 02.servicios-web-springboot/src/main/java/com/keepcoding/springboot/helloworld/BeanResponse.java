package com.keepcoding.springboot.helloworld;

public class BeanResponse {

    private String message;

    public BeanResponse() {
    }

    public BeanResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
