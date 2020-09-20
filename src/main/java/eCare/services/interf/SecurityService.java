package eCare.services.interf;

public interface SecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
