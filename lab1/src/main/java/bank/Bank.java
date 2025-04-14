package bank;

import enterprise.Enterprise;
import enterprise.EnterpriseApplication;
import finance.*;
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

    private final static List<Integer> occupiedID = new ArrayList<Integer>();
    @Getter
    private final static List<Enterprise> enterprises = new ArrayList<Enterprise>();

    private final List<User> users = new ArrayList<User>();
    private final List<FinanceAccount> accounts = new ArrayList<FinanceAccount>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final List<Applications> applications = new ArrayList<>();
    private final List<EnterpriseApplication> enterpriseApplications = new ArrayList<>();

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

        FinanceAccount newFinanceAccount = FinanceAccount.builder().accountID(generateIDForAccount()).balance(0).client(client).build();

        accounts.add(newFinanceAccount);
        System.out.println("-----------------------\nНовый счет был открыт(ID:"+ newFinanceAccount.getAccountID() +")\n-----------------------");

        return newFinanceAccount;
    }

    public void performTransaction(Transaction transaction) {

        System.out.println("\n======================================");
        if (transaction instanceof ClientsTransaction clientsTransaction) {
            if (clientsTransaction.getAccountFrom().getBalance() < clientsTransaction.getAmount()){
                System.out.println("Недостаточно средств на счету (ID "+ clientsTransaction.getAccountFrom().getAccountID() + ")");
            }else if (clientsTransaction.getAccountFrom().isFrozen()){
                System.out.println("Счет с которого осуществляется перевод заморожен (ID "+ clientsTransaction.getAccountTo().getAccountID() + ")");
            }else if(clientsTransaction.getAccountTo().isFrozen()){
                System.out.println("Счет на который осуществляется перевод заморожен (ID "+ clientsTransaction.getAccountFrom().getAccountID() + ")");
            }else if (clientsTransaction.getAccountFrom().isLocked()){
                System.out.println("Счет с которого осуществляется перевод заблокирован (ID "+ clientsTransaction.getAccountTo().getAccountID() + ")");
            }else if (clientsTransaction.getAccountTo().isLocked()){
                System.out.println("Счет на который осуществляется перевод заблокирован (ID "+ clientsTransaction.getAccountFrom().getAccountID() + ")");
            }else {
                String name1 = clientsTransaction.getAccountFrom().getClient().getFullName();
                String name2 = clientsTransaction.getAccountTo().getClient().getFullName();
                String date = Instant.now().toString();
                int id = getIdForNewTransaction();

                transaction.setInfo("Transaction ID:" + id + "\n" + name1 + ", ID " + clientsTransaction.getAccountFrom().getAccountID() + " --(" + date + ", amount: " + clientsTransaction.getAmount() + ")--> " + name2 + ", ID " + clientsTransaction.getAccountTo().getAccountID());

                clientsTransaction.setId(id);
                clientsTransaction.getAccountFrom().decreaseBalance(clientsTransaction.getAmount());
                clientsTransaction.getAccountTo().increaseBalance(clientsTransaction.getAmount());

                System.out.println("Транзакция выполнена успешно!");
                transactions.add(transaction);

                clientsTransaction.getAccountFrom().getClient().getLogs().add("- " + clientsTransaction.getAmount() + " (перевод средств) ID" + clientsTransaction.getAccountFrom().getAccountID() + ".");
                clientsTransaction.getAccountTo().getClient().getLogs().add("+ " + clientsTransaction.getAmount() + " (перевод средств) ID" + clientsTransaction.getAccountTo().getAccountID() + ".");

            }
        } else {
            EnterpriseTransaction enterpriseTransaction = (EnterpriseTransaction) transaction;
            if (enterpriseTransaction.getAmount() > enterpriseTransaction.getEnterprise().getBalance()){
                System.out.println("Недостаточно средств на счету компании");
            }else {
                transactions.add(enterpriseTransaction);
                enterpriseTransaction.getClientAccount().increaseBalance(enterpriseTransaction.getAmount());
                enterpriseTransaction.getClientAccount().getClient().getLogs().add("+  (перевод от предприятия) " + enterpriseTransaction.getAmount() + " ID" + enterpriseTransaction.getClientAccount().getAccountID() + ".");
                System.out.println("Транзакция выполнена успешно!");
            }
        }
        System.out.println("======================================\n");
    }

    public void cancelTransaction(Transaction transaction) {
        if (transaction instanceof ClientsTransaction clientsTransaction) {
            clientsTransaction.getAccountFrom().increaseBalance(clientsTransaction.getAmount());
            clientsTransaction.getAccountTo().decreaseBalance(clientsTransaction.getAmount());

            System.out.println("======================================\n");
            System.out.println("Транзакция (ID " + clientsTransaction.getId() + ") была отменена!");
            System.out.println("======================================");

            clientsTransaction.getAccountFrom().getClient().getLogs().add("+ " + clientsTransaction.getAmount() + " (отмена операции) ID" + clientsTransaction.getAccountFrom().getAccountID() + ".");
            clientsTransaction.getAccountTo().getClient().getLogs().add("- " + clientsTransaction.getAmount() + " (отмена операции) ID" + clientsTransaction.getAccountTo().getAccountID() + ".");

            transactions.remove(transaction);
        }else{
            EnterpriseTransaction enterpriseTransaction = (EnterpriseTransaction) transaction;
            enterpriseTransaction.getClientAccount().decreaseBalance(enterpriseTransaction.getAmount());
            enterpriseTransaction.getEnterprise().increaseBalance(enterpriseTransaction.getAmount());

            System.out.println("======================================\n");
            System.out.println("Транзакция была отменена!");
            System.out.println("======================================");

            enterpriseTransaction.getClientAccount().getClient().getLogs().add("- " + enterpriseTransaction.getAmount() + " (отмена операции) ID" + enterpriseTransaction.getClientAccount().getAccountID() + ".");

            transactions.remove(transaction);
        }
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
}
