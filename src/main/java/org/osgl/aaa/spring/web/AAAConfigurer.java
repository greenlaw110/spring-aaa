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
import org.osgl.aaa.*;
import org.osgl.aaa.impl.SimpleAAAContext;
import org.osgl.aaa.impl.SimplePrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("org.osgl.aaa.spring")
public class AAAConfigurer extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

    private static AuthorizationService author;
    private static AuthenticationService authen;
    private static AAAPersistentService db;
    private static boolean allowSystem = true;
    private static String system = AAA.SYSTEM;
    private static int superUser = AAA.SUPER_USER;
    private static boolean enableAAAManager = true;
    private static Principal systemPrincipal = SimplePrincipal.createSystemPrincipal(system);
    private static String sessionKeyUserName = "username";
    private static AAAContext ctxt;

    private A3Manager sessionManager;

    public AAAConfigurer() {
        try {
            sessionManager = $.newInstance("org.osgl.aaa.spring.web.A3SessionManager");
        } catch (Exception e) {
            sessionManager = new A3HttpSessionManager();
        }
    }

    @Deprecated
    public static AAAContext getAAAContext() {
        return new SimpleAAAContext(authen, author, db, null, superUser, allowSystem ? systemPrincipal: null, allowSystem);
    }

    public static AAAContext createAAAContext() {
        return new SimpleAAAContext(authen, author, db, null, superUser, allowSystem ? systemPrincipal: null, allowSystem);
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService) {
        authen = authenticationService;
    }

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        author = authorizationService;
    }

    @Autowired
    public void setPersistenceService(AAAPersistentService persistenceService) {
        db = persistenceService;
    }

    @Autowired(required = false)
    public void setSystemPrincipal(Principal sys) {
        systemPrincipal = sys;
    }

    @Autowired(required = false)
    public void setAAAContext(AAAContext ctxt) {
        this.ctxt = ctxt;
    }

    public void setAllowSystem(boolean allowSystem) {
        AAAConfigurer.allowSystem = allowSystem;
    }

    public void setSystemPrincipal(String systemPrincipal) {
        system = systemPrincipal;
    }

    public void setSuperUserLevel(int superUserLevel) {
        superUser = superUserLevel;
    }

    public void setEnableAAAManager(boolean enableAAAManager) {
        AAAConfigurer.enableAAAManager = enableAAAManager;
    }

    public void setSessionManager(A3Manager sessionManager) {
        if (null != sessionManager) this.sessionManager = sessionManager;
    }

    public void setSessionKeyUserName(String sessionKeyUserName) {
        AAAConfigurer.sessionKeyUserName = sessionKeyUserName;
    }

    public static String getSessionKeyUserName() {
        return sessionKeyUserName;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (enableAAAManager) {
            sessionManager.register(registry);
        }
    }
}
