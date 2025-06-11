package program;

import bank.Bank;
import program.additionalClasses.Initializer;
import program.additionalClasses.Forms;
import program.additionalClasses.UserMenu;
import users.*;
import java.util.*;


public final class BankApp {

    private static class Context{
        public static Bank currentBank = null;
        public static User currentUser = null;

        public static List<Bank> banks = new ArrayList<Bank>();
        private static final Forms forms = new Forms(banks);
        private static final UserMenu menu = new UserMenu(forms);

        public static void registerUser() {
            currentUser = forms.roleChooseForm(currentBank);
            forms.personalDataForm(currentUser, currentBank);

            if(currentUser instanceof Client){
                currentBank.addRegisterApplication(currentUser);
                System.out.println(currentUser.getFullName() + ", заявка отправлена, ждите ее рассмотрения!\n");
            }else
                currentBank.getUsers().add(currentUser);
        }

        public static void chooseBank(){
            currentBank = forms.bankChooseForm();
        }

        public static void authorize(){
            currentUser = forms.authorizeForm(currentBank);

            if (currentUser.getFullName().equals("ERROR")){
                System.out.println("Неверный пароль или логин");
            } else if (currentUser.getFullName().equals("NOTAPPLY")) {
                System.out.println("Ваша заявка еще не рассмотрена");
            } else
                System.out.println("\n" + currentUser.getFullName() + ", здравствуйте");
        }

        public static void userMenu() {
            if(currentUser instanceof Manager) {
                menu.manager(currentBank, (Manager)currentUser);
            }else if (currentUser instanceof Client) {
                menu.client(currentBank, (Client)currentUser);
            }else if (currentUser instanceof Operator) {
                menu.operator(currentBank, (Operator)currentUser);
            }else if (currentUser instanceof  Admin) {
                menu.admin(currentBank, (Admin)currentUser);
            } else if (currentUser instanceof EnterpriseSpecialist) {
                menu.enterpriseSpecialist(currentBank, (EnterpriseSpecialist)currentUser);
            }
        }
    }

    public static void start() throws InterruptedException {
        Initializer.initBanks(Context.banks);

        String resultOfStartMenu;
        do{
            resultOfStartMenu = startMenu();
            switch (resultOfStartMenu) {
                case "AUTHORIZATION":
                    if (authorizationMenu()){
                        Context.userMenu();
                    }
                    break;
                case "REGISTRATION":
                    registerMenu();
                    break;
            }

            Context.currentUser = null;
            Context.currentBank = null;
        }while (!resultOfStartMenu.equals("EXIT"));

        Thread.sleep(1200);
        System.out.println("Программа завершена");
    }

    public static String startMenu(){
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        do{
            System.out.print(
                            "1. Авторизация\n" +
                            "2. Регистрация\n" +
                            "=============(Выход 0)============\n" +
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
                    return "AUTHORIZATION";
                case 2:
                    return "REGISTRATION";
                default:
                    System.out.println("\nТакой команды не существует --> [" + choice + "]\n");
            }

        }while (choice != 0);
        System.out.println("Выход....");
        return "EXIT";
    }

    //2

    public static void registerMenu(){
        Context.chooseBank();
        Context.registerUser();
    }

    private static boolean authorizationMenu() {
        Context.chooseBank();
        Context.authorize();

        return !(Context.currentUser.getFullName().equals("ERROR") || Context.currentUser.getFullName().equals("NOTAPPLY"));
    }
}
