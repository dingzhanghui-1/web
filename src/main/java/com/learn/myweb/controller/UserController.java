package com.learn.myweb.controller;

import com.learn.myweb.utils.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.learn.myweb.entity.User;
import com.learn.myweb.model.ResponseMessage;
import com.learn.myweb.repository.UserRepository;

@RequestMapping(value = "/users", produces = { "application/json;charset=UTF-8" })
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenManager tokenManager;

    @PostMapping(value = "")
    public ResponseMessage create(@RequestBody User userBody) {
        ResponseMessage message = new ResponseMessage();
        User user = userRepository.saveAndFlush(userBody);
        message.setStatus(true);
        message.setResult(user);
        return message;
    }

    @GetMapping(value = "/login")
    public ResponseMessage login(@RequestParam("mobile") String mobile, @RequestParam("password") String password) {
        ResponseMessage message = new ResponseMessage();
        /*
         * String salt = BCry.gensalt(); String encodedPassword =
         * BCrypt.hashpw(userBody.getPassword(), salt);
         */
        User userIndb = userRepository.findByMobileAndPassword(mobile, password);
        if (userIndb != null) {
            message.setStatus(true);
            message.setResult(userIndb);
            message.setToken(tokenManager.createToken(userIndb.getId()));
        } else {
            message.setStatus(false);
            message.setResult("用户名或密码错误");
        }
        return message;
    }
}
