package users;

import finance.Loan;
import finance.Transaction;

public class Manager extends User{

    public Manager(String fullName, String passportNumber, int id, String phone, String email) {
        super(fullName, passportNumber, id, phone, email);
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
}
