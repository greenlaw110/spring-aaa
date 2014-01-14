package org.osgl.aaa.spring;

import org.osgl.aaa.AAA;
import org.osgl.aaa.DynamicPermissionCheckHelper;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by luog on 15/01/14.
 */
public abstract class DynamicPermissionCheckHelperBean<T> implements DynamicPermissionCheckHelper<T>, InitializingBean {

    protected abstract Class<T> getTargetClass();

    @Override
    public void afterPropertiesSet() throws Exception {
        AAA.registerDynamicPermissionChecker(this, getTargetClass());
    }
}
