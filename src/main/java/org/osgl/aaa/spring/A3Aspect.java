package org.osgl.aaa.spring;

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
