package top.atstudy.framework.starter;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

public abstract class Starter {
    private static ApplicationContext springApplicationContext;
    private static DefaultListableBeanFactory defaultListableBeanFactory;
    public static BeanFactory beanFactory;

    public Starter() {
    }

    public static ApplicationContext getSpringApplicationContext() {
        return springApplicationContext;
    }

    public static void setSpringApplicationContext(ApplicationContext springApplicationContext) {
        if (springApplicationContext == null) {
            springApplicationContext = springApplicationContext;
            defaultListableBeanFactory = (DefaultListableBeanFactory)springApplicationContext.getAutowireCapableBeanFactory();
        }

    }

    public static DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return defaultListableBeanFactory;
    }
}
