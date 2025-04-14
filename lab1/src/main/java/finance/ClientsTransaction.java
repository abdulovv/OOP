package finance;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsTransaction extends Transaction {
    private FinanceAccount accountFrom;
    private FinanceAccount accountTo;
    private int amount;

    private int id;
    private String info;

    public ClientsTransaction(FinanceAccount accountFrom, FinanceAccount accountTo, int amount) {
        super();
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }
}
