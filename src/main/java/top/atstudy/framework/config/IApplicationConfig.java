package top.atstudy.framework.config;


import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.BeanFactory;
import top.atstudy.framework.core.Constant;
import top.atstudy.framework.core.Handlers;
import top.atstudy.framework.core.Interceptors;
import top.atstudy.framework.core.Plugins;

public interface IApplicationConfig extends Serializable {
    Constant GLOBAL_CONSTANT = new Constant();

    void configConsts(Constant var1);

    void configHandler(Handlers var1);

    void configInterceptor(Interceptors var1);

    void configPlugin(Plugins var1);

    void configComponent(List<IComponentConfig> var1);

    void init(BeanFactory var1);
}