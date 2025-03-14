package users;

import finance.Loan;
import finance.Transaction;

public class Manager extends Operator{

    public Manager() {
        super();
    }

    public Manager(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
    }

    public void approveLoan(Loan loan) {
        //loan.approve();
        //System.out.println("Кредит одобрен.");
    }

    /**
     * Отклонение кредита.
     *
     * @param loan Кредит для отклонения
     */
    public void rejectLoan(Loan loan) {
        //loan.reject();
        //System.out.println("Кредит отклонён.");
    }

    /**
     * Отмена операции, выполненной специалистом предприятия.
     *
     * @param transaction Транзакция для отмены
     */
    public void cancelOperation(Transaction transaction) {
        //transaction.cancel();
        //System.out.println("Операция отменена.");
    }

    public void viewStatistics() {
        super.viewStatistics();
    }


    public void cancelTransaction(Transaction transaction) {
        //transaction.cancel();
        //System.out.println("Транзакция " + transaction.getTransactionId() + " отменена.");
    }

    public void confirmSalaryProject() {
        //salaryProject.confirm();
        //System.out.println("Зарплатный проект подтверждён.");
    }

    @Override
    public int contextMenu() {
        return 0;
    }
}
