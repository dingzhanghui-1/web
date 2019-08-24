package com.learn.myweb;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {


    @RequestMapping(value = "/", produces = {"application/json; charset=UTF-8"})
    public String hello() {
        return "hello myweb!";
    }


}


