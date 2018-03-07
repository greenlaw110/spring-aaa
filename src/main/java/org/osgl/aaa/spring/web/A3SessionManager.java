package org.osgl.aaa.spring.web;

/*-
 * #%L
 * Spring AAA Plugin
 * %%
 * Copyright (C) 2017 - 2018 OSGL (Open Source General Library)
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.osgl.$;
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

public class A3SessionManager extends A3Manager implements SessionManager.Listener {

    private static final ThreadLocal<$.T2<Principal, Object>> param = new ThreadLocal<$.T2<Principal, Object>>();

    @Override
    protected String resolveUserName(HttpServletRequest request) {
        throw E.unsupport();
    }

    private void fireEvent(Principal principal, Object handler) {
        firePrincipalResolved(principal, handler);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        $.T2<Principal, Object> t2 = param.get();
        if (null == t2) {
            t2 = $.T2(t2._1, handler);
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
        AAAContext ctxt = AAAConfigurer.createAAAContext();
        AAA.setContext(ctxt);
        Principal user = ctxt.getPersistentService().findByName(username, Principal.class);
        if (null != user) {
            ctxt.setCurrentPrincipal(user);
        }
        $.T2<Principal, Object> t2 = param.get();
        if (null == t2) {
            t2 = $.T2(user, null);
            param.set(t2);
        } else {
            Object handler = t2._2;
            if (null != handler) {
                fireEvent(user, handler);
            }
        }
    }

    @Override
    public void onSessionCleanUp() {
        param.remove();
        AAA.clearContext();
    }

    @Override
    protected void register(InterceptorRegistry registry) {
        if (RythmConfigurer.getInstance().sessionManagerEnabled()) {
            SessionManager.addListener(this);
            registry.addInterceptor(this);
        } else {
            A3HttpSessionManager sm = A3HttpSessionManager.INSTANCE;
            registry.addInterceptor(sm);
        }
    }
}
