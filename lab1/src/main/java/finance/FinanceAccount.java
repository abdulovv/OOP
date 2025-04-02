package finance;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import users.Client;

@Builder
@Getter
@Setter
public class FinanceAccount {
    private int accountID;
    private Client client;
    private long balance;
    private boolean isFrozen = false;
    private boolean isLocked = false;

    @Override
    public String toString() {
        return " accountID: " + accountID + "\n client: (id)" + client.getId() + " (full name)" + client.getFullName() + "\n balance: " + balance + "\n isFrozen: " + isFrozen + "\n isLocked: " + isLocked + "\n";
    }

    public FinanceAccount(int accountID, Client client, long balance, boolean isFrozen, boolean isLocked) {
        this.accountID = accountID;
        this.client = client;
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
