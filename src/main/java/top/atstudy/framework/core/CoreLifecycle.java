package top.atstudy.framework.core;

import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.starter.Starter;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class CoreLifecycle extends CoreMetaData {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Plugins plugins = new Plugins();
    private ApplicationContext applicationContext;

    public CoreLifecycle(IApplicationConfig systemConfig, IApplicationConfig applicationConfig, List<IComponentConfig> componentList) {
        super(systemConfig, applicationConfig, componentList);
    }

    @Autowired
    public CoreLifecycle setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        Starter.setSpringApplicationContext(this.applicationContext);
        return this;
    }

    @PostConstruct
    public void init() {
        this.logger.info("Application Context init finish.");
        this.assemblyPlugin();
        this.plugins.startPlugin();
    }

    @PreDestroy
    public void destroy() {
        this.logger.info("Application Destroy.");
        this.plugins.stopPlugin();
    }

    private void assemblyPlugin() {
        Iterator var1 = this.componentList.iterator();

        while(var1.hasNext()) {
            IComponentConfig iComponentConfig = (IComponentConfig)var1.next();
            iComponentConfig.assemblyPlugin();
        }

        this.systemConfig.configPlugin(this.plugins);
        this.applicationConfig.configPlugin(this.plugins);
    }
}
