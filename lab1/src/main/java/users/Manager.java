package users;

import bank.Bank;
import finance.Applications;
import finance.FinanceAccount;
import finance.Transaction;

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

    public void approveLoan(Applications loan, Bank bank) {
        super.approveLoan(loan, bank);
    }

    public void approveInstallment(Applications installment, Bank bank) {
       super.approveInstallment(installment, bank);
    }

    public void viewClientStatistic(Client currentClient) {
        super.viewClientStatistic(currentClient);
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
