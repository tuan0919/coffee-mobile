package nlu.hcmuaf.android_coffee_app.exceptions;

public class ProductException extends CustomException {
    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, int errorCode) {
        super(message, errorCode);
    }
}
