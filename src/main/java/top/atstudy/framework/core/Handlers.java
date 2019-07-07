package top.atstudy.framework.core;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Handlers {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Handler head;
    protected Handler tail;

    public Handlers() {
    }

    public Handlers add(Handler handler) {
        Handler proxyHandler = new HandlerPatternProxy(handler);
        if (this.head == null) {
            this.head = proxyHandler;
        }

        proxyHandler.prev = this.tail;
        if (this.tail != null) {
            this.tail.next = proxyHandler;
        }

        this.tail = proxyHandler;
        handler.prev = proxyHandler.prev;
        if (handler.prev != null) {
            ((HandlerPatternProxy)((HandlerPatternProxy)proxyHandler.prev)).target.next = proxyHandler;
        }

        return this;
    }

    public Handlers copyAndPushPoison(final FilterChain chain) {
        Handlers handlers = this.copyHandlers();
        handlers.add(new Handler() {
            public void handle(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
                chain.doFilter(request, response);
            }
        });
        return handlers;
    }

    private Handlers copyHandlers() {
        Handlers handlers = new Handlers();
        handlers.head = this.head;
        HandlerPatternProxy temp = new HandlerPatternProxy(((HandlerPatternProxy)this.tail).target);
        temp.prev = this.tail.prev;
        handlers.tail = temp;
        return handlers;
    }
}
