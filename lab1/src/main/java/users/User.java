package users;

public abstract class User {
    protected String fullName;
    protected String passportNumber;
    protected String id;
    protected String phone;
    protected String email;

    public User(String fullName, String passportNumber, String id, String phone, String email) {
        this.fullName = fullName;
        this.passportNumber = passportNumber;
        this.id = id;
        this.phone = phone;
        this.email = email;
    }

    public boolean login(String username, String password) {
        // Логика авторизации
        return true;
    }

    public void logout() {
        // Логика выхода из системы
    }
}
