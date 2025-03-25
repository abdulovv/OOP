package users;

import bank.Bank;
import finance.Applications;
import finance.FinanceAccount;
import finance.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Client extends User {
    private final List<FinanceAccount> accounts = new ArrayList<>();
    private FinanceAccount mainAccount;


    public Client(Bank currentBank) {
        super();
        this.id = currentBank.getIdForNewClient();
        mainAccount = new FinanceAccount(10000+id,id,10000,false, false);
        accounts.add(mainAccount);
        currentBank.getAccounts().add(mainAccount);
    }

    public Client(String error) {
        this.fullName = error;
    }

    public Client(String fullName, String passportNumber, int id, String phone, String email, String login, String password, Bank currentBank) {
        super(fullName, passportNumber, id, phone, email, login, password);
        mainAccount = new FinanceAccount(10000+id,id,10000,false, false);
        accounts.add(mainAccount);
        currentBank.getAccounts().add(mainAccount);
    }


    public void openFinanceAccount(Bank bank) {
        FinanceAccount newFinanceAccount = bank.createAccount(this);
        accounts.add(newFinanceAccount);
        System.out.println("-----------------------\nНовый счет был открыт(ID:"+ newFinanceAccount.getAccountID() +")\n-----------------------");
    }

    public void closeAccount(FinanceAccount financeAccount) {
        mainAccount.increaseBalance(financeAccount.getBalance());
        accounts.remove(financeAccount);
    }

    public void printAllAccounts() {
        int i = 1;
        System.out.println("===========================================");
        System.out.println("MAIN ACCOUNT WITH ID " + mainAccount.getAccountID() + "\n");
        for (FinanceAccount financeAccount : accounts) {
            System.out.println(i++ + ". " + financeAccount.toString() );
        }
        System.out.println("===========================================");
    }

    public void applyForLoan() {
        //bank.approveLoan(loanId);
    }

    public void applyForSalaryProject() {
        //enterprise.registerSalaryProject(enterpriseId, this.id);
    }


    @Override
    public String toString() {
        return(
            "  Fullname: " + fullName + "\n" +
            "  Email:" + email + '\n' +
            "  Phone:" + phone + '\n' +
            "  Passport number:" + passportNumber + '\n' +
            "  ID:" + id
        );
    }

    public void freezeFinanceAccount(FinanceAccount financeAccount) {
        financeAccount.setFrozen(true);
    }

    public void unfreezeFinanceAccount(FinanceAccount financeAccount) {
        financeAccount.setFrozen(false);
    }

    public void createTransaction(Bank currentBank, Transaction transaction) {
        currentBank.performTransaction(transaction);
    }
}
