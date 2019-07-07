package top.atstudy.specification.aware;

public abstract class AbstractOperator implements IOperatorAware {
    private Long operatorId;
    private String operatorName;
    private String operationId;

    public AbstractOperator() {
    }

    public Long getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return this.operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
    }

    public String getOperationId() {
        return this.operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId == null ? null : operationId.trim();
    }
}