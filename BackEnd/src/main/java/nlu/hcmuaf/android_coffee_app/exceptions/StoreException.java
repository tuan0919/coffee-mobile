package nlu.hcmuaf.android_coffee_app.exceptions;

public class StoreException extends CustomException {
    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, int errorCode) {
        super(message, errorCode);
    }
}
