package users;
import interfaces.AuthenticationInterface;
import jakarta.validation.constraints.*;

public abstract class User implements AuthenticationInterface {
    @Size(min = 2, max = 50, message = "ФИО должно быть от 2 до 100 символов")
    protected String fullName;

    @Pattern(regexp = "\\d{10}", message = "Номер паспорта должен состоять из 10 цифр")
    protected String passportNumber;

    @NotNull
    protected int id;

    @Pattern(regexp = "\\+375\\d{9}", message = "Телефон должен быть в формате +37529XXXXXXX")
    protected String phone;

    @Email(message = "Email должен быть корректным")
    protected String email;

    public User(String fullName, String passportNumber, int id, String phone, String email) {
        this.fullName = fullName;
        this.passportNumber = passportNumber;
        this.id = id;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public boolean login(String username, String password) {
        // Логика авторизации
        return true;
    }

    @Override
    public void logout() {
        // Логика выхода из системы
    }
}
