package nlu.hcmuaf.android_coffee_app.exceptions;

public class CartException extends CustomException {
    public CartException(String message) {
        super(message);
    }

    public CartException(String message, int errorCode) {
        super(message, errorCode);
    }
}
