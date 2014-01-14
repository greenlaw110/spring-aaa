package org.osgl.aaa.spring.web;

import org.osgl.aaa.AAAContext;
import org.osgl.aaa.Principal;
import org.rythmengine.utils.S;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luog on 14/01/14.
 */
public abstract class A3Manager extends HandlerInterceptorAdapter {

    protected abstract String resolveUserName(HttpServletRequest request);
    protected abstract void register(InterceptorRegistry registry);

    private void cleanUp() {
        AAAContext ctxt = AAAConfigurer.getAAAContext();
        ctxt.setGuardedTarget(null);
        ctxt.setCurrentPrincipal(null);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = resolveUserName(request);
        if (S.empty(username)) return true;
        AAAContext ctxt = AAAConfigurer.getAAAContext();
        Principal user = ctxt.getPersistentService().findByName(username, Principal.class);
        if (null != user) {
            ctxt.setCurrentPrincipal(user);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        cleanUp();
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        cleanUp();
    }

}
