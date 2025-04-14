package program.additionalClasses;

import bank.Bank;
import finance.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import users.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public final class Forms {
    private List<Bank> banks = new ArrayList<Bank>();
    public Forms(List<Bank> banks){
        this.banks = banks;
    }

    public Bank bankChooseForm() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        Bank choosenBank = null;
        do{
            System.out.println("Выберите банк(№):");
            for (int i = 0; i < banks.size(); i++) {
                System.out.println((i+1) + "." + banks.get(i).toString());
            }
            System.out.print("==========\nВвод:");
            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            try {
                choosenBank = banks.get(choice-1);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (choosenBank == null);
        return choosenBank;
    }

    public User roleChooseForm(Bank currentBank ) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        User user = null;
        boolean flag = false;
        do{
            System.out.println(
                    "Выберите роль(№):\n" +
                            "1.Клиент\n" +
                            "2.Менеджер\n" +
                            "3.Оператор\n" +
                            "4.Администратор\n" +
                            "5.Специалист стороннего предприятия\n"
            );

            System.out.print("===================================\nВвод:");
            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            switch (choice) {
                case 1:
                    user = new Client(currentBank);
                    flag = true;
                    break;
                case 2:
                    user = new Manager(currentBank.getIdForNewClient());
                    flag = true;
                    break;
                case 3:
                    user = new Operator(currentBank.getIdForNewClient());
                    flag = true;
                    break;
                case 4:
                    user = new Admin(currentBank.getIdForNewClient());
                    flag = true;
                    break;
                case 5:
                    user = new EnterpriseSpecialist(currentBank.getIdForNewClient());
                    flag = true;
                    break;
                default:
                    System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }

        }while (!flag);
        return user;
    }

    private static boolean isValidUserData(User currentUser, Bank currentBank) {
        // Создаем Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Проверяем объект
        Set<ConstraintViolation<User>> violations = validator.validate(currentUser);

        if (!violations.isEmpty()) {
            System.out.println("\n----------------------------------\nДанные введены с ошибкой:");
            for (ConstraintViolation<User> violation : violations) {
                System.out.println(violation.getMessage());
            }
            System.out.print("----------------------------------\n");
            return false;
        }

        for (User u : currentBank.getUsers()){
            if (currentUser.getLogin().equals(u.getLogin())){
                System.out.println("Логин --> [" + u.getLogin() + "] занят другим пользователем");
                return false;
            }
        }
        return true;
    }

    public void personalDataForm(User currentUser, Bank currentBank) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.print("\nВведите ФИО[10-50символов]:");
            String fullName = scanner.nextLine();
            currentUser.setFullName(fullName);

            System.out.print("\nВведите номер паспорта[10цифр]:");
            String passportNumber = scanner.nextLine();
            currentUser.setPassportNumber(passportNumber);

            System.out.print("\nВведите номер телефона[+375(XX)XXXXXXX]:");
            String number = scanner.nextLine();
            currentUser.setPhone(number);

            System.out.print("\nВведите email[name@name.teg]:");
            String email = scanner.nextLine();
            currentUser.setEmail(email);

            System.out.print("\nВведите логин[5-20символов A-Za-z,0-9]:");
            String login = scanner.nextLine();
            currentUser.setLogin(login);

            System.out.print("\nВведите пароль[5-20символов A-Za-z,0-9]:");
            String password = scanner.nextLine();
            currentUser.setPassword(password);
        }while (!isValidUserData(currentUser, currentBank));
    }

    public User authorizeForm(Bank currentBank) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);


        System.out.println("==========АВТОРИЗАЦИЯ==========");

        System.out.print("Введите логин[5-20символов A-Za-z,0-9]:");
        String login = scanner.nextLine();

        System.out.print("Введите пароль[5-20символов A-Za-z,0-9]:");
        String password = scanner.nextLine();

        User user = currentBank.authorizing(login, password);

        if (user == null){
            User notReg = currentBank.getWaitingRegClients().stream().filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password)).findFirst().orElse(null);

            user = notReg != null ? new Client("NOTAPPLY") : new Client("ERROR");
        }
        return user;
    }

    public FinanceAccount financeAccountChooseForm(Client client) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        FinanceAccount financeAccount = null;

        do {
            System.out.println("Выберите счёт(№)");

            int i = 1;
            for (FinanceAccount a : client.getAccounts()) {
                System.out.println("№" + i++ + ". ID:" + a.getAccountID() + ", Balance:" + a.getBalance());
            }

            try {
                System.out.print("Ввод:");
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            try {
                financeAccount = client.getAccounts().get(--choice);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }

        }while (financeAccount == null);
        return financeAccount;
    }

    public FinanceAccount financeAccountChooseForm(Bank bank) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        FinanceAccount financeAccount = null;

        do {
            System.out.println("Выберите счёт(№)");

            int i = 1;
            for (FinanceAccount a : bank.getAccounts()) {
                System.out.println("№" + i++ + ". ID:" + a.getAccountID() + ", Balance:" + a.getBalance() + ", owner:" + a.getClient().getFullName());
            }

            try {
                System.out.print("Ввод:");
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            try {
                financeAccount = bank.getAccounts().get(--choice);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }

        }while (financeAccount == null);
        return financeAccount;
    }

    public Transaction createTransactionForm(Client client) {
        FinanceAccount financeAccountFROM = financeAccountChooseForm(client);

        Bank bankTo = bankChooseForm();

        int accountID = -1;
        Scanner scanner = new Scanner(System.in);

        FinanceAccount financeAccountTO = null;

        do {
            System.out.println("Введите номер счета для перевода(ID), для отмены введите 0");
            try {
                System.out.print("Ввод:");
                accountID = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            if (accountID == 0)
                return null;


            if(financeAccountFROM.getAccountID() == accountID ){
                System.out.println("Нельзя переводить на счет с которого осуществляется перевод");
                continue;
            }

            for (FinanceAccount a : bankTo.getAccounts()) {
                if (accountID == a.getAccountID()) {
                    financeAccountTO = a;
                }
            }

            if (financeAccountTO == null){
                System.out.println("Счет с номером (ID)" + accountID + " НЕ существует");
            }

        }while (financeAccountTO == null);

        int amount = 0;
        boolean success = false;
        do {
            System.out.println("Введите сумму перевода(Доступно " + financeAccountFROM.getBalance() + "), для отмены введите 0");
            try {
                System.out.print("Ввод:");
                amount = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            if (amount == 0)
                return null;

            if (amount <= 0) {
                System.out.println("\nНеверная сумма перевода --> [" + amount + "]\n");
            }else
                success = true;

        }while (!success);

        return new Transaction(financeAccountFROM, financeAccountTO, amount);
    }

    public Transaction transactionChooseForm(Bank bank) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        Transaction transaction = null;

        if (bank.getTransactions().isEmpty()) {
            System.out.println("Транзакций еще не осуществлялось");
            return null;
        }

        do {
            System.out.println("Выберите транзакцию(№), для отмены введите 0");

            int i = 1;
            for (Transaction t : bank.getTransactions()) {
                System.out.println("№" + i++ + ". " + t.getInfo());
            }

            try {
                System.out.print("Ввод:");
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            if (choice == 0) {
                System.out.println("Отмена действия");
                return null;
            }

            try {
                transaction = bank.getTransactions().get(--choice);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }

        }while (transaction == null);
        return transaction;
    }

    public User neededRefreshUsersForm(Bank bank) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);


        User user = null;

        List<User> list = bank.getUsers().stream().filter(u1 -> u1 instanceof Operator && ((Operator) u1).isCancel()).toList();

        if (list.isEmpty()) {
            System.out.println("Обновление опций менеджера/оператора не ожидается");
            return null;
        }

        do {
            System.out.println("Выберите пользователя(№), для отмены введите 0");

            int i = 1;

            for (User o : list) {
                System.out.println("№" + i++ + ". " + o.getFullName() + ", " + o.getClass().toString());
            }

            try {
                System.out.print("Ввод:");
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            if (choice == 0) {
                System.out.println("Отмена действия");
                return null;
            }

            try {
                user = list.get(--choice);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }

        }while (user == null);
        return user;
    }

    public Applications takeLoanForm(Client client) {
        Applications loan = null;
        Scanner scanner = new Scanner(System.in);

        String info = "LOAN\nCLIENT:" + client.getFullName() + "\nSUM:";
        long sum = 0;
        long remainingSum = 0;
        long commission = 0;
        int duration = 0;

        do {
            System.out.print("Введите сумму кредита(>5000):");
            try {
                sum = scanner.nextLong();
            }catch (Exception e) {
                System.out.println("Неверный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            if (sum > 5000) break;

        }while (true);

        int choice = -1;
        do {
            System.out.print(
                    "Длительность кредита\n" +
                    "1. 3 месяца\n" +
                    "2. 6 месяцев\n" +
                    "3. 12 месяцев\n" +
                    "4. 24 месяца\n" +
                    "Ввод(№):"
            );

            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("Неверный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            if (choice == 1) {
                duration = 3;
                remainingSum = (long) (sum * 1.08f);
                break;
            }else if (choice == 2) {
                remainingSum = (long) (sum * 1.16f);
                duration = 6;
                break;
            } else if (choice == 3) {
                remainingSum = (long) (sum * 1.21f);
                duration = 12;
                break;
            } else if (choice == 4) {
                remainingSum = (long) (sum * 1.34f);
                duration = 24;
                break;
            }else
                System.out.println("Неверный номер --> [" + choice + "]\n");

        }while (true);

        commission = remainingSum - sum;

        info += sum + "\nREMAINING SUM: " + remainingSum + "\nCOMMISSION: " + commission;
        info += "\nDURATION:" + duration + " MONTHS";

        loan = new Loan(sum, duration, info);

        loan.setSum(sum);
        loan.setRemainingSum(remainingSum);
        loan.setCommission(commission);
        loan.setClient(client);

        System.out.println("Итоговая сумма возврата:" + remainingSum);

        return loan;
    }

    public Applications takeInstallmentForm(Client client) {
        Applications installment = null;
        Scanner scanner = new Scanner(System.in);

        String info = "INSTALLMENT\nCLIENT:" + client.getFullName() + "\nINSTALLMENT SUM:";
        long sum = 0;
        long remainingSum = 0;
        long commission = 0;
        int duration = 0;

        do {
            System.out.print("Введите сумму рассрочки(>5000):");
            try {
                sum = scanner.nextLong();
            } catch (Exception e) {
                System.out.println("Неверный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            if (sum > 5000) break;

        } while (true);

        int choice = -1;
        do {
            System.out.print(
                    "Длительность рассрочки\n" +
                            "1. 3 месяца\n" +
                            "2. 6 месяцев\n" +
                            "3. 12 месяцев\n" +
                            "4. 24 месяца\n" +
                            "Ввод(№):"
            );

            try {
                choice = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Неверный ввод --> [" + scanner.nextLine() + "]");
                continue;
            }

            if (choice == 1) {
                duration = 3;
                remainingSum = (long) (sum * 1.05f);
                break;
            } else if (choice == 2) {
                remainingSum = (long) (sum * 1.10f);
                duration = 6;
                break;
            } else if (choice == 3) {
                remainingSum = (long) (sum * 1.15f);
                duration = 12;
                break;
            } else if (choice == 4) {
                remainingSum = (long) (sum * 1.20f);
                duration = 24;
                break;
            } else
                System.out.println("Неверный номер --> [" + choice + "]\n");

        } while (true);

        commission = remainingSum - sum;

        info += sum + "\nREMAINING SUM: " + remainingSum + "\nCOMMISSION: " + commission;
        info += "\nDURATION:" + duration + " MONTHS";

        installment = new Installment(sum, duration, info);

        installment.setSum(sum);
        installment.setRemainingSum(remainingSum);
        installment.setCommission(commission);
        installment.setClient(client);

        System.out.println("Итоговая сумма возврата:" + remainingSum);

        return installment;
    }

    public Applications chooseInactiveLoan(Bank bank) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        Applications loan = null;
        List<Applications> loans = bank.getApplications().stream().filter(app -> !app.isActive() && app instanceof Loan).toList();

        if (loans.isEmpty()) {
            System.out.println("Заявок на одобрение кредитов нету");
            return loan;
        }

        do{
            System.out.println("Выберите кредит(№):");
            int i = 1;
            for (Applications a : loans) {
                if (!a.isActive() && a instanceof Loan){
                    System.out.println((i++) + "№. " + a.getInfo());
                }
            }
            System.out.print("==============================\nВвод:");
            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            try {
                loan = loans.get(choice-1);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (loan == null);




        return loan;
    }

    public Applications chooseInactiveInstallment(Bank bank) {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        Applications installment = null;
        List<Applications> installments = bank.getApplications().stream().filter(app -> !app.isActive() && app instanceof Installment).toList();

        if (installments.isEmpty()) {
            System.out.println("Заявок на одобрение рассрочек нету");
            return installment;
        }

        do{
            System.out.println("Выберите рассрочку(№):");
            int i = 1;
            for (Applications a : installments) {
                if (!a.isActive() && a instanceof Installment){
                    System.out.println((i++) + "№. " + a.getInfo());
                }
            }
            System.out.print("==============================\nВвод:");
            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            try {
                installment = installments.get(choice-1);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (installment == null);

        return installment;
    }

    public Applications loanChooseForm(Client client) {
        List<Applications> loans = client.getApplications().stream().filter(app -> app.isActive() && app instanceof Loan).toList();

        if (loans.isEmpty()) {
            System.out.println("У вас нет активных кредитов\n----------------------------------");
            return null;
        }

        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        Applications loan = null;

        do{
            System.out.println("Выберите кредит(№):");
            int i = 1;
            for (Applications a : loans) {
                System.out.println((i++) + "№." + a.getInfo());
            }
            System.out.print("==============================\nВвод:");
            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            try {
                loan = loans.get(choice-1);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (loan == null);

        return loan;
    }

    public Applications installmentChooseForm(Client client) {
        List<Applications> installments = client.getApplications().stream().filter(app -> app.isActive() && app instanceof Installment).toList();
        if (installments.isEmpty()) {
            System.out.println("У вас нет активных рассрочек\n----------------------------------");
            return null;
        }

        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        Applications installment = null;
        do{
            System.out.println("Выберите рассрочку(№):");
            int i = 1;
            for (Applications a : installments) {
                System.out.println((i++) + "№." + a.getInfo());
            }
            System.out.print("==============================\nВвод:");
            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            try {
                installment = installments.get(choice-1);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (installment == null);

        return installment;
    }


    public Client clientChoose(Bank bank) {
        Client client = null;

        List<User> clients = bank.getUsers().stream().filter(user -> user instanceof Client).toList();

        int choice = -1;
        Scanner scanner = new Scanner(System.in);

        do{
            System.out.println("Выберите клиента(№):");
            int i = 1;
            for (User cl : clients) {
                System.out.println((i++) + "№." + cl.getFullName() + ", " + cl.getEmail());
            }
            System.out.print("==============================\nВвод:");
            try {
                choice = scanner.nextInt();
            }catch (Exception e) {
                System.out.println("\nНекорректный ввод --> [" + scanner.nextLine() + "]\n");
                continue;
            }

            try {
                client = (Client)clients.get(choice-1);
            }catch (Exception e) {
                System.out.println("\nНеверный номер --> [" + choice + "]\n");
            }
        }while (client == null);

        return client;
    }
}
