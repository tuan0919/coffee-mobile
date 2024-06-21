package nlu.hcmuaf.android_coffee_app.exceptions;

import lombok.AllArgsConstructor;

public class CategoryException extends CustomException {
    public CategoryException(String message) {
        super(message);
    }
    public CategoryException(String message, int errorCode) {
        super(message, errorCode);
    }
}
