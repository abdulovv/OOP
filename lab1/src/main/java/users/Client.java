package users;

import bank.Bank;
import finance.Applications;
import finance.FinanceAccount;
import finance.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


@Getter
@Setter
public class Client extends User {
    private final List<Applications> applications = new ArrayList<>();
    private final List<FinanceAccount> accounts = new ArrayList<>();
    private FinanceAccount mainAccount;


    public Client(Bank currentBank) {
        super();
        this.id = currentBank.getIdForNewClient();
        mainAccount = new FinanceAccount(Bank.generateIDForAccount(), this,10000,false, false);
        accounts.add(mainAccount);
        currentBank.getAccounts().add(mainAccount);
    }

    public Client(String error) {
        this.fullName = error;
    }

    public Client(String fullName, String passportNumber, int id, String phone, String email, String login, String password, Bank bank) {
        super(fullName, passportNumber, id, phone, email, login, password);
        mainAccount = new FinanceAccount(Bank.generateIDForAccount(), this,10000,false, false);
        accounts.add(mainAccount);
        bank.getAccounts().add(mainAccount);
    }


    public void openFinanceAccount(Bank bank) {
        FinanceAccount newFinanceAccount = bank.openAccount(this);
        accounts.add(newFinanceAccount);
    }

    public void closeAccount(Bank bank, FinanceAccount financeAccount) {
        if (financeAccount != mainAccount) {
            accounts.remove(financeAccount);
            bank.closeAccount(financeAccount);
        }else
            System.out.println("Нельзя закрыть основной аккаунт");

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


    public void withdrawal(FinanceAccount currentFinanceAccount) {
        Scanner sc = new Scanner(System.in);

        int amount = -1;
        do {
            try {
                System.out.print("\nВведите сумму для снятия:");
                amount = sc.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + sc.nextLine() + "]");
            }
        }while (amount <= 0);


        if (amount <= currentFinanceAccount.getBalance()) {
            currentFinanceAccount.decreaseBalance(amount);
            System.out.println("---------------\nНаличные сняты (" + amount + ")\n---------------");
        }else{
            System.out.println("---------------\nНедостаточно средств.\n---------------");
        }
    }

    public void replenish(FinanceAccount currentFinanceAccount) {
        Scanner sc = new Scanner(System.in);

        int amount = -1;
        do {
            try {
                System.out.print("\nВведите сумму для пополнения:");
                amount = sc.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + sc.nextLine() + "]");
            }
        }while (amount <= 0 || amount >= 1000000000);

        currentFinanceAccount.increaseBalance(amount);
        System.out.println("---------------\nСчет пополнен (" + amount + ")\n---------------");
    }

    public void takeLoan(Applications loan, Bank bank) {
        bank.addLoan(loan);
    }

    public void takeInstallment(Applications installment, Bank bank) {
        bank.addInstallment(installment);
    }

    public void printLoans() {
    }

    public void printInstallment() {
    }
}
