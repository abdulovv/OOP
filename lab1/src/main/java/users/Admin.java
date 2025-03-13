package users;

import lombok.Data;

public class Admin extends User {

    public Admin(String fullName, String passportNumber, int id, String phone, String email) {
        super(fullName, passportNumber, id, phone, email);
    }

    public void viewLogs(String logFilePath) {
        //вывод логов, содержащихся в банке
    }

    public void cancelAction(Client client) {
        //отмена действий
    }
}
