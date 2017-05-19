package com.behzad.servlet;

import com.behzad.service.Service3;

import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.CompletableFuture;

/**
 * Created by behzad on 5/19/17.
 */
@WebServlet(urlPatterns = {"/AsyncServlet2"}, asyncSupported = true)
public class AsyncServlet2 extends HttpServlet {
    @EJB
    private Service3 service;

    @Override
    protected void doGet(HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        final PrintWriter pw = response.getWriter();
        pw.write("<html><body>Started publishing with thread " + Thread.currentThread().getId() + "<br>");
        response.flushBuffer(); // send back to the browser NOW

        CompletableFuture<String> cf = new CompletableFuture<>();
        service.foo(cf);

        // since we need to keep the response open, we need to start an async context
        final AsyncContext ctx = request.startAsync(request, response);
        cf.whenCompleteAsync((s, t) -> {
            try {
                if (t != null) throw t;
                pw.write("written in the future using thread " + Thread.currentThread().getId()
                        + "... service response is:");
                pw.write(s);
                pw.write("</body></html>");
                response.flushBuffer();
                ctx.complete(); // all done, free resources
            } catch (Throwable t2) {

            }
        });
    }
}
