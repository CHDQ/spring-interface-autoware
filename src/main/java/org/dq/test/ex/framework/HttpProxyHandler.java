package org.dq.test.ex.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.http.HttpClient;

public class HttpProxyHandler implements InvocationHandler {
    private String url;

    public HttpProxyHandler(String url) {
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HttpRequest annotation = method.getAnnotation(HttpRequest.class);
        System.out.println(url + annotation.value());
        return null;
    }
}
