package users;

import bank.Bank;

public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        super(fullName, passportNumber, id, phone, email, login, password);
    }

    public void viewLogs(String logFilePath) {
        //вывод логов, содержащихся в банке
    }

    public void cancelAction(Client client) {
        //отмена действий
    }


}
