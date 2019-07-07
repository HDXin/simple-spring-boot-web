package top.atstudy.framework.core;

import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.starter.ApplicationStarter;
import java.util.ArrayList;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class CoreEngineStack extends CoreMetaData {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private BeanDefinitionRegistry beanDefinitionRegistry;

    public CoreEngineStack() throws IllegalAccessException, InstantiationException {
        super(CoreBoot.me, ApplicationStarter.CONFIG_BOOT, new ArrayList());
        this.assemblyComponent();
    }

    private void assemblyComponent() {
        this.systemConfig.configComponent(this.componentList);
        this.applicationConfig.configComponent(this.componentList);
    }

    private void assemblyInit(BeanFactory beanFactory) {
        this.systemConfig.init(beanFactory);
        Iterator var2 = this.componentList.iterator();

        while(var2.hasNext()) {
            IComponentConfig iComponentConfig = (IComponentConfig)var2.next();
            iComponentConfig.init(beanFactory);
        }

        this.applicationConfig.init(beanFactory);
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer sourcesBeanFactoryPostProcessor() {
        CoreResourcesConfigRegistrar resourcesConfigRegistrar = new CoreResourcesConfigRegistrar(this.systemConfig, this.applicationConfig, this.componentList);
        return (PropertySourcesPlaceholderConfigurer)resourcesConfigRegistrar.registry();
    }

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        CoreHandlerRegistrar handlerRegistrar = new CoreHandlerRegistrar(this.systemConfig, this.applicationConfig, this.componentList);
        CoreWebMvcConfigurerRegistrar webMvcConfigurerRegistrar = new CoreWebMvcConfigurerRegistrar(this.systemConfig, this.applicationConfig, this.componentList);
        return (bf) -> {
            this.beanDefinitionRegistry = (BeanDefinitionRegistry)bf;
            BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry)bf;
            this.registryLifecycle(beanFactory);
            bf.registerSingleton("coreHandlerRegistrar", handlerRegistrar.registry());
            bf.registerSingleton("coreWebMvcConfigurerRegistrar", webMvcConfigurerRegistrar.registry());
        };
    }

    @Bean
    public BeanPostProcessor servletAfterInitialization() {
        return new BeanPostProcessor() {
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (beanName.equalsIgnoreCase("dispatcherServlet")) {
                    CoreEngineStack.this.assemblyInit((BeanFactory)CoreEngineStack.this.beanDefinitionRegistry);
                }

                return bean;
            }
        };
    }

    private void registryLifecycle(BeanDefinitionRegistry beanFactory) {
        BeanDefinition beanDefinition = this.buildCoreBridge(CoreLifecycle.class);
        beanFactory.registerBeanDefinition("beanDefCoreLifecycleBridge", beanDefinition);
    }

    private BeanDefinition buildCoreBridge(Class<?> coreBrideCls) {
        return BeanDefinitionBuilder.genericBeanDefinition(coreBrideCls).addConstructorArgValue(this.systemConfig).addConstructorArgValue(this.applicationConfig).addConstructorArgValue(this.componentList).getBeanDefinition();
    }
}
