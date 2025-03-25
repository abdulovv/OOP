package program.additionalClasses;

import bank.Bank;
import finance.FinanceAccount;
import finance.Transaction;
import users.*;

import java.util.Scanner;

public class UserMenu {
    private static final Forms forms = new Forms();

    public UserMenu(){

    }

    public void client(Bank bank, Client client) {
        FinanceAccount currentFinanceAccount;
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(
                            "1.  Открыть счет\n" +
                            "2.  Закрыть счет\n" +
                            "3.  Оформить кредит\n" +
                            "4.  Оформить рассрочку\n" +
                            "5.  Подать заявку на зарплатный проект\n" +
                            "6.  Вывести список счетов\n" +
                            "7.  Вывести список кредитов\n" +
                            "8.  Вывести список рассрочек\n" +
                            "9.  Заморозка счета\n" +
                            "10  Разморозка счета\n" +
                            "11. Перевод средств со счета\n" +
                            "-------------------(ВЫХОД 0)-------------------\n" +
                            "Ввод:"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            switch (choice) {
                case 0:
                    break;
                case 1:
                    client.openFinanceAccount(bank);
                    break;
                case 2:
                    currentFinanceAccount = forms.financeAccountChooseForm(client);
                    client.closeAccount(currentFinanceAccount);
                    break;
                case 6:
                    client.printAllAccounts();
                    break;
                case 9:
                    currentFinanceAccount = forms.financeAccountChooseForm(client);
                    client.freezeFinanceAccount(currentFinanceAccount);
                    break;
                case 10:
                    currentFinanceAccount = forms.financeAccountChooseForm(client);
                    client.unfreezeFinanceAccount(currentFinanceAccount);
                    break;
                case 11:
                    Transaction transaction = forms.transactionForm(bank, client);
                    if (transaction == null){
                        System.out.println("Транзакция была отменена!");
                    }else
                        bank.performTransaction(transaction);
                    break;
                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");

            }

        }while (choice != 0);
    }

    public void operator(Bank bank, Operator operator) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(
                            "1. Вывести клиентов\n" +
                            "2. Рассмотреть клиентов ждущих одобрение регистрации\n" +
                            "3. Рассмотреть клиентов ждущих одобрение рассрочки\n" +
                            "4. Рассмотреть клиентов ждущих одобрение кредитов\n" +
                            "5. Отмена операций специалиста стороннего предприятия\n" +
                            "-------------------(ВЫХОД 0)-------------------\n" +
                            "Ввод:"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            switch (choice) {
                case 0:
                    break;
                case 1:

                    break;
                case 2:

                    break;
                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }

        }while (choice != 0);
    }

    public void manager(Bank bank, Manager manager) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(
                    "1.  Вывести клиентов\n" +
                    "2.  Рассмотреть клиентов ждущих одобрение регистрации\n" +
                    "3.  Рассмотреть клиентов ждущих одобрение рассрочки\n" +
                    "4.  Рассмотреть клиентов ждущих одобрение кредитов\n" +
                    "5.  Рассмотреть заявки на зарплатный проект\n" +
                    "6.  Проссмотреть статистику по движениям средств клиентов\n" +
                    "7.  Отмена операций специалиста стороннего предприятия\n" +
                    "8.  Отмена операции клиента\n" +
                    "9.  Блокировка счета\n" +
                    "10.  Разблокировка счета\n" +
                    "-------------------(ВЫХОД 0)-------------------\n" +
                    "Ввод:"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            switch (choice) {
                case 0:
                    break;
                case 1:
                    manager.PrintAllClients(bank);
                    break;
                case 2:
                    manager.checkWaitingRegistrationClients(bank);
                    break;
                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");

            }

        }while (choice != 0);
    }

    public void admin(Bank currentBank, Admin admin) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(
                    "1. Просмотр всех логов\n" +
                    "2. Отмена всех действий пользователей\n" +

                    "-------------------(ВЫХОД 0)-------------------\n" +
                    "Ввод:"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            switch (choice) {
                case 0:
                    break;
                case 1:

                    break;
                case 2:

                    break;
                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (choice != 0);
    }
}
