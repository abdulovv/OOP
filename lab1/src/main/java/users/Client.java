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
    private List<Applications> applications;
    private FinanceAccount mainAccount = new FinanceAccount(id,id,10000,true);


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


    public void openFinanceAccount(Bank bank) {
        FinanceAccount newFinanceAccount = bank.createAccount(this);
        accounts.add(newFinanceAccount);
        System.out.println("-----------------------\nНовый счет был открыт(ID:"+ newFinanceAccount.getAccountID() +")\n-----------------------");
    }

    public void closeAccount(FinanceAccount financeAccount) {
        mainAccount.topUpBalance(financeAccount.getBalance());
        accounts.remove(financeAccount);
    }

    public void printAllAccounts() {
        int i = 1;
        System.out.println("===========================================");
        System.out.println("MAIN ACCOUNT:" + mainAccount.toString() + "\n");
        for (FinanceAccount financeAccount : accounts) {
            System.out.println(i++ + ". " + financeAccount.toString());
        }
        System.out.println("===========================================");
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
