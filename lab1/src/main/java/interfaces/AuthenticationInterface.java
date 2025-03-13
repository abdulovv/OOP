package interfaces;

public interface AuthenticationInterface {
    boolean login(String username, String password);
    void logout();
}
