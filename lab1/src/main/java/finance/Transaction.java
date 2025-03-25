package finance;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Transaction {
    private FinanceAccount accountFrom;
    private FinanceAccount accountTo;
    private int amount;

    private int id;
    private String info;

    public Transaction(FinanceAccount accountFrom, FinanceAccount accountTo, int amount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.amount = amount;
    }
}
