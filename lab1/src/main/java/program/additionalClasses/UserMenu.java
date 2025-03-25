package program.additionalClasses;

import bank.Bank;
import finance.FinanceAccount;
import finance.Transaction;
import users.*;

import java.util.Scanner;

public final class UserMenu {
    private static final Forms forms = new Forms();

    public UserMenu(){

    }

    public void client(Bank currentBank, Client client) {

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
                            "10. Разморозка счета\n" +
                            "11. Перевод средств со счета\n" +
                            "12. Снятие наличных\n" +
                            "13. Пополнение счета\n" +
                            "-------------------(ВЫХОД 0)-------------------\n" +
                            "Ввод:"
            );

            FinanceAccount currentFinanceAccount = null;

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
                    client.openFinanceAccount(currentBank);
                    break;
                case 2:
                    currentFinanceAccount = forms.financeAccountChooseForm(client);
                    client.closeAccount(currentBank, currentFinanceAccount);
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
                    Transaction transaction = forms.createTransactionForm(currentBank, client);
                    if (transaction == null){
                        System.out.println("Транзакция была отменена!");
                    }else
                        client.createTransaction(currentBank, transaction);
                    break;
                case 12:
                    currentFinanceAccount = forms.financeAccountChooseForm(client);
                    client.withdrawal(currentFinanceAccount);
                    break;
                case 13:
                    currentFinanceAccount = forms.financeAccountChooseForm(client);
                    client.replenish(currentFinanceAccount);
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
                    "10. Разблокировка счета\n" +
                    "-------------------(ВЫХОД 0)-------------------\n" +
                    "Ввод:"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            FinanceAccount currentFinanceAccount = null;

            switch (choice) {
                case 0:
                    break;
                case 1:
                    manager.PrintAllClients(bank);
                    break;
                case 2:
                    manager.checkWaitingRegistrationClients(bank);
                    break;
                case 8:
                    Transaction transaction = forms.transactionChooseForm(bank);
                    manager.cancelTransaction(bank, transaction);
                    break;
                case 9:
                    currentFinanceAccount = forms.financeAccountChooseForm(bank);
                    manager.lockClientAccount(currentFinanceAccount);
                    break;
                case 10:
                    currentFinanceAccount = forms.financeAccountChooseForm(bank);
                    manager.unlockClientAccount(currentFinanceAccount);
                    break;

                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");

            }

        }while (choice != 0);
    }

    public void admin(Bank bank, Admin admin) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(
                    "1. Просмотр всех логов\n" +
                    "2. Отмена действия\n" +
                    "3. Обновить опцию отмены оператора/менеджера\n" +
                    "4. Пополнить счет\n" +
                    "5. Вывод средств со счета\n" +
                    "6. Аннулировать счет\n" +

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
                    Transaction transaction = forms.transactionChooseForm(bank);
                    admin.cancelAction(bank, transaction);
                    break;
                case 3:
                    User user = forms.neededRefreshUsersForm(bank);
                    admin.refreshOptions(user);
                    break;
                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (choice != 0);
    }
}
