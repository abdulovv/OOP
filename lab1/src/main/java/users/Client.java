package users;

import bank.Bank;
import finance.Applications;
import finance.FinanceAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Client extends User {
    private final List<FinanceAccount> accounts = new ArrayList<>();
    private boolean verified;
    private List<Applications> applications;

    public Client() {
        super();
        applications = new ArrayList<Applications>();
    }

    public Client(String error) {
        this.fullName = error;
        applications = new ArrayList<Applications>();
    }

    public Client(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
        applications = new ArrayList<Applications>();
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

    public void addApplication(Applications application) {
        applications.add(application);
    }

    @Override
    public String toString() {
        return(
            "  Fullname: " + fullName + "\n" +
            "  Email:" + email + '\n' +
            "  Phone:" + phone + '\n' +
            "  Passport number:" + passportNumber
        );
    }
}
