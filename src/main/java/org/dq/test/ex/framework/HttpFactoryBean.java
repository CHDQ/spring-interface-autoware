package org.dq.test.ex.framework;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

public class HttpFactoryBean implements FactoryBean {
    private String url;
    private Class<?> type;

    @Override
    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(this.type.getClassLoader(), new Class[]{this.type}, new HttpProxyHandler(url));
    }

    @Override
    public Class<?> getObjectType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
