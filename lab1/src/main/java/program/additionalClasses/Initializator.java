package program.additionalClasses;

import bank.Bank;
import users.Admin;
import users.Client;
import users.Manager;

import java.util.List;

public final class Initializator {
    public static void initBanks(List<Bank> banks) {
        banks.add(new Bank("Priorbank", "423472398234"));
        banks.add(new Bank("Alfabank","3989237404"));
        banks.add(new Bank("Belinvestbank","2342342443"));

        banks.get(0).getUsers().add(new Client("Vladislav Curik", "2394872154", banks.get(0).getIdForNewClient(), "+375295912338", "dodik@gmail.com", "vlados", "qwerty"));
        banks.get(0).getUsers().add(new Manager("Zahar Terekh", "8638263011", banks.get(0).getIdForNewClient(), "+375297107707", "zaha@gmail.com", "zahar", "qwerty"));
        banks.get(0).getUsers().add(new Admin("Sasha Abdulov", "3009129834", banks.get(0).getIdForNewClient(), "+375291479324", "sasha@gmail.com", "sasha", "qwerty"));
    }


}
