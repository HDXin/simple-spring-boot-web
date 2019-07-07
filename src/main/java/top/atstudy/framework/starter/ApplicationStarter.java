package top.atstudy.framework.starter;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.ComponentScan;
import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.core.Constant;
import top.atstudy.framework.core.Handlers;
import top.atstudy.framework.core.Interceptors;
import top.atstudy.framework.core.Plugins;

@ComponentScan(
        basePackages = {"top.atstudy.framework"}
)
public abstract class ApplicationStarter extends Starter implements IApplicationStarter, IApplicationConfig {
    public static Class<? extends ApplicationStarter> CONFIG_BOOT_CLASSES;
    public static ApplicationStarter CONFIG_BOOT;

    public ApplicationStarter() {
        this.configConsts(IApplicationConfig.GLOBAL_CONSTANT);
    }

    public void runApplication(String[] args) {
        String[] var10000 = new String[1];
        StringBuilder var10003 = new StringBuilder();
        Constant var10004 = IApplicationConfig.GLOBAL_CONSTANT;
        var10000[0] = var10003.append("--spring.profiles.active").append("=").append(IApplicationConfig.GLOBAL_CONSTANT.getEnv()).toString();
        String[] custom = var10000;
        String[] runerArgs = (String[])ArrayUtils.addAll(args, custom);
        Arrays.stream(runerArgs).forEach((v) -> {
            String[] arg = v.split("=");
            if (arg.length > 1) {
                System.setProperty(arg[0].replace("--", ""), arg[1]);
            }

        });
        IApplicationStarter.runApplication(this, runerArgs);
    }

    public void configConsts(Constant constant) {
    }

    public void configHandler(Handlers handlers) {
    }

    public void configInterceptor(Interceptors interceptors) {
    }

    public void configPlugin(Plugins plugins) {
    }

    public void configComponent(List<IComponentConfig> components) {
    }

    public void init(BeanFactory applicationContext) {
    }
}
