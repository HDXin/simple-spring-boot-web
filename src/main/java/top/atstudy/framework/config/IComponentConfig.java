package top.atstudy.framework.config;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import top.atstudy.framework.core.Handler;
import top.atstudy.framework.core.IPlugin;
import top.atstudy.framework.core.Interceptor;

public interface IComponentConfig extends Serializable {
    default void assemblyComponent() {
        this.assemblyHandler();
        this.assemblyInterceptor();
        this.assemblyPlugin();
    }

    void assemblyHandler();

    void assemblyInterceptor();

    void assemblyPlugin();

    void init(BeanFactory var1);

    void configHandler(List<Handler> var1);

    void configInterceptor(List<Interceptor> var1);

    void configPlugin(List<IPlugin> var1);
}

