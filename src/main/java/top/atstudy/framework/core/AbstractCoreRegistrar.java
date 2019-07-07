package top.atstudy.framework.core;

import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.config.IComponentConfig;
import java.util.List;

public abstract class AbstractCoreRegistrar extends CoreMetaData {
    public AbstractCoreRegistrar(IApplicationConfig systemConfig, IApplicationConfig applicationConfig, List<IComponentConfig> componentList) {
        super(systemConfig, applicationConfig, componentList);
    }

    public abstract Object registry();
}
