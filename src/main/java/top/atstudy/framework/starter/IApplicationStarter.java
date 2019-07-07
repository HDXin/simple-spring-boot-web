package top.atstudy.framework.starter;

import org.springframework.boot.SpringApplication;

public interface IApplicationStarter {
    static void runApplication(ApplicationStarter configBoot, String[] args) {
        ApplicationStarter.CONFIG_BOOT = configBoot;
        ApplicationStarter.CONFIG_BOOT_CLASSES = configBoot.getClass();
        SpringApplication.run(ApplicationStarter.CONFIG_BOOT_CLASSES, args);
    }

    void runApplication(String[] var1);
}
