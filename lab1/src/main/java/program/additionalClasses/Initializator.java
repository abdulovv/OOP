package program.additionalClasses;

import bank.Bank;
import java.util.List;

public final class Initializator {
    public static void initBanks(List<Bank> banks) {
        banks.add(new Bank("Priorbank", "423423u4283479823489234"));
        banks.add(new Bank("Alfabank","33422234"));
    }
}
