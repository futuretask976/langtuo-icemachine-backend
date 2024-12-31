package com.langtuo.teamachine.web;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * @author Jiaqing
 */
@Component
public class TeaMachineDisposableBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("teaMachineDisposableBean|destroy|entering");
        System.out.println("teaMachineDisposableBean|destroy|exiting");
    }
}
