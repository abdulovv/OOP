package program.additionalClasses;

import bank.Bank;
import finance.Applications;
import finance.FinanceAccount;
import finance.Loan;
import finance.Transaction;
import users.*;

import java.util.Scanner;

public final class UserMenu {
    private Forms forms;

    public UserMenu(Forms forms) {
        this.forms = forms;
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
                            "14. Накопление средств\n" +
                            "-------------------(ВЫХОД 0)-------------------\n" +
                            "Ввод:"
            );

            FinanceAccount currentFinanceAccount = null;
            Applications application = null;

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
                case 3:
                    application = forms.takeLoanForm(client);
                    client.takeLoan(application, currentBank);
                    break;
                case 4:
                    application = forms.takeInstallmentForm(client);
                    client.takeInstallment(application, currentBank);
                    break;
                case 5:
                    break;
                case 6:
                    client.printAllAccounts();
                    break;
                case 7:
                    client.printLoans();
                    break;
                case 8:
                    client.printInstallment();
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
                    Transaction transaction = forms.createTransactionForm(client);
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
                case 14:

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
                            "5. Проссмотреть статистику по движениям средств клиентов\n" +
                            "6. Отмена операции клиента\n" +
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
                    operator.PrintAllClients(bank);
                    break;
                case 2:
                    operator.checkWaitingRegistrationClients(bank);
                    break;
                case 3:

                    break;
                case 4:

                    break;

                case 5:
                    operator.viewStatistics();
                    break;
                case 6:
                    Transaction transaction = forms.transactionChooseForm(bank);
                    operator.cancelTransaction(bank, transaction);
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
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
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
                    "4. Добавление средств на счет\n" +
                    "5. Аннулировать счет\n" +
                    "6. Блокировка счета\n" +
                    "7. Разблокировка счета\n" +
                    "-------------------(ВЫХОД 0)-------------------\n" +
                    "Ввод:"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            User user;
            FinanceAccount financeAccount;

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
                    user = forms.neededRefreshUsersForm(bank);
                    if (user != null) {
                        admin.refreshOptions(user);
                    }
                    break;
                case 4:
                    financeAccount = forms.financeAccountChooseForm(bank);
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

                    admin.increaseClientBalance(financeAccount, amount);
                    break;
                    case 5:
                        financeAccount = forms.financeAccountChooseForm(bank);
                        admin.annulAccount(financeAccount);
                        break;
                    case 6:
                        financeAccount = forms.financeAccountChooseForm(bank);
                        admin.lockClientAccount(financeAccount);
                        break;

                    case 7:
                        financeAccount = forms.financeAccountChooseForm(bank);
                        admin.unlockClientAccount(financeAccount);
                        break;

                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (choice != 0);
    }
}
