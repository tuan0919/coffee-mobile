package nlu.hcmuaf.android_coffee_app.exceptions;

public class UserInfoException extends CustomException {
    public UserInfoException(String message) {
        super(message);
    }

    public UserInfoException(String message, int errorCode) {
        super(message, errorCode);
    }
}
