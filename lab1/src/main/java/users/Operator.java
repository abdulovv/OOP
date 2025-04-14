package users;

import bank.Bank;
import finance.Applications;
import finance.FinanceAccount;
import finance.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
public class Operator extends User{
    protected boolean cancel;

    public Operator(int idForNewUser) {
        super();
        cancel = false;
    }

    public Operator(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
        cancel = false;
    }


    public void PrintAllClients(Bank bank) {
        System.out.println("==========================\nСписок клиентов:");
        int i = 1;
        for (User user : bank.getUsers()) {
            if(user instanceof Client) {
                System.out.println(i++ + "." + user.toString() + "\n");
            }
        }
        System.out.println("==========================\n");
    }

    public void viewClientStatistic(Client client) {
        for(String s : client.getLogs()){
            System.out.println("--  " + s + "  --");
        }
    }

    public void cancelTransaction(Bank bank, Transaction transaction) {
        if (transaction != null && !cancel){
            bank.cancelTransaction(transaction);
            cancel = true;
        }else if (cancel){
            System.out.println("-----------------------------------------\nВозможность отменить 1 транзакцию израсходована, ждите обнуления\n-----------------------------------------");
        }
    }

    public void confirmSalaryProject() {
        //salaryProject.confirm();
        //System.out.println("Зарплатный проект подтверждён.");
    }

    public void checkWaitingRegistrationClients(Bank bank) {
        if(bank.getWaitingRegClients().isEmpty()) {
            System.out.println("====================================\nЗаявок на одобрение регистрации нету\n====================================\n");
            return;
        }

        User client = null;

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            System.out.println("Выберите клиента для рассмотрения(№)");

            int i = 1;
            for (User c : bank.getWaitingRegClients()) {
                System.out.println("№" + i++ + ". " + c.getFullName());
            }

            try {
                System.out.print("Ввод:");
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            try {
                client = bank.getWaitingRegClients().get(--choice);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + ++choice + "]\n");
            }

        }while (client == null);

        System.out.println("\n\n Данные о клиенте:\n" + client.toString());

        scanner.nextLine();
        String input;

        do {
            System.out.println("Одобрить - y / отклонить - n");
            System.out.print("Ввод:");
            input = scanner.nextLine();

            if (input.equals("y")) {
                bank.getWaitingRegClients().remove(client);
                bank.getUsers().add(client);
            }

        }while (!input.equals("n") && !input.equals("y"));

        bank.getWaitingRegClients().remove(client);
    }

    public void approveLoan(Applications loan, Bank bank) {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        do {
            System.out.print("\nApproving loan\n" + loan.getInfo() + "\nВведите: y - да / n - нет\nВвод:");
            String approved = sc.nextLine();

            if (approved.equals("y")) {
                loan.setActive(true);
                loan.getClient().getApplications().add(loan);

                FinanceAccount account = loan.getClient().getMainAccount();
                if (account.isLocked() || account.isFrozen()) {
                    loan.getClient().openFinanceAccount(bank);
                    account = loan.getClient().getAccounts().getLast();
                }

                account.increaseBalance(loan.getSum());
                account.getClient().getLogs().add("+ " + loan.getSum() + " (одобрение кредита) ID" + account.getAccountID() + ". --");
                System.out.println("------------------------------\nКредит был одобрен\n------------------------------");
                flag = false;
            }else if (approved.equals("n")) {
                bank.getApplications().remove(loan);
                System.out.println("------------------------------\nКредит не был одобрен\n------------------------------");
                flag = false;
            }
        }while (flag);
    }

    public void approveInstallment(Applications installment, Bank bank) {
        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        do {
            System.out.print("====================================\nApproving installment\n" + installment.getInfo() + "\nВведите: y - да / n - нет\nВвод:");
            String approved = sc.nextLine();

            if (approved.equals("y")) {
                installment.setActive(true);
                installment.getClient().getApplications().add(installment);
                System.out.println("------------------------------\nРассрочка была одобрена\n------------------------------");
                flag = false;
            }else if (approved.equals("n")) {
                bank.getApplications().remove(installment);
                System.out.println("------------------------------\nРассрочка не была одобрена\n------------------------------");
                flag = false;
            }
        }while (flag);
    }
}
