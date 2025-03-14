package program;

import bank.Bank;
import program.additionalClasses.Initializator;
import program.additionalClasses.Forms;
import users.*;

import java.util.*;


public final class BankApp {
    private static Scanner scanner = new Scanner(System.in);
    private static int choice = -1;

    private static class Context{
        public static Bank currentBank = null;
        public static User currentUser = null;

        public static List<Bank> banks = new ArrayList<Bank>();
        private static final Forms forms = new Forms();

        public static void registerUser() {
            currentUser = forms.roleChooseForm();
            forms.personalDataForm(currentUser, currentBank);


            if(currentUser instanceof Client){
                //bank.addApplication();
                System.out.println(currentUser.getFullName() + ", заявка отправлена, ждите ее рассмотрения!");
                currentBank.getUsers().add(currentUser);
            }
        }

        public static void chooseBank(){
            currentBank = forms.bankChooseForm(banks);
        }

        public static void authorize(){

        }

        public static void clear(){
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }

    }//END CONTEXT

    public static void start() throws InterruptedException {
        Initializator.initBanks(Context.banks);

        String resultOfStartMenu;
        do{
            resultOfStartMenu = startMenu();
            switch (resultOfStartMenu) {
                case "AUTHORIZATION":
                    authorizationMenu();
                    break;
                case "REGISTRATION":
                    registerMenu();
                    break;
            }
        }while (!resultOfStartMenu.equals("EXIT"));
        Thread.sleep(1200);
        System.out.println("Программа завершена");
    }

    //1 SLICE
    public static String startMenu(){
        choice = -1;
        do{
            System.out.print(
                            "1. Авторизация\n" +
                            "2. Регистрация нового клиента\n" +
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

    //2 SLICE

    public static void registerMenu(){
        Context.clear();
        Context.chooseBank();
        Context.registerUser();
    }

    private static void authorizationMenu() {
        Context.clear();
        Context.chooseBank();
        Context.authorize();
    }

    //3 SLICE

}
