package users;

import bank.Bank;
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
}
