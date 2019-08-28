package com.learn.myweb.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.myweb.model.ResponseMessage;

@RestController
public class BaseController {

    @ExceptionHandler(Exception.class)
    public ResponseMessage dealException(Exception e) {
        ResponseMessage message = new ResponseMessage();
        message.setStatus(false);
        message.setResult(e);
        return message;
    }


    public void  dealInvalidToken(HttpServletResponse response)
    {
        try {
            ResponseMessage message = new ResponseMessage();
            message.setToken(String.valueOf(100));
            message.setResult("token已经失效");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(message);
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(json);
            writer.flush();
        }
        catch (Exception e)
        {

        }


    }
}
