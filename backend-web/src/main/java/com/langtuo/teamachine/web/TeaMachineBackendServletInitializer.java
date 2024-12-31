package com.langtuo.teamachine.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 当前类用于在 Tomcat 中启动
 * @author Jiaqing
 */
@Slf4j
public class TeaMachineBackendServletInitializer extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("configure|entering");
        }));
        return builder.sources(TeaMachineBackendApplication.class);
    }
}
