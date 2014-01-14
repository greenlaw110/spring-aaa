package org.osgl.aaa.spring.web;

import org.osgl._;
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

/**
 * Created by luog on 13/01/14.
 */
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
    private static String sessionKeyUserName = "username";
    private static AAAContext ctxt;

    private A3Manager sessionManager;

    public AAAConfigurer() {
        try {
            sessionManager = _.newInstance("org.osgl.aaa.spring.web.A3SessionManager");
        } catch (Exception e) {
            sessionManager = new A3HttpSessionManager();
        }
    }


    public static AAAContext getAAAContext() {
        if (null == ctxt) {
            ctxt = new SimpleAAAContext(authen, author, db, superUser, allowSystem ? SimplePrincipal.createSystemPrincipal(system) : null, allowSystem);
        }
        return ctxt;
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
