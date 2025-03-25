package users;

import bank.Bank;
import finance.Transaction;
import interfaces.OperatorManagerInterface;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Operator extends User implements OperatorManagerInterface {
    protected boolean cancel;

    public Operator(int idForNewUser) {
        super();
        cancel = false;
    }

    public Operator(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
        cancel = false;
    }

    @Override
    public void viewStatistics() {
        System.out.println("Статистика по операциям:");
        // Логика получения и вывода статистики
    }

    @Override
    public void cancelTransaction(Bank bank, Transaction transaction) {
        if (transaction != null && !cancel){
            bank.cancelTransaction(transaction);
            cancel = true;
        }else if (cancel){
            System.out.println("-----------------------------------------\nВозможность отменить 1 транзакцию израсходована, ждите обнуления\n-----------------------------------------");
        }
    }

    @Override
    public void confirmSalaryProject() {
        //salaryProject.confirm();
        //System.out.println("Зарплатный проект подтверждён.");
    }

}
