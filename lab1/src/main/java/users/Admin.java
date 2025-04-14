package users;

import bank.Bank;
import finance.ClientsTransaction;
import finance.FinanceAccount;
import finance.ClientsTransaction;
import finance.Transaction;

import java.util.Scanner;

public class Admin extends User {

    public Admin(int idForNewUser) {
        super();
    }

    public Admin(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
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

    public void increaseClientBalance(FinanceAccount financeAccount) {
        long amount = -1;

        while (true){
            Scanner sc = new Scanner(System.in);
            System.out.print("Сумма зачисления:");

            try {
                amount = sc.nextLong();
            }catch (Exception e){
                System.out.println("\nНеверный ввод --> [" + sc.nextLine() + "]\n");
            }

            if (amount > 0) break;

            System.out.println("Неверная сумма");
        }

        financeAccount.increaseBalance(amount);
        financeAccount.getClient().getLogs().add("+ " + amount + "(добавление наличных) ID" + financeAccount.getAccountID() + ".");
    }

    public void annulAccount(FinanceAccount financeAccount) {
        financeAccount.setBalance(0);
        financeAccount.getClient().getLogs().add("0 (аннулирование счета) ID" + financeAccount.getAccountID() + ".");
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
