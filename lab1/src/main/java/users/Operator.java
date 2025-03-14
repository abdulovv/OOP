package users;

import interfaces.OperatorInterface;

public class Operator extends User implements OperatorInterface {

    public Operator() {
        super();
    }

    public Operator(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
    }

    @Override
    public void viewStatistics() {
        System.out.println("Статистика по операциям:");
        // Логика получения и вывода статистики
    }

    @Override
    public void cancelTransaction() {
        //transaction.cancel();
        //System.out.println("Транзакция " + transaction.getTransactionId() + " отменена.");
    }

    @Override
    public void confirmSalaryProject() {
        //salaryProject.confirm();
        //System.out.println("Зарплатный проект подтверждён.");
    }

    @Override
    public int contextMenu() {
        return 0;
    }

}
