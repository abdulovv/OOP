package program.additionalClasses;

import bank.Bank;
import users.Admin;
import users.Client;
import users.Manager;

import java.util.List;

public final class Initializer {
    public static void initBanks(List<Bank> banks) {
        banks.add(new Bank("Priorbank", "423472398234","D:/OOP/lab1/src/main/java/program/additionalClasses/logs1.txt"));
        banks.add(new Bank("Alfabank","3989237404","D:/OOP/lab1/src/main/java/program/additionalClasses/logs2.txt"));
        banks.add(new Bank( "Belinvestbank","2342342443","D:/OOP/lab1/src/main/java/program/additionalClasses/logs3.txt"));

        banks.get(0).getUsers().add(new Client("Vladislav Curik", "2394872154", banks.get(0).getIdForNewClient(), "+375295912338", "dodik@gmail.com", "vlados", "qwe", banks.get(0)));
        banks.get(0).getUsers().add(new Client("Alexandr Kokosh", "9023489534", banks.get(0).getIdForNewClient(), "+375259091277", "ashas@gmail.com", "ashas", "qwe", banks.get(0)));
        banks.get(0).getUsers().add(new Manager("Zahar Terekh", "8638263011", banks.get(0).getIdForNewClient(), "+375297107707", "zaha@gmail.com", "zahar", "qwe"));
        banks.get(0).getUsers().add(new Admin("Sasha Abdulov", "3009129834", banks.get(0).getIdForNewClient(), "+375291479324", "sasha@gmail.com", "sasha", "qwe"));

        banks.get(1).getUsers().add(new Client("Vasya Ivanov", "7892047023", banks.get(1).getIdForNewClient(), "+37529777151", "vasyok@gmail.com", "vasya", "qwe", banks.get(1)));
        banks.get(1).getUsers().add(new Manager("Zahar Terekh", "8638263011", banks.get(1).getIdForNewClient(), "+375297107707", "zaha@gmail.com", "zahar", "qwe"));


    }


}
