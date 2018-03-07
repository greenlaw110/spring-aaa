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
