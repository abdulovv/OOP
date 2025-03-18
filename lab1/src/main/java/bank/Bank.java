package bank;

import finance.FinanceAccount;
import finance.Loan;
import finance.Transaction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import users.Client;
import users.User;

import java.util.*;

@Getter
@Setter
public class Bank {
    private final String LOG_FILEPATH = "logs.txt";
    private final String name;
    private final String BIC;
    private int idForNewClient;

    private final List<User> users = new ArrayList<User>();
    private final List<FinanceAccount> accounts = new ArrayList<FinanceAccount>();
    private final List<Loan> loans = new ArrayList<Loan>();
    private final List<Transaction> transactions = new LinkedList<Transaction>();

    private final List<User> waitingRegClients = new ArrayList<User>();

    public Bank(String name, String BIC) {
        this.name = name;
        this.BIC = BIC;
        idForNewClient = 1;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getIdForNewClient() {
        return idForNewClient++;
    }

    public User authorizing(String login, String password) {
        return users.stream().filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password)).findFirst().orElse(null);
    }

    public void addRegisterApplication(User user) {

    }

    public FinanceAccount createAccount(Client client) {
        Random random = new Random();
        int accountID = 0;
        FinanceAccount accountWithOccupiedID;
        do{
            accountID = random.nextInt(11111,99999);
            int finalAccountID = accountID;
            accountWithOccupiedID = accounts.stream().filter(a -> a.getAccountID() == finalAccountID).findFirst().orElse(null);
        }while (accountWithOccupiedID != null);

        int balance = 0;
        int clientID = client.getId();

        FinanceAccount newFinanceAccount = FinanceAccount.builder().accountID(accountID).balance(balance).clientID(clientID).build();

        accounts.add(newFinanceAccount);
        return newFinanceAccount;
    }
}
