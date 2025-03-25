package bank;

import finance.Applications;
import finance.FinanceAccount;
import finance.Loan;
import finance.Transaction;
import lombok.Getter;
import lombok.Setter;
import users.Client;
import users.User;

import java.time.Instant;
import java.util.*;

@Getter
@Setter
public class Bank {
    private final String LOG_FILEPATH = "logs.txt";
    private final String name;
    private final String BIC;
    private int idForNewClient;
    private int idForNewTransaction;

    private final List<User> users = new ArrayList<User>();
    private final List<FinanceAccount> accounts = new ArrayList<FinanceAccount>();
    private final List<Transaction> transactions = new LinkedList<Transaction>();
    private final List<Applications> applications = new ArrayList<>();

    private final List<User> waitingRegClients = new ArrayList<User>();

    public Bank(String name, String BIC) {
        this.name = name;
        this.BIC = BIC;
        idForNewClient = 1;
        idForNewTransaction = 1000;
    }

    @Override
    public String toString() {
        return name;
    }

    public int getIdForNewClient() {
        return idForNewClient++;
    }

    public int getIdForNewTransaction() {
        return idForNewTransaction++;
    }

    public User authorizing(String login, String password) {
        return users.stream().filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password)).findFirst().orElse(null);
    }

    public void addRegisterApplication(User user) {
        getWaitingRegClients().add((Client) user);
    }

    public FinanceAccount openAccount(Client client) {
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
        System.out.println("-----------------------\nНовый счет был открыт(ID:"+ newFinanceAccount.getAccountID() +")\n-----------------------");

        return newFinanceAccount;
    }

    public void performTransaction(Transaction transaction) {
        System.out.println("\n======================================");
        if (transaction.getAccountFrom().getBalance() < transaction.getAmount()){
            System.out.println("Недостаточно средств на счету (ID "+ transaction.getAccountFrom().getAccountID() + ")");
        }else if (transaction.getAccountFrom().isFrozen()){
            System.out.println("Счет с которого осуществляется перевод заморожен (ID "+ transaction.getAccountTo().getAccountID() + ")");
        }else if(transaction.getAccountTo().isFrozen()){
            System.out.println("Счет на который осуществляется перевод заморожен (ID "+ transaction.getAccountFrom().getAccountID() + ")");
        }else if (transaction.getAccountFrom().isLocked()){
            System.out.println("Счет с которого осуществляется перевод заблокирован (ID "+ transaction.getAccountTo().getAccountID() + ")");
        }else if (transaction.getAccountTo().isLocked()){
            System.out.println("Счет на который осуществляется перевод заблокирован (ID "+ transaction.getAccountFrom().getAccountID() + ")");
        }else {
            String name1 = (users.stream().filter(u -> u.getId() == transaction.getAccountFrom().getClientID()).findFirst().get().getFullName());
            String name2 = (users.stream().filter(u -> u.getId() == transaction.getAccountTo().getClientID()).findFirst().get().getFullName());
            String date = Instant.now().toString();
            int id = getIdForNewTransaction();

            transaction.setInfo("Transaction ID:" + id + "\n" + name1 + ", ID " + transaction.getAccountFrom().getAccountID() + " --(" + date + ", amount: " + transaction.getAmount() + ")--> " + name2 + ", ID " + transaction.getAccountTo().getAccountID());

            transaction.setId(id);
            transaction.getAccountFrom().decreaseBalance(transaction.getAmount());
            transaction.getAccountTo().increaseBalance(transaction.getAmount());

            System.out.println("Транзакция выполнена успешно!");
            transactions.add(transaction);
        }
        System.out.println("======================================\n");
    }

    public void cancelTransaction(Transaction transaction) {
        transaction.getAccountFrom().increaseBalance(transaction.getAmount());
        transaction.getAccountTo().decreaseBalance(transaction.getAmount());

        System.out.println("======================================\n");
        System.out.println("Транзакция (ID " + transaction.getId() + ") была отменена!");
        System.out.println("======================================");
        transactions.remove(transaction);
    }

    public Client findClientByID(int clientID) {
        return (Client) (users.stream().filter(u -> u.getId() == clientID).findFirst().orElse(null));
    }

    public void closeAccount(FinanceAccount financeAccount) {
        FinanceAccount mainFinanceAccount = findClientByID(financeAccount.getClientID()).getMainAccount();
        mainFinanceAccount.increaseBalance(financeAccount.getBalance());

        accounts.remove(financeAccount);
        System.out.println("======================================\n");
        System.out.println("Счет (ID " + financeAccount.getAccountID() + ") успешно закрыт!");
        System.out.println("======================================");
    }

}
