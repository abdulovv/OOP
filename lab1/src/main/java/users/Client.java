package users;

import bank.Bank;
import finance.*;
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
    private final List<String> logs = new ArrayList<String>();
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
        if (currentFinanceAccount.isLocked() || currentFinanceAccount.isFrozen()) {
            System.out.println("Счет " + currentFinanceAccount.getAccountID() + " заморожен или заблокирован");
            return;
        }

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
            logs.add("- " + amount + " (снятие наличных) ID" + currentFinanceAccount.getAccountID() + ".");
        }else{
            System.out.println("---------------\nНедостаточно средств.\n---------------");
        }
    }

    public void replenish(FinanceAccount currentFinanceAccount) {
        if (currentFinanceAccount.isLocked() || currentFinanceAccount.isFrozen()) {
            System.out.print("Счет " + currentFinanceAccount.getAccountID() + "заморожен или заблокирован");
            return;
        }

        Scanner sc = new Scanner(System.in);

        int amount = -1;
        do {
            try {
                System.out.print("\nВведите сумму для пополнения:");
                amount = sc.nextInt();
            }catch (Exception e) {
                System.out.println("Невернный ввод --> [" + sc.nextLine() + "]");
            }
        }while (amount <= 0 || amount >= 100000000);

        currentFinanceAccount.increaseBalance(amount);
        System.out.println("---------------\nСчет пополнен (" + amount + ")\n---------------");
        logs.add("+ " + amount + " (пополнения счета) ID" + currentFinanceAccount.getAccountID() + ".");
    }

    public void takeLoan(Applications loan, Bank bank) {
        bank.addLoan(loan);
        System.out.println("Ждите одобрения кредита\n----------------------------------");
    }

    public void takeInstallment(Applications installment, Bank bank) {
        bank.addInstallment(installment);
        System.out.println("Ждите одобрения рассрочки\n----------------------------------");
    }

    private boolean checkLoans(){
        for (Applications a : applications){
            if (a instanceof Loan && a.isActive()){
                return true;
            }
        }
        return false;
    }

    private boolean checkInstallments(){
        for (Applications a : applications){
            if (a instanceof Installment && a.isActive()){
                return true;
            }
        }
        return false;
    }


    public void printLoans() {
        if (!checkLoans()){
            System.out.println("Нету действительных кредитов\n----------------------------------");
            return;
        }

        int i = 1;
        for (Applications a : applications) {
            if (a instanceof Loan && a.isActive()){
                System.out.println(i++ + ". " + a.getInfo());
            }
        }
        System.out.println("------------------------------");
    }

    public void printInstallment() {
        if (!checkInstallments()){
            System.out.println("Нету действительных рассрочек\n----------------------------------");
            return;
        }

        int i = 1;
        for (Applications a : applications) {
            if (a instanceof Installment && a.isActive()){
                System.out.println(i++ + ". " + a.getInfo());
            }
        }
        System.out.println("------------------------------");
    }

    public void payForApplication(Applications application, FinanceAccount financeAccount, Bank bank) {
        if (financeAccount.isLocked() || financeAccount.isFrozen()) {
            System.out.println("Счет " + financeAccount.getAccountID() + " был заморожен или заблокирован");
            return;
        }

        long payment = application.getRemainingSum() / application.getDuration() + 1;
        String info = "";

        if (payment <= financeAccount.getBalance()) {
            application.decreaseDuration();
            application.decreaseRemainingSum(payment);
            financeAccount.decreaseBalance(payment);
            logs.add("- " + payment + " (списание наличных на поганшения кредита/рассрочки) ID" + financeAccount.getAccountID() + ".");

            System.out.println("Оплата произведена успешно\n----------------------------------");

            if(application.getRemainingSum() <= 0){
                System.out.println("\nЗадолжность полностью выплачена!\n----------------------------------");
                applications.remove(application);
                bank.getApplications().remove(application);
            }else{
                if (application instanceof Loan){
                    info = "LOAN\nCLIENT:" + this.getFullName() + "\nSUM: " + application.getSum() +"\nCOMMISSION: " + application.getCommission() +
                            "\nREMAINING SUM: " + application.getRemainingSum() + "\nDURATION: " + application.getDuration() + "MONTH";
                }else{
                    info = "INSTALLMENT\nCLIENT:" + this.getFullName() + "\nSUM: " + application.getSum() +"\nCOMMISSION: " + application.getCommission() +
                            "\nREMAINING SUM: " + application.getRemainingSum() + "\nDURATION: " + application.getDuration() + "MONTH";
                }

            }
            application.setInfo(info);
            return;
        }

        System.out.println("Недостаточно средств\n----------------------------------");
    }

}
