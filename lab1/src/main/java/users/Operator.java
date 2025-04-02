package users;

import bank.Bank;
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

    public void viewStatistics() {
        System.out.println("Статистика по операциям:");
        // Логика получения и вывода статистики
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

}
