package com.learn.myweb.Interceptor;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.learn.myweb.redis.StringRedisService;
import com.learn.myweb.utils.TokenManager;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisService stringRedisService;

    @Autowired
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return verifyToken(request);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    
    public boolean verifyToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (null != token && !StringUtils.isEmpty(token)) {
            if (stringRedisService.judgeKey(token)) {
                if (parseTokeFromRedis(token)) {
                    stringRedisService.setKeyExpirDate(token, 15, TimeUnit.MINUTES);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean parseTokeFromRedis(String token) {
        String userIdIndb = stringRedisService.get(token);
        String tokenUserId = tokenManager.parseUserIdFromToken(token);
        if (null != userIdIndb && StringUtils.equals(userIdIndb, tokenUserId)) {
            return true;
        } else {
            return false;
        }
    }
}
