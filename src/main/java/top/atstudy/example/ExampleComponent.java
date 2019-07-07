package top.atstudy.example;

import top.atstudy.framework.core.Handler;
import top.atstudy.framework.core.IPlugin;
import top.atstudy.framework.core.Interceptor;
import top.atstudy.framework.starter.ComponentStarter;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Configuration;

@Configuration("com.kuaicto.example.ExampleComponent")
public class ExampleComponent extends ComponentStarter {
    public ExampleComponent() {
    }

    public void init(BeanFactory context) {
    }

    public void configHandler(List<Handler> handlers) {
    }

    public void configInterceptor(List<Interceptor> interceptors) {
    }

    public void configPlugin(List<IPlugin> plugins) {
    }
}
