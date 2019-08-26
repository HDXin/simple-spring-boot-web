package top.atstudy.framework.enums;

import top.atstudy.framework.spec.enums.base.ICodeEnum;

public enum EnumEnv implements ICodeEnum<EnumEnv, String> {
    DEV("dev"),
    TEST("test"),
    DEPLOY("deploy");

    private String code;

    private EnumEnv(String code) {
        this.code = code;
    }

    public String getCode() {
        return null;
    }

    public EnumEnv codeOf(String code) {
        return null;
    }
}

