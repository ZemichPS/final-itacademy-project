package by.ITAcademy.UserMicroService.core.exception;


public class LoginException extends RuntimeException {
    public LoginException(String msg) {
        super(msg);
    }

    public LoginException() {
        super();
    }
}
