package org.dq.test.ex.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.http.HttpClient;

public class HttpProxyHandler implements InvocationHandler {
    private String url;

    HttpProxyHandler(String url) {
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HttpRequest annotation = method.getAnnotation(HttpRequest.class);
        System.out.println(url + annotation.value());//这里没有做多余操作，可以在该处远程调用等操作
        return null;
    }
}
