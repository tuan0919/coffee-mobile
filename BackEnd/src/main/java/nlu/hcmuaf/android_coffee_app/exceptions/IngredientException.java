package nlu.hcmuaf.android_coffee_app.exceptions;

public class IngredientException extends CustomException {
    public IngredientException(String message) {
        super(message);
    }

    public IngredientException(String message, int errorCode) {
        super(message, errorCode);
    }
}
