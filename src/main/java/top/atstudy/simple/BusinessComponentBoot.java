package top.atstudy.simple;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Configuration;
import top.atstudy.framework.core.Handler;
import top.atstudy.framework.core.IPlugin;
import top.atstudy.framework.core.Interceptor;
import top.atstudy.framework.starter.ComponentStarter;
import java.util.List;

@Configuration
public class BusinessComponentBoot extends ComponentStarter {

//    private SqlSessionFactory sqlSessionFactory;
    private BeanFactory applicationContext;

    public BusinessComponentBoot() {

    }

    @Override
    public void init(BeanFactory applicationContext) {
        this.applicationContext = applicationContext;
//        this.sqlSessionFactory = applicationContext.getBean(SqlSessionFactory.class);
        this.registryTypeHandler();
    }

    @Override
    public void configHandler(List<Handler> list) {
    }

    @Override
    public void configInterceptor(List<Interceptor> list) {
//    	list.add(new OperatorAwareInterceptor(this.applicationContext.getBean(EmployeeService.class)));
    }

    @Override
    public void configPlugin(List<IPlugin> list) {

    }

    private void registryTypeHandler() {
//        this.sqlSessionFactory.getConfiguration().getTypeHandlerRegistry().register(EnumDeleted.class,NumberCodeEnumTypeHandler.class);
    }
}
