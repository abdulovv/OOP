package program.additionalClasses;

import bank.Bank;
import enterprise.Enterprise;
import enterprise.EnterpriseApplication;
import finance.*;
import finance.ClientsTransaction;
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
                            "14. Оплата кредита(месячная)\n" +
                            "15. Оплата рассрочки(месячная)\n" +
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
                    Enterprise enterprise = forms.enterpriseChoose();
                    EnterpriseApplication enterpriseApplication = forms.enterpriseApplicationForm(client, enterprise);
                    client.applyForSalaryProject(enterpriseApplication, currentBank);
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
                    ClientsTransaction transaction = forms.createTransactionForm(client);
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
                    application = forms.loanChooseForm(client);
                    if (application != null) {
                        System.out.println("Сумма для оплаты: " + (application.getRemainingSum() / application.getDuration() + 1));

                        currentFinanceAccount = forms.financeAccountChooseForm(client);
                        client.payForApplication(application, currentFinanceAccount, currentBank);
                    }
                    break;
                case 15:
                    application = forms.installmentChooseForm(client);
                    if (application != null) {
                        System.out.println("Сумма для оплаты: " + (application.getRemainingSum() / application.getDuration() + 1));

                        currentFinanceAccount = forms.financeAccountChooseForm(client);
                        client.payForApplication(application, currentFinanceAccount, currentBank);
                    }
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
                            "6. Отмена операции\n" +
                            "-------------------(ВЫХОД 0)-------------------\n" +
                            "Ввод:"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            Applications currentApplication = null;
            Client currentClient = null;

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
                    currentApplication = forms.chooseInactiveLoan(bank);
                    if (currentApplication != null){
                        operator.approveLoan(currentApplication, bank);
                    }
                    break;
                case 4:
                    currentApplication = forms.chooseInactiveLoan(bank);
                    if (currentApplication != null){
                        operator.approveInstallment(currentApplication, bank);
                    }
                    break;

                case 5:
                    currentClient = forms.clientChoose(bank);
                    operator.viewClientStatistic(currentClient);
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
                    "3.  Рассмотреть клиентов ждущих одобрение кредитов\n" +
                    "4.  Рассмотреть клиентов ждущих одобрение рассрочки\n" +
                    "5.  Проссмотреть статистику по движениям средств клиентов\n" +
                    "6.  Рассмотреть заявки на зарплатный проект \n" +
                    "7.  Отмена операции\n" +
                    "8.  Блокировка счета\n" +
                    "9. Разблокировка счета\n" +
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
            Applications currentApplication = null;
            Client currentClient = null;

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
                    currentApplication = forms.chooseInactiveLoan(bank);
                    if (currentApplication != null){
                        manager.approveLoan(currentApplication, bank);
                    }
                    break;
                case 4:
                    currentApplication = forms.chooseInactiveInstallment(bank);
                    if (currentApplication != null){
                        manager.approveLoan(currentApplication, bank);
                    }
                    break;
                case 5:
                    currentClient = forms.clientChoose(bank);
                    manager.viewClientStatistic(currentClient);
                    break;
                case 6:
                    EnterpriseApplication enterpriseApplication = forms.chooseEnterpriseApplication(bank);
                    if (enterpriseApplication != null) {
                        manager.confirmSalaryProject(enterpriseApplication, bank);
                    }
                    break;
                case 7:
                    Transaction transaction = forms.transactionChooseForm(bank);
                    manager.cancelTransaction(bank, transaction);
                    break;
                case 8:
                    currentFinanceAccount = forms.financeAccountChooseForm(bank);
                    manager.lockClientAccount(currentFinanceAccount);
                    break;
                case 9:
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
                    "1. Отмена действия\n" +
                    "2. Обновить опцию отмены оператора/менеджера\n" +
                    "3. Добавление средств на счет\n" +
                    "4. Аннулировать счет\n" +
                    "5. Блокировка счета\n" +
                    "6. Разблокировка счета\n" +
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
                    Transaction transaction = forms.transactionChooseForm(bank);
                    admin.cancelAction(bank, transaction);
                    break;
                case 2:
                    user = forms.neededRefreshUsersForm(bank);
                    if (user != null) {
                        admin.refreshOptions(user);
                    }
                    break;
                case 3:
                    financeAccount = forms.financeAccountChooseForm(bank);
                    admin.increaseClientBalance(financeAccount);
                    break;
                case 4:
                    financeAccount = forms.financeAccountChooseForm(bank);
                    admin.annulAccount(financeAccount);
                    break;
                case 5:
                    financeAccount = forms.financeAccountChooseForm(bank);
                    admin.lockClientAccount(financeAccount);
                    break;
                case 6:
                    financeAccount = forms.financeAccountChooseForm(bank);
                    admin.unlockClientAccount(financeAccount);
                    break;
                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (choice != 0);
    }

    public void enterpriseSpecialist (Bank bank, EnterpriseSpecialist specialist) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print(
                    "1. Отмена сотрудника\n" +
                            "2. Перевод сотруднику\n" +
                            "-------------------(ВЫХОД 0)-------------------\n" +
                            "Ввод:"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            Enterprise enterprise = null;
            Client employee = null;

            switch (choice) {
                case 0:
                    break;
                case 1:
                    enterprise = forms.enterpriseChoose();
                    employee = forms.employeeChoose(enterprise);
                    if (employee != null) {
                        specialist.deleteEmployee(enterprise, employee);
                    }
                    break;
                case 2:
                    EnterpriseTransaction enterpriseTransactionToClient = forms.createEnterpriseTransactionToClient();
                    if (enterpriseTransactionToClient != null) {
                        specialist.transferMoney(enterpriseTransactionToClient, bank);
                    }
                    break;
                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (choice != 0);
    }
}
