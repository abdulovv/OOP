package interfaces;

import bank.Bank;
import finance.Transaction;

public interface OperatorManagerInterface {
    void viewStatistics();

    void cancelTransaction(Bank bank, Transaction transaction);

    void confirmSalaryProject();
}
