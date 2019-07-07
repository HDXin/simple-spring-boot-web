package top.atstudy.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.config.IComponentConfig;
import java.util.List;

public abstract class CoreMetaData {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected IApplicationConfig applicationConfig;
    protected IApplicationConfig systemConfig;
    protected List<IComponentConfig> componentList;

    public CoreMetaData(IApplicationConfig systemConfig, IApplicationConfig applicationConfig, List<IComponentConfig> componentList) {
        this.applicationConfig = applicationConfig;
        this.systemConfig = systemConfig;
        this.componentList = componentList;
    }
}

