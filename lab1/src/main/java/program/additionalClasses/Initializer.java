package program.additionalClasses;

import bank.Bank;
import enterprise.Enterprise;
import enterprise.EnterpriseType;
import users.Admin;
import users.Client;
import users.EnterpriseSpecialist;
import users.Manager;

import java.util.List;

public final class Initializer {
    public static void initBanks(List<Bank> banks) {
        banks.add(new Bank("Priorbank", "423472398234"));
        banks.add(new Bank("Alfabank","3989237404"));
        banks.add(new Bank( "Belinvestbank","2342342443"));

        banks.get(0).getUsers().add(new Client("Vladislav Curik", "2394872154", banks.get(0).getIdForNewClient(), "+375295912338", "vladik@gmail.com", "vlados", "qwe", banks.get(0)));
        banks.get(0).getUsers().add(new Client("Alexandr Kokosh", "9023489534", banks.get(0).getIdForNewClient(), "+375259091277", "ashas@gmail.com", "ashas", "qwe", banks.get(0)));
        banks.get(0).getUsers().add(new Manager("Zahar Terekh", "8638263011", banks.get(0).getIdForNewClient(), "+375297107707", "zaha@gmail.com", "zahar", "qwe"));
        banks.get(0).getUsers().add(new Admin("Sasha Abdulov", "3009129834", banks.get(0).getIdForNewClient(), "+375291479324", "sasha@gmail.com", "sasha", "qwe"));
        banks.get(0).getUsers().add(new EnterpriseSpecialist("Siega Golovach", "2093842401", banks.get(0).getIdForNewClient(), "+375298653422", "siega@gmail.com", "siega", "qwe"));

        banks.get(1).getUsers().add(new Client("Vasya Ivanov", "7892047023", banks.get(1).getIdForNewClient(), "+37529777151", "vasyok@gmail.com", "vasya", "qwe", banks.get(1)));
        banks.get(1).getUsers().add(new Manager("Zahar Terekh", "8638263011", banks.get(1).getIdForNewClient(), "+375297107707", "zaha@gmail.com", "zahar", "qwe"));

        Enterprise soleTrader = new Enterprise(
                EnterpriseType.IndividualEntrepreneur,                               // legalType
                "ИП Петров Иван Сергеевич",         // legalName
                "190123456",                        // УНП (условный)
                "BPSBBY2X",                         // БИК (например, Белгазпромбанк)
                "г. Минск, ул. Ленина, д. 5, кв. 12" // legalAddress
        );

        Enterprise llc = new Enterprise(
                EnterpriseType.LimitedLiabilityCompany,
                "ООО 'ТехноПромСервис'",
                "190654321",
                "ALFABY2X",          // БИК Альфа-Банка (пример)
                "г. Гродно, пр-т Космонавтов, д. 15, оф. 301"
        );

        Enterprise cjsc = new Enterprise(
                EnterpriseType.JointStockCompany,
                "ЗАО 'БелАгроТрейд'",
                "123987654",
                "MTBKBY22",          // БИК МТБанка (пример)
                "г. Брест, ул. Советская, д. 30"
        );

        Bank.getEnterprises().add(soleTrader);
        Bank.getEnterprises().add(llc);
        Bank.getEnterprises().add(cjsc);
    }


}
