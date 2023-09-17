package by.itacademy.sharedresource.core.dto;


import by.itacademy.sharedresource.core.enums.ErrorType;

import java.util.Map;
import java.util.Objects;


public class StructuredErrorResponse {
    private ErrorType errorType;
    private Map<String, String> errorMap;

    public StructuredErrorResponse(ErrorType errorType, Map<String, String> errorMap) {
        this.errorType = errorType;
        this.errorMap = errorMap;
    }

    public StructuredErrorResponse() {
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map<String, String> errorMap) {
        this.errorMap = errorMap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructuredErrorResponse response = (StructuredErrorResponse) o;
        return errorType == response.errorType && Objects.equals(errorMap, response.errorMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorType, errorMap);
    }
}
