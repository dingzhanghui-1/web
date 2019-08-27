package com.learn.myweb.controller;

import com.learn.myweb.model.ResponseMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @ExceptionHandler(Exception.class)
    public ResponseMessage dealException(Exception e) {
        ResponseMessage message = new ResponseMessage();
        message.setStatus(false);
        message.setResult(e);
        return message;
    }
}
