package org.dq.test.ex.framework;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpRequest {
    String value();
}
