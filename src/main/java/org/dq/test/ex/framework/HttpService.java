package org.dq.test.ex.framework;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpService {
    String value();
}
