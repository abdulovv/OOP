package users;

import finance.FinanceAccount;

import java.util.ArrayList;
import java.util.List;


public class Client extends User {
    private List<FinanceAccount> accounts = new ArrayList<>();
    private boolean verified;

    public Client() {
        super();
    }

    public Client(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
    }

    @Override
    public int contextMenu() {
        return 0;
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
