package org.dq.test;

import org.dq.test.ex.Usage;
import org.dq.test.ex.framework.HttpRequestRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(HttpRequestRegister.class)
public class TestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);
        Usage usage = context.getBean(Usage.class);
        usage.index();
    }

}
