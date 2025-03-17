package users;

import bank.Bank;

public class EnterpriseSpecialist extends User {

    public EnterpriseSpecialist() {
        super();
    }

    public EnterpriseSpecialist(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
    }

    public void requestTransfer(String fromAccount, String toAccount, double amount) {
        //bank.processTransferRequest(fromAccount, toAccount, amount);
        ///System.out.println("Запрос на перевод средств отправлен.");
    }

}
