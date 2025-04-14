package users;

import bank.Bank;
import enterprise.EnterpriseApplication;
import finance.Applications;
import finance.ClientsTransaction;
import finance.FinanceAccount;
import finance.ClientsTransaction;

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

    public void approveLoan(Applications loan, Bank bank) {
        super.approveLoan(loan, bank);
    }

    public void approveInstallment(Applications installment, Bank bank) {
       super.approveInstallment(installment, bank);
    }

    public void viewClientStatistic(Client currentClient) {
        super.viewClientStatistic(currentClient);
    }

    public void cancelTransaction(Bank bank, ClientsTransaction transaction) {
        super.cancelTransaction(bank, transaction);
    }

    public void confirmSalaryProject(EnterpriseApplication application, Bank bank) {
        boolean flag = true;
        Scanner sc = new Scanner(System.in);
        System.out.println(application.getInfo());

        do {
            System.out.print("y / n - одобрить / отклонить\nВвод:");
            String input = sc.nextLine();

            switch (input) {
                case "y":
                    application.getEnterprise().addEmployee(application.getClient());
                    flag = false;
                    break;
                case "n":
                    System.out.println("-----------------\nЗаявка была отклонена");
                    flag = false;
                    break;
                default:
                    System.out.println("Неверный ввод -> [" + input + "]");
            }
            bank.getEnterpriseApplications().remove(application);

        }while (flag);
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
