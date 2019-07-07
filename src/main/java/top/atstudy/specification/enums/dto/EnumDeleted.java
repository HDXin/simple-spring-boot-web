package top.atstudy.specification.enums.dto;

import top.atstudy.specification.enums.base.ILabelCodeEnum;

import java.util.Arrays;

public enum EnumDeleted implements ILabelCodeEnum<EnumDeleted, Integer> {
    NORMAL(0, "正常"),
    DELETED(1, "已删除");

    private Integer code;
    private String label;

    private EnumDeleted(Integer code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public Integer getCode() {
        return this.code;
    }

    public EnumDeleted codeOf(Integer code) {
        return (EnumDeleted)Arrays.stream(values()).filter((v) -> {
            return v.getCode().equals(code);
        }).findFirst().get();
    }
}
