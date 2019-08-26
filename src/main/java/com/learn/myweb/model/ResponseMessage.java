package com.learn.myweb.model;

import lombok.Data;

@Data
public class ResponseMessage {

    private String token;

    private boolean status;

    private Object result;
}
