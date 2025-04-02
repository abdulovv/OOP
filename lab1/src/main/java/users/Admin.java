package users;

import bank.Bank;
import finance.FinanceAccount;
import finance.Transaction;

public class Admin extends User {

    public Admin(int idForNewUser) {
        super();
    }

    public Admin(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
    }

    public void viewLogs(String logFilePath) {
        //вывод логов, содержащихся в банке
    }

    public void cancelAction(Bank bank, Transaction transaction) {
        if (transaction != null){
            bank.cancelTransaction(transaction);
        }
    }

    public void refreshOptions(User user) {
        if (user instanceof Manager){
            ((Manager) user).setCancel(false);
        }else{
            ((Operator) user).setCancel(false);
        }
    }

    public void increaseClientBalance(FinanceAccount financeAccount, long amount) {
        financeAccount.increaseBalance(amount);
    }

    public void annulAccount(FinanceAccount financeAccount) {
        financeAccount.setBalance(0);
    }

    public void lockClientAccount(FinanceAccount financeAccount) {
        financeAccount.setLocked(true);
        System.out.println("=========================================\nСчет с номером (ID " + financeAccount.getAccountID() + ") заблокирован!\n=========================================");
    }

    public void unlockClientAccount(FinanceAccount financeAccount) {
        financeAccount.setLocked(false);
        System.out.println("=========================================\nСчет с номером (ID " + financeAccount.getAccountID() + ") разблокирован!\n=========================================");
    }
}
