package org.dq.test.ex;

import org.dq.test.ex.framework.HttpRequest;
import org.dq.test.ex.framework.HttpService;

@HttpService("127.0.0.1:8080")
public interface Usage {
    @HttpRequest("/index")
    void index();
}
