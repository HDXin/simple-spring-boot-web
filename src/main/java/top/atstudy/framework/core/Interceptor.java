package top.atstudy.framework.core;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

public abstract class Interceptor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected Interceptor prev;
    protected Interceptor next;

    public Interceptor() {
    }

    public abstract void before(HttpServletRequest var1, HttpServletResponse var2, HandlerMethod var3);

    public abstract void after(HttpServletRequest var1, HttpServletResponse var2, HandlerMethod var3, ModelAndView var4);
}