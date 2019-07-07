package top.atstudy.example;

import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.core.Constant;
import top.atstudy.framework.core.Handler;
import top.atstudy.framework.core.Handlers;
import top.atstudy.framework.core.IPlugin;
import top.atstudy.framework.core.Interceptor;
import top.atstudy.framework.core.Interceptors;
import top.atstudy.framework.core.Plugins;
import top.atstudy.framework.starter.ApplicationStarter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

@SpringBootApplication(
        scanBasePackages = {"top.atstudy.example"}
)
public class ExampleBoot extends ApplicationStarter {
    public ExampleBoot() {
    }

    public void configConsts(Constant constant) {
        constant.setDebug(false);
    }

    public void configHandler(Handlers handlers) {
        handlers.add(new Handler() {
            public void handle(String url, HttpServletRequest request, HttpServletResponse response) throws Exception {
                System.out.println("this is CustomHandler");
                super.next.handle(url, request, response);
            }
        });
    }

    public void configInterceptor(Interceptors interceptors) {
        interceptors.add(new Interceptor() {
            public void before(HttpServletRequest request, HttpServletResponse response, HandlerMethod action) {
                System.out.println("begin interceptor");
            }

            public void after(HttpServletRequest request, HttpServletResponse response, HandlerMethod action, ModelAndView result) {
                System.out.println("end interceptor");
            }
        });
    }

    public void configPlugin(Plugins plugins) {
        plugins.add(new IPlugin() {
            public void start() {
                System.out.println("Plugin AAA start");
            }

            public void stop() {
                System.out.println("Plugin AAA stop");
            }
        });
        plugins.add(new IPlugin() {
            public void start() {
                System.out.println("Plugin BBB start");
            }

            public void stop() {
                System.out.println("Plugin BBB stop");
            }
        });
    }

    public void configComponent(List<IComponentConfig> components) {
        components.add(new ExampleComponent());
    }

    public void init(BeanFactory applicationContext) {
        super.init(applicationContext);
    }

    public static void main(String[] args) {
        (new ExampleBoot()).runApplication(args);
    }
}
