package top.atstudy.framework.interceptor;

import top.atstudy.framework.controller.BasicController;
import top.atstudy.framework.core.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

public class RequestContextInterceptor extends Interceptor {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RequestContextInterceptor() {
    }

    public void before(HttpServletRequest request, HttpServletResponse response, HandlerMethod action) {
        Object bean = action.getBean();
        if (bean instanceof BasicController) {
            BasicController controller = (BasicController)bean;
            controller.setRequest(request);
            controller.setResponse(response);
        }

    }

    public void after(HttpServletRequest request, HttpServletResponse response, HandlerMethod action, ModelAndView result) {
        System.out.println("");
    }
}