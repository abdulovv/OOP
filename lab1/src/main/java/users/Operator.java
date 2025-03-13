package users;

import finance.Transaction;

public class Operator extends User{


    public Operator(String fullName, String passportNumber, int id, String phone, String email) {
        super(fullName, passportNumber, id, phone, email);
    }

    public void viewStatistics() {
        System.out.println("Статистика по операциям:");
        // Логика получения и вывода статистики
    }

    /**
     * Отмена транзакции.
     *
     * @param transaction Транзакция для отмены
     */
    public void cancelTransaction(Transaction transaction) {
        //transaction.cancel();
        //System.out.println("Транзакция " + transaction.getTransactionId() + " отменена.");
    }

    public void confirmSalaryProject() {
        //salaryProject.confirm();
        //System.out.println("Зарплатный проект подтверждён.");
    }
}
