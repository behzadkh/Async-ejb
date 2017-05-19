package com.behzad.service;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.CompletableFuture;

/**
 * Created by behzad on 5/19/17.
 */
@Stateless
public class Service3 {
    @Asynchronous
    public void foo(CompletableFuture<String> cf) {
        // simulate some long running process
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cf.complete("bar");
    }
}
