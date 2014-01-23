package org.osgl.aaa.spring.web;

import org.osgl._;
import org.osgl.aaa.AAA;
import org.osgl.aaa.AAAContext;
import org.osgl.aaa.Principal;
import org.osgl.util.E;
import org.rythmengine.spring.web.RythmConfigurer;
import org.rythmengine.spring.web.Session;
import org.rythmengine.spring.web.SessionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luog on 14/01/14.
 */
public class A3SessionManager extends A3Manager implements SessionManager.Listener {

    private static final ThreadLocal<_.T2<Principal, Object>> param = new ThreadLocal<_.T2<Principal, Object>>();

    @Override
    protected String resolveUserName(HttpServletRequest request) {
        throw E.unsupport();
    }

    private void fireEvent(Principal principal, Object handler) {
        firePrincipalResolved(principal, handler);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        _.T2<Principal, Object> t2 = param.get();
        if (null == t2) {
            t2 = _.T2(null, handler);
            param.set(t2);
        } else {
            Principal p = t2._1;
            fireEvent(p, handler);
        }

        return true;
    }

    @Override
    public void onSessionResolved(Session session) {
        String username = session.get(AAAConfigurer.getSessionKeyUserName());
        AAAContext ctxt = AAAConfigurer.getAAAContext();
        AAA.setContext(ctxt);
        Principal user = ctxt.getPersistentService().findByName(username, Principal.class);
        if (null != user) {
            ctxt.setCurrentPrincipal(user);
        }
        _.T2<Principal, Object> t2 = param.get();
        if (null == t2) {
            t2 = _.T2(user, null);
            param.set(t2);
        } else {
            Object handler = t2._2;
            fireEvent(user, handler);
        }
    }

    @Override
    public void onSessionCleanUp() {
        param.remove();
    }

    @Override
    protected void register(InterceptorRegistry registry) {
        if (RythmConfigurer.getInstance().sessionManagerEnabled()) {
            SessionManager.addListener(this);
            registry.addInterceptor(this);
        } else {
            registry.addInterceptor(new A3HttpSessionManager());
        }
    }
}
