package org.osgl.aaa.spring.web;

import org.osgl.util.S;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;

@Component
public class A3HttpSessionManager extends A3Manager {

    static final A3HttpSessionManager INSTANCE = new A3HttpSessionManager();

    @Override
    protected String resolveUserName(HttpServletRequest request) {
        return S.string(request.getAttribute(AAAConfigurer.getSessionKeyUserName()));
    }

    @Override
    protected void register(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }
}
