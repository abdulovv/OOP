package enterprise;

import finance.FinanceAccount;
import lombok.Getter;
import lombok.Setter;
import users.Client;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Enterprise {
    private List<ProjectApplications> projectApplications = new ArrayList<ProjectApplications>();
    private List<String> logs = new ArrayList<String>();
    private List<Client> employees = new ArrayList<Client>();

    private EnterpriseType type;
    private String name;
    private String unp;
    private String bic;
    private String address;
    private long balance;

    public Enterprise(EnterpriseType type, String name, String unp, String bic, String address) {
        this.type = type;
        this.name = name;
        this.unp = unp;
        this.bic = bic;
        this.address = address;
        balance = 1000000000;
    }

    public void transferMoney(Enterprise enterprise, long amount) {
        decreaseBalance(amount);
        enterprise.increaseBalance(amount);
        System.out.println("Enterprise " + name + " transferred " + amount + " to " + enterprise.getName() + ".");
    }

    public void transferMoney(FinanceAccount financeAccount, long amount) {
        financeAccount.increaseBalance(amount);
        financeAccount.getClient().getLogs().add("+  (зарплата с предприятия) ID" + financeAccount.getAccountID() + " .");
    }

    public void addEmployee(Client client){
        employees.add(client);
    }

    public void increaseBalance(long amount) {
        balance += amount;
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "projectApplications=" + projectApplications + "\n" +
                ", type=" + type + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", unp='" + unp + '\'' + "\n" +
                ", bic='" + bic + '\'' + "\n" +
                ", address='" + address + '\'' + "\n" +
                '}';
    }

    public void decreaseBalance(long amount) {
        balance -= amount;
    }
}
