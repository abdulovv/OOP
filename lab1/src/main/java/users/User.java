package users;
import bank.Bank;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public abstract class User{
    @NotNull(message = "ФИО не может быть null")
    @Size(min = 10, max = 50, message = "ФИО должно быть от 10 до 50 символов")
    @Pattern(regexp = "^[A-Za-zА-Яа-я\\s]{10,50}$", message = "ФИО должно содержать только буквы и пробелы")
    protected String fullName;

    @Pattern(regexp = "\\d{10}", message = "Номер паспорта должен состоять из 10 цифр")
    protected String passportNumber;

    @NotNull
    protected int id;

    @Pattern(regexp = "\\+375\\d{9}", message = "Телефон должен быть в формате +375(XX)XXXXXXX")
    protected String phone;

    @Email(message = "Email должен быть корректным")
    protected String email;

    @Size(min = 5, max = 20, message = "Длина логина должна быть от 5 до 20 символов")
    @Pattern(regexp = "^[A-Za-z0-9]{5,20}$", message = "Разрешены только английские буквы и цифры")
    public String login;

    @Size(min = 5, max = 20, message = "Длина пароля должна быть от 5 до 20 символов")
    @Pattern(regexp = "^[A-Za-z0-9]{5,20}$", message = "Разрешены только английские буквы и цифры")
    public String password;

    public User() {

    }

    public User(String fullName, String passportNumber, int id, String phone, String email, String login, String password) {
        this.fullName = fullName;
        this.passportNumber = passportNumber;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.login = login;
        this.password = password;
    }
}
