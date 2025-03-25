package program.additionalClasses;

import bank.Bank;
import finance.FinanceAccount;
import finance.Transaction;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import users.*;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public final class Forms {
    public Forms(){}

    public Bank bankChooseForm(List<Bank> banks) {
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

    public Transaction transactionForm(Bank currentBank, Client client) {
        FinanceAccount financeAccountFROM = financeAccountChooseForm(client);

        if (financeAccountFROM == null){

        }

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

            for (FinanceAccount a : currentBank.getAccounts()) {
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
}
