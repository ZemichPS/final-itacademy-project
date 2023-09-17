package by.itacademy.sharedresource.core.dto;

import by.itacademy.sharedresource.core.dto.ErrorResponse;
import by.itacademy.sharedresource.core.enums.ErrorType;

import java.util.Objects;


public class ErrorResponse {
    private ErrorType errorType;
    private String message;

    public ErrorResponse(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }

    public ErrorResponse() {
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorResponse response = (ErrorResponse) o;
        return errorType == response.errorType && Objects.equals(message, response.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorType, message);
    }
}
