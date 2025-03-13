package users;

import finance.Account;

import java.util.ArrayList;
import java.util.List;

public class Client extends User {
    List<Account> accounts = new ArrayList<>();

    public Client(String fullName, String passportNumber, int id, String phone, String email) {
        super(fullName, passportNumber, id, phone, email);
    }

    public void openAccount() {
        //bank.createAccount(accountNumber);
    }

    public void closeAccount(String accountNumber) {
        //
    }

    public void applyForLoan() {
        //bank.approveLoan(loanId);
    }

    public void applyForSalaryProject() {
        //enterprise.registerSalaryProject(enterpriseId, this.id);
    }
}
