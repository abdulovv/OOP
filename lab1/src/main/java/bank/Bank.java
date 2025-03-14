package bank;

import finance.FinanceAccount;
import finance.Loan;
import finance.Transaction;
import lombok.Getter;
import users.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Getter
public class Bank {
    private final String LOG_FILEPATH = "logs.txt";
    private final String name;
    private final String BIC;
    private int idForNewClient;

    private final List<User> users = new ArrayList<User>();
    private final List<FinanceAccount> accounts = new ArrayList<FinanceAccount>();
    private final List<Loan> loans = new ArrayList<Loan>();
    private final List<Transaction> transactions = new LinkedList<Transaction>();

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
}
