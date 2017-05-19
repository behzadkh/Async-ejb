package com.behzad.service;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.Future;

/**
 * Created by behzad on 5/19/17.
 */
@Stateless
public class Service2 {

    @Asynchronous
    public Future<String> foo(String s) {
        // simulate some long running process
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        s += "<br>Service2: threadId=" + Thread.currentThread().getId();
        return new AsyncResult<String>(s);
    }
}
