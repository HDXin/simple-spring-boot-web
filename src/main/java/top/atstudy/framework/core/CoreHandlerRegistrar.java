package top.atstudy.framework.core;

import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.support.ReentrantRequestWrapper;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

public class CoreHandlerRegistrar extends AbstractCoreRegistrar {
    public CoreHandlerRegistrar(IApplicationConfig systemConfig, IApplicationConfig applicationConfig, List<IComponentConfig> componentList) {
        super(systemConfig, applicationConfig, componentList);
    }

    public Object registry() {
        return this.registryFilters();
    }

    public FilterRegistrationBean<Filter> registryFilters() {
        Filter filter = new CoreHandlerRegistrar.CoreFilter(this.systemConfig, this.applicationConfig, this.componentList);
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean();
        bean.setFilter(filter);
        return bean;
    }

    public static class CoreFilter implements Filter {
        private final IApplicationConfig systemConfig;
        private final IApplicationConfig applicationConfig;
        private final List<IComponentConfig> componentList;

        public CoreFilter(IApplicationConfig systemConfig, IApplicationConfig applicationConfig, List<IComponentConfig> componentList) {
            this.systemConfig = systemConfig;
            this.applicationConfig = applicationConfig;
            this.componentList = componentList;
        }

        public void init(FilterConfig filterConfig) throws ServletException {
        }

        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            Handlers handlers = new Handlers();
            this.assemblyHandlers(handlers);
            Handlers threadHandlers = handlers.copyAndPushPoison(chain);
            HttpServletRequest req = (HttpServletRequest)request;
            HttpServletResponse resp = (HttpServletResponse)response;
            ReentrantRequestWrapper reentrantRequestWrapper = new ReentrantRequestWrapper(req);

            try {
                threadHandlers.head.handle(req.getRequestURI(), reentrantRequestWrapper, resp);
            } catch (Exception var10) {
                var10.printStackTrace();
            }

        }

        public void destroy() {
        }

        private void assemblyHandlers(Handlers handlers) {
            Iterator var2 = this.componentList.iterator();

            while(var2.hasNext()) {
                IComponentConfig iComponentConfig = (IComponentConfig)var2.next();
                iComponentConfig.assemblyHandler();
            }

            this.systemConfig.configHandler(handlers);
            this.applicationConfig.configHandler(handlers);
        }
    }
}