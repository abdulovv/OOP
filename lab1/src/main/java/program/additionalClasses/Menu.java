package program.additionalClasses;

import bank.Bank;
import users.Manager;
import users.User;

import java.util.Scanner;

public class Menu {
    public static Scanner scanner = new Scanner(System.in);
    public static int choice = 0;

    public Menu(){

    }

    public void manager(Bank bank, Manager manager) {

        do {
            System.out.println(
                    "1. Вывести клиентов\n" +
                            "2. Рассмотреть клиентов ждущих одобрение регистрации\n" +
                            "-------------------(ВЫХОД 0)-------------------\n" +
                            "Ввод:"
            );
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    manager.PrintAllClients(bank);
                    break;
                case 2:
                    manager.checkWaitingRegistrationClients(bank);
                    break;
            }

        }while (choice != 0);
    }
}
