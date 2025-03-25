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
    private boolean isFrozen = false;
    private boolean isLocked = false;

    @Override
    public String toString() {
        return " accountID: " + accountID + "\n clientID: " + clientID + "\n balance: " + balance + "\n isFrozen: " + isFrozen + "\n isLocked: " + isLocked + "\n";
    }

    public FinanceAccount(int accountID, int clientID, long balance, boolean isFrozen, boolean isLocked) {
        this.accountID = accountID;
        this.clientID = clientID;
        this.balance = balance;
        this.isFrozen = isFrozen;
        this.isLocked = isLocked;
    }

    public void increaseBalance(long amount) {
        this.balance += amount;
    }

    public void decreaseBalance(long amount) {
        this.balance -= amount;
    }
}
