package finance;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FinanceAccount {
    private int accountID;
    private int clientID;
    private long balance;
    private boolean isActive = true;

    public void topUpBalance(long sumOfMoney) {
        if (isActive) {
            balance += sumOfMoney;
        }else
            System.out.println("ОШИБКА, счет " + accountID + " заморожен");
    }

    @Override
    public String toString() {
        return "accountID: " + accountID + "\n clientID: " + clientID + "\n balance: " + balance + "\n";
    }

    public FinanceAccount(int accountID, int clientID, long balance, boolean isActive) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.balance = balance;
        this.isActive = isActive;
    }
}
