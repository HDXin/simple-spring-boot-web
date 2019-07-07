package top.atstudy.framework.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Plugins {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<IPlugin> pluginList = new ArrayList();

    public Plugins() {
    }

    public Plugins add(IPlugin plugin) {
        this.pluginList.add(plugin);
        return this;
    }

    public void startPlugin() {
        this.logger.debug("plugins starting...");
        Iterator var1 = this.pluginList.iterator();

        while(var1.hasNext()) {
            IPlugin plugin = (IPlugin)var1.next();
            plugin.start();
        }

    }

    public void stopPlugin() {
        Collections.reverse(this.pluginList);
        this.logger.debug("plugins in the stop...");
        Iterator var1 = this.pluginList.iterator();

        while(var1.hasNext()) {
            IPlugin plugin = (IPlugin)var1.next();
            plugin.stop();
        }

    }
}
