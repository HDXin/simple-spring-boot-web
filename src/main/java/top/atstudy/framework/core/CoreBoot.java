package top.atstudy.framework.core;


import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.handler.FrameworkDiagnoseDataHandler;
import top.atstudy.framework.handler.FrameworkExceptionHandler;
import top.atstudy.framework.handler.GatewayMediaFetchHandler;
import top.atstudy.framework.handler.LoggerHandler;
import top.atstudy.framework.interceptor.RequestContextInterceptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;

public class CoreBoot implements IApplicationConfig {
    public static final CoreBoot me = new CoreBoot();
    private final List<Handler> componentHandlers = new ArrayList();
    private final List<IPlugin> componentPlugins = new ArrayList();
    private final List<Interceptor> componentInterceptors = new ArrayList();

    public CoreBoot() {
        this.configConsts(IApplicationConfig.GLOBAL_CONSTANT);
    }

    public void init(BeanFactory applicationContext) {
    }

    public void configConsts(Constant constant) {
        constant.setEnv("dev");
    }

    public void configHandler(Handlers handlers) {
        handlers.add(new LoggerHandler());
        handlers.add(new FrameworkExceptionHandler());
        handlers.add(new FrameworkDiagnoseDataHandler());
        handlers.add(new GatewayMediaFetchHandler());
        Iterator var2 = this.componentHandlers.iterator();

        while(var2.hasNext()) {
            Handler handler = (Handler)var2.next();
            handlers.add(handler);
        }

    }

    public void configInterceptor(Interceptors interceptors) {
        interceptors.add(new RequestContextInterceptor());
        Iterator var2 = this.componentInterceptors.iterator();

        while(var2.hasNext()) {
            Interceptor interceptor = (Interceptor)var2.next();
            interceptors.add(interceptor);
        }

    }

    public void configPlugin(Plugins plugins) {
        Iterator var2 = this.componentPlugins.iterator();

        while(var2.hasNext()) {
            IPlugin plugin = (IPlugin)var2.next();
            plugins.add(plugin);
        }

    }

    public void configComponent(List<IComponentConfig> components) {
    }

    public void attachHandler(Handler handler) {
        this.componentHandlers.add(handler);
    }

    public void attachInterceptor(Interceptor interceptor) {
        this.componentInterceptors.add(interceptor);
    }

    public void attachPlugin(IPlugin plugin) {
        this.componentPlugins.add(plugin);
    }

    public void attachPlugin(List<IPlugin> plugins) {
        this.componentPlugins.addAll(plugins);
    }

    public void attachInterceptor(List<Interceptor> interceptors) {
        this.componentInterceptors.addAll(interceptors);
    }

    public void attachHandler(List<Handler> handlers) {
        this.componentHandlers.addAll(handlers);
    }
}