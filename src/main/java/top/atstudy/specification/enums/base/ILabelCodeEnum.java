package top.atstudy.specification.enums.base;

public interface ILabelCodeEnum<T extends Enum<T>, C> extends ICodeEnum<T, C> {
    String getLabel();
}
