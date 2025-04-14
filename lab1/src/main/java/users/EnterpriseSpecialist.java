package users;

import bank.Bank;
import enterprise.Enterprise;
import finance.EnterpriseTransaction;

public class EnterpriseSpecialist extends User {

    public EnterpriseSpecialist(int idForNewUser) {
        super();
    }

    public EnterpriseSpecialist(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
    }

    public void deleteEmployee(Enterprise enterprise, Client employee) {
        enterprise.getEmployees().remove(employee);
    }

    public void transferMoney(EnterpriseTransaction enterpriseTransactionToClient, Bank bank) {
        bank.performTransaction(enterpriseTransactionToClient);
    }
}
