package org.osgl.aaa.spring.web;

import org.osgl.aaa.AAA;
import org.osgl.aaa.AAAContext;
import org.osgl.aaa.Principal;
import org.osgl.util.C;
import org.osgl.util.E;
import org.osgl.util.S;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class A3Manager extends HandlerInterceptorAdapter {

    public static interface Listener {
        void onPrincipalResolved(Principal principal, Object handler);
        void onCleanUp();

        public static abstract class Base implements Listener, InitializingBean {
            @Override
            public void afterPropertiesSet() throws Exception {
                registerListener(this);
            }
        }
    }

    protected abstract String resolveUserName(HttpServletRequest request);
    protected abstract void register(InterceptorRegistry registry);

    private static final C.List<Listener> listeners = C.newList();

    protected final void firePrincipalResolved(Principal principal, Object handler) {
        for (Listener l : listeners) {
            l.onPrincipalResolved(principal, handler);
        }
    }

    protected final void fireCleanUp() {
        for (Listener l : listeners) {
            l.onCleanUp();
        }
    }

    public static void registerListener(Listener listener) {
        E.NPE(listener);
        listeners.add(listener);
    }

    private void cleanUp() {
        fireCleanUp();
        AAAContext ctxt = AAA.context();
        ctxt.setGuardedTarget(null);
        ctxt.setCurrentPrincipal(null);
        AAA.setContext(null);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String username = resolveUserName(request);
        Principal user = null;
        if (S.notEmpty(username)) {
            AAAContext ctxt = AAAConfigurer.getAAAContext();
            AAA.setContext(ctxt);
            user = ctxt.getPersistentService().findByName(username, Principal.class);
            if (null != user) {
                ctxt.setCurrentPrincipal(user);
            }
        }
        firePrincipalResolved(user, handler);
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
