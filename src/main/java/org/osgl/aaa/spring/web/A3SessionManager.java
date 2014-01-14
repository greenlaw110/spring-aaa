package org.osgl.aaa.spring.web;

import org.osgl.aaa.AAAContext;
import org.osgl.aaa.Principal;
import org.rythmengine.spring.web.RythmConfigurer;
import org.rythmengine.spring.web.Session;
import org.rythmengine.spring.web.SessionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luog on 14/01/14.
 */
public class A3SessionManager extends A3Manager {

    @Override
    protected String resolveUserName(HttpServletRequest request) {
        // this method will not be used
        return null;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // do nothing here as it is triggered by SessionManager
        return true;
    }

    @Override
    protected void register(InterceptorRegistry registry) {
        if (RythmConfigurer.getInstance().sessionManagerEnabled()) {
            SessionManager.addListener(new SessionManager.Listener() {
                @Override
                public void onSessionResolved(Session session) {
                    String username = session.get(AAAConfigurer.getSessionKeyUserName());
                    AAAContext ctxt = AAAConfigurer.getAAAContext();
                    Principal user = ctxt.getPersistentService().findByName(username, Principal.class);
                    if (null != user) {
                        ctxt.setCurrentPrincipal(user);
                    }
                }

                @Override
                public void onSessionCleanUp() {
                    // nothing to do here is clean up work has been done in A3Manager
                }
            });
        } else {
            registry.addInterceptor(new A3HttpSessionManager());
        }
    }
}
