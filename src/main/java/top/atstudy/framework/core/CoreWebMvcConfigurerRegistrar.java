package top.atstudy.framework.core;

import top.atstudy.framework.config.IApplicationConfig;
import top.atstudy.framework.config.IComponentConfig;
import top.atstudy.framework.support.JsonHttpMessageConvert;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CoreWebMvcConfigurerRegistrar extends AbstractCoreRegistrar {
    public CoreWebMvcConfigurerRegistrar(IApplicationConfig systemConfig, IApplicationConfig applicationConfig, List<IComponentConfig> componentList) {
        super(systemConfig, applicationConfig, componentList);
    }

    public Object registry() {
        return this.registryWebMvcConfigurer();
    }

    public WebMvcConfigurer registryWebMvcConfigurer() {
        this.logger.info("Application start registry WebConfigurer.");
        return new WebMvcConfigurer() {
            public void addInterceptors(InterceptorRegistry registry) {
                CoreWebMvcConfigurerRegistrar.this.registryInterceptors(registry);
            }

            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                for(int i = converters.size() - 1; i >= 0; --i) {
                    HttpMessageConverter<?> converter = (HttpMessageConverter)converters.get(i);
                    if (converter instanceof MappingJackson2HttpMessageConverter) {
                        converters.remove(converter);
                    }
                }

                converters.add(new JsonHttpMessageConvert());
            }
        };
    }

    private void registryInterceptors(InterceptorRegistry registry) {
        final Interceptors interceptors = this.assemblyInterceptors();
        registry.addInterceptor(new HandlerInterceptor() {
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if (!(handler instanceof HandlerMethod)) {
                    return true;
                } else {
                    HandlerMethod action = (HandlerMethod)handler;
                    if (action.getBean() instanceof BasicErrorController) {
                        return true;
                    } else {
                        for(Interceptor item = interceptors.head; item != null; item = item.next) {
                            item.before(request, response, action);
                        }

                        return true;
                    }
                }
            }

            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
                if (handler instanceof HandlerMethod) {
                    HandlerMethod action = (HandlerMethod)handler;
                    if (!(action.getBean() instanceof BasicErrorController)) {
                        for(Interceptor item = interceptors.tail; item != null; item = item.prev) {
                            item.after(request, response, action, modelAndView);
                        }

                    }
                }
            }

            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
            }
        });
    }

    private Interceptors assemblyInterceptors() {
        Interceptors interceptors = new Interceptors();
        Iterator var2 = this.componentList.iterator();

        while(var2.hasNext()) {
            IComponentConfig iComponentConfig = (IComponentConfig)var2.next();
            iComponentConfig.assemblyInterceptor();
        }

        this.systemConfig.configInterceptor(interceptors);
        this.applicationConfig.configInterceptor(interceptors);
        return interceptors;
    }
}
