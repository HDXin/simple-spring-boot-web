package top.atstudy.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.core.Constant;
import top.atstudy.framework.core.Handlers;
import top.atstudy.framework.core.Interceptors;
import top.atstudy.framework.core.Plugins;
import top.atstudy.framework.starter.ApplicationStarter;

import java.util.List;

/**
 * Created by admin on 2017/11/15.
 */
@SpringBootApplication
@EnableConfigurationProperties
public class SpringBoot extends ApplicationStarter {
    private static final Logger logger = LoggerFactory.getLogger(SpringBoot.class);
    public SpringBoot() {
    }

    @Override
    public void configConsts(Constant constant) {
        constant.setEnv("test");
    }

    @Override
    public void configHandler(Handlers handlers) {
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
    }

    @Override
    public void configPlugin(Plugins plugins) {
    }

    @Override
    public void configComponent(List<IComponentConfig> list) {
        list.add(new BusinessComponentBoot());
    }

    @Override
    public void init(BeanFactory applicationContext) {
        super.init(applicationContext);
    }

    public static void main(String[] args) {
        new SpringBoot().runApplication(args);
    }

}