package org.osgl.aaa.spring;

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

/**
 * Created by luog on 12/01/14.
 */
//@Aspect
class A3Aspect {
//
//    private AAAContext aaa;
//
//    @Pointcut("execution(!private * *(..)) && @annotation(org.osgl.aaa.RequirePermission))")
//    private void requirePermissionMethod() {}
//
//    @Pointcut("execution(!private * *(..)) && @annotation(org.osgl.aaa.RequirePrivilege))")
//    private void requirePrivilegeMethod() {}
//
//    @Pointcut("requirePermissionMethod() || requirePrivilegeMethod()")
//    private void guardedMethod() {}
//
//    @Pointcut("execution(!private * *(..)) && @annotation(org.osgl.aaa.RequirePermission))")
//    private void requirePermissionConstructor() {}
//
//
//    private static Method getMostSpecificMethod(Method method, Class<?> targetClass) {
//        if (method == null || targetClass == null) {
//            return method;
//        }
//        try {
//            String name = method.getName();
//            Class<?>[] types = method.getParameterTypes();
//            method = targetClass.getMethod(name, types);
//        } catch (NoSuchMethodException ex) {
//            // use original method
//        }
//        return method;
//    }
//
//    private _.T2<RequirePermission, RequirePrivilege> getGuardInfo(ProceedingJoinPoint pjp) {
//        MethodSignature signature = (MethodSignature)pjp.getSignature();
//        Method m = signature.getMethod();
//        Class c = signature.getDeclaringType();
//        m = getMostSpecificMethod(m, c);
//
//        RequirePermission perm = m.getAnnotation(RequirePermission.class);
//        RequirePrivilege priv = m.getAnnotation(RequirePrivilege.class);
//
//        return _.T2(perm, priv);
//    }
//
//    @Around("guardedMethod()")
//    public Object methodInvocation(ProceedingJoinPoint pjp) throws Throwable {
//        if (null == aaa) return pjp.proceed();
//
//        _.T2<RequirePermission, RequirePrivilege> guardInfo = getGuardInfo(pjp);
//        RequirePermission perm = guardInfo._1;
//        RequirePrivilege priv = guardInfo._2;
//
//        return null;
//
//    }
}
