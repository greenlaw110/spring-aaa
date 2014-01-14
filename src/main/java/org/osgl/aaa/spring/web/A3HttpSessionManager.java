package org.osgl.aaa.spring.web;

import org.osgl.util.S;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by luog on 13/01/14.
 */
@Component
public class A3HttpSessionManager extends A3Manager {

    @Override
    protected String resolveUserName(HttpServletRequest request) {
        return S.string(request.getAttribute(AAAConfigurer.getSessionKeyUserName()));
    }

    @Override
    protected void register(InterceptorRegistry registry) {
        registry.addInterceptor(this);
    }
}
