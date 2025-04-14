package finance;

import enterprise.Enterprise;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnterpriseTransaction extends Transaction {
    private FinanceAccount clientAccount;
    private Enterprise enterprise;
    private long amount;
    private int ID;

    public EnterpriseTransaction(FinanceAccount clientAccount, Enterprise enterprise, long amount, int ID) {
        super();
        this.clientAccount = clientAccount;
        this.enterprise = enterprise;
        this.amount = amount;
        this.ID = ID;
    }
}
