package com.parkee.techtest.bean;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class GeneralResponseBean<T> {
    private HttpStatus status;
    private String message;
    private T data;
    private Date timestamp;

    public GeneralResponseBean(T data, HttpStatus status) {
        this.data = data;
        this.message = "Success";
        this.status = status;
        this.timestamp = new Date();
    }

    public GeneralResponseBean(String message) {
        this.data = null;
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
        this.timestamp = new Date();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
