package nlu.hcmuaf.android_coffee_app.controller.exception;
import nlu.hcmuaf.android_coffee_app.dto.response.MessageResponseDTO;
import nlu.hcmuaf.android_coffee_app.exceptions.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<MessageResponseDTO> handleException(CustomException ex) {
        return ResponseEntity.status(ex.getErrorCode())
                .body(MessageResponseDTO.builder().message(ex.getMessage()).build());
    }
}