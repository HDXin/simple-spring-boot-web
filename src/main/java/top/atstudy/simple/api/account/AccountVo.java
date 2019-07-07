package top.atstudy.simple.api.account;

public class AccountVo {

    private Long accountId;

    private String accountName;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    @Override
    public String toString() {
        return "AccountVo{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                '}';
    }
}
