package top.atstudy.framework.core;

import top.atstudy.framework.kit.LoggerKit;

public class Constant {
    public static final String SPRING_ENV_CMD = "--spring.profiles.active";
    private String env = "dev";
    private Boolean debug = false;
    private String logFilePath;
    private String logMaxHistory;

    public Constant() {
        this.setLogFilePath(System.getProperty("user.dir"));
        this.setLogMaxHistory("3");
    }

    public static String getSpringEnvCmd() {
        return "--spring.profiles.active";
    }

    public String getEnv() {
        return this.env;
    }

    public Constant setEnv(String env) {
        this.env = env;
        return this;
    }

    public Boolean getDebug() {
        return this.debug;
    }

    public Constant setDebug(Boolean debug) {
        this.debug = debug;
        return this;
    }

    public String getLogFilePath() {
        return this.logFilePath;
    }

    public Constant setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath == null ? null : logFilePath.trim();
        LoggerKit.pushLogFilePath(this.logFilePath);
        return this;
    }

    public Constant setLogMaxHistory(String logMaxHistory) {
        this.logMaxHistory = logMaxHistory == null ? null : logMaxHistory.trim();
        LoggerKit.pushLogMaxHistory(this.logMaxHistory);
        return this;
    }
}
