package bank;

import finance.Applications;
import finance.FinanceAccount;
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
    private final String name;
    private final String BIC;
    private int idForNewClient;
    private int idForNewTransaction;
    private String path;

    private final static List<Integer> occupiedID = new ArrayList<Integer>();

    private final List<User> users = new ArrayList<User>();
    private final List<FinanceAccount> accounts = new ArrayList<FinanceAccount>();
    private final List<Transaction> transactions = new LinkedList<Transaction>();
    private final List<Applications> applications = new ArrayList<>();

    private final List<User> waitingRegClients = new ArrayList<User>();

    public Bank(String name, String BIC, String path) {
        this.name = name;
        this.BIC = BIC;
        this.path = path;
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

        FinanceAccount newFinanceAccount = FinanceAccount.builder().accountID(generateIDForAccount()).balance(0).client(client).build();

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
            String name1 = transaction.getAccountFrom().getClient().getFullName();
            String name2 = transaction.getAccountTo().getClient().getFullName();
            String date = Instant.now().toString();
            int id = getIdForNewTransaction();

            transaction.setInfo("Transaction ID:" + id + "\n" + name1 + ", ID " + transaction.getAccountFrom().getAccountID() + " --(" + date + ", amount: " + transaction.getAmount() + ")--> " + name2 + ", ID " + transaction.getAccountTo().getAccountID());

            transaction.setId(id);
            transaction.getAccountFrom().decreaseBalance(transaction.getAmount());
            transaction.getAccountTo().increaseBalance(transaction.getAmount());

            System.out.println("Транзакция выполнена успешно!");
            transactions.add(transaction);

            transaction.getAccountFrom().getClient().getLogs().add("- " + transaction.getAmount() + " (перевод средств) ID" + transaction.getAccountFrom().getAccountID() + ".");
            transaction.getAccountTo().getClient().getLogs().add("+ " + transaction.getAmount() + " (перевод средств) ID" + transaction.getAccountTo().getAccountID() + ".");

        }
        System.out.println("======================================\n");
    }

    public void cancelTransaction(Transaction transaction) {
        transaction.getAccountFrom().increaseBalance(transaction.getAmount());
        transaction.getAccountTo().decreaseBalance(transaction.getAmount());

        System.out.println("======================================\n");
        System.out.println("Транзакция (ID " + transaction.getId() + ") была отменена!");
        System.out.println("======================================");

        transaction.getAccountFrom().getClient().getLogs().add("+ " + transaction.getAmount() + " (отмена операции) ID" + transaction.getAccountFrom().getAccountID() + ".");
        transaction.getAccountTo().getClient().getLogs().add("- " + transaction.getAmount() + " (отмена операции) ID" + transaction.getAccountTo().getAccountID() + ".");

        transactions.remove(transaction);
    }

    public Client findClientByID(int clientID) {
        return (Client) (users.stream().filter(u -> u.getId() == clientID).findFirst().orElse(null));
    }

    public void closeAccount(FinanceAccount financeAccount) {
        FinanceAccount mainFinanceAccount = findClientByID(financeAccount.getClient().getId()).getMainAccount();
        mainFinanceAccount.increaseBalance(financeAccount.getBalance());

        accounts.remove(financeAccount);
        System.out.println("======================================\n");
        System.out.println("Счет (ID " + financeAccount.getAccountID() + ") успешно закрыт!");
        System.out.println("======================================");
    }

    public void addLoan(Applications loan) {
        applications.add(loan);
    }

    public void addInstallment(Applications installment) {
        applications.add(installment);
    }

    public static int generateIDForAccount(){
        Random random = new Random();
        int accountID = 0;
        do{
            accountID = random.nextInt(11111,99999);
        }while (occupiedID.contains(accountID));

        occupiedID.add(accountID);
        return accountID;
    }

    public void printLogs() {

    }
}
