package program.additionalClasses;

import bank.Bank;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import users.*;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public final class Forms {
    private static int choice;
    private static Scanner scanner = new Scanner(System.in);

    public Forms(){}

    public Bank bankChooseForm(List<Bank> banks) {
        Bank choosenBank = null;
        choice = -1;
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

    public User roleChooseForm() {
        User user = null;
        boolean flag = false;
        choice = -1;
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
                    user = new Client();
                    flag = true;
                    break;
                case 2:
                    user = new Manager();
                    flag = true;
                    break;
                case 3:
                    user = new Operator();
                    flag = true;
                    break;
                case 4:
                    user = new Admin();
                    flag = true;
                    break;
                case 5:
                    user = new EnterpriseSpecialist();
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
            System.out.println("Данные введены с ошибкой:");
            for (ConstraintViolation<User> violation : violations) {
                System.out.println(violation.getMessage());
            }
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
        scanner.nextLine();
        do {
            System.out.print("Введите ФИО[10-50символов]:");
            String fullName = scanner.nextLine();
            currentUser.setFullName(fullName);

            System.out.print("Введите номер паспорта[10цифр]:");
            String passportNumber = scanner.nextLine();
            currentUser.setPassportNumber(passportNumber);

            System.out.print("Введите номер телефона[+375(XX)XXXXXXX]:");
            String number = scanner.nextLine();
            currentUser.setPhone(number);

            System.out.print("Введите email[name@name.teg]:");
            String email = scanner.nextLine();
            currentUser.setEmail(email);

            System.out.print("Введите логин[5-20символов A-Za-z,0-9]:");
            String login = scanner.nextLine();
            currentUser.setLogin(login);

            System.out.print("Введите пароль[5-20символов A-Za-z,0-9]:");
            String password = scanner.nextLine();
            currentUser.setPassword(password);

            currentUser.setId(currentBank.getIdForNewClient());
        }while (!isValidUserData(currentUser, currentBank));
    }

    public User authorizeForm(Bank currentBank) {
        scanner.nextLine();
        System.out.println("==========АВТОРИЗАЦИЯ==========");

        System.out.print("Введите логин[5-20символов A-Za-z,0-9]:");
        String login = scanner.nextLine();

        System.out.print("Введите пароль[5-20символов A-Za-z,0-9]:");
        String password = scanner.nextLine();

        User user = currentBank.authorizing(login, password);

        if (user == null){
            User notReg = currentBank.getWaitingRegClients().stream().filter(u -> u.getLogin().equals(login) && u.getPassword().equals(password)).findFirst().orElse(null);
            if (notReg != null){
                user = new Client("NOTAPPLY");
            }

            user = new Client("ERROR");
        }
        return user;
    }
}
