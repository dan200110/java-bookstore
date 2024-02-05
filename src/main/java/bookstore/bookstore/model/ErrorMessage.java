package bookstore.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private String message;
    private List<ValidationError> validationErrors;

    public ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.validationErrors = new ArrayList<>();
    }

    public void addValidationError(String field, String message) {
        ValidationError validationError = new ValidationError(field, message);
        this.validationErrors.add(validationError);
    }
}
