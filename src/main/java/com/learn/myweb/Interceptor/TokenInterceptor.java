package com.learn.myweb.Interceptor;

import com.learn.myweb.redis.StringRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisService stringRedisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String url = request.getRequestURL().toString();
        if (url.contains("/login")) {
            return true;
        } else {

        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

    public boolean verifyToken( HttpServletRequest request)
    {
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token) || null == token)
        {
            return false;
        }else
        {
            if(stringRedisService.judgeKey(token))
            {


                stringRedisService.setKeyExpirDate(token,15, TimeUnit.MINUTES);


            }else
            {

            }

        }




    }

    private boolean parseTokeFromRedis(String token)
    {
        String userIdIndb = stringRedisService.get(token);




    }
}
