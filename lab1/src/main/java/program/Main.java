package program;

import java.io.Console;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do{
            System.out.print(
                    "1. Авторизация\n" +
                    "2. Регистрация нового клиента\n" +
                    "=============(Выход 0)============\n" +
                    "Ввод:");
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
                case 3:

                    break;
                default:
                    System.out.println("\nТакой команды не существует --> [" + choice + "]\n");
            }

        }while (choice != 0);
        System.out.println("Выход....");
    }
}
