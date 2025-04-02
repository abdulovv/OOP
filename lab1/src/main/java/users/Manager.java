package users;

import bank.Bank;
import finance.FinanceAccount;
import finance.Loan;
import finance.Transaction;

import java.util.Scanner;

public class Manager extends Operator{

    public Manager(int idForNewUser) {
        super(idForNewUser);
    }

    public Manager(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
    }

    public void PrintAllClients(Bank bank) {
        super.PrintAllClients(bank);
    }

    public void approveLoan(Loan loan) {
        //loan.approve();
        //System.out.println("Кредит одобрен.");
    }

    public void rejectLoan(Loan loan) {
        //loan.reject();
        //System.out.println("Кредит отклонён.");
    }

    public void cancelOperation(Transaction transaction) {
        //transaction.cancel();
        //System.out.println("Операция отменена.");
    }

    public void viewStatistics() {
        super.viewStatistics();
    }


    public void cancelTransaction(Bank bank, Transaction transaction) {
        super.cancelTransaction(bank, transaction);
    }

    public void confirmSalaryProject() {
        //salaryProject.confirm();
        //System.out.println("Зарплатный проект подтверждён.");
    }

    public void checkWaitingRegistrationClients(Bank bank) {
        super.checkWaitingRegistrationClients(bank);
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
