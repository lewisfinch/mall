package com.mall.interceptor;

import com.mall.utils.UserContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInfoInterceotor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptor preHandle");
        String userInfo = request.getHeader("userId");
        if(userInfo == null){
            return false;
        }
        Integer userId = Integer.valueOf(userInfo);
        UserContext.setCurrentUser(userId);
        System.out.println("check");
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
