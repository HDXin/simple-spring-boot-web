package top.atstudy.specification.enums.base;

public interface ICodeEnum<T extends Enum<T>, C> {
    C getCode();

    T codeOf(C var1);

    static ICodeEnum codeOf(Enum instance, Object code) {
        ICodeEnum sub = (ICodeEnum)instance;
        return (ICodeEnum)sub.codeOf(code);
    }
}
