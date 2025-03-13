package users;

public class EnterpriseSpecialist extends User {
    public EnterpriseSpecialist(String fullName, String passportNumber, int id, String phone, String email) {
        super(fullName, passportNumber, id, phone, email);
    }

    public void requestTransfer(String fromAccount, String toAccount, double amount) {
        //bank.processTransferRequest(fromAccount, toAccount, amount);
        ///System.out.println("Запрос на перевод средств отправлен.");
    }
}
