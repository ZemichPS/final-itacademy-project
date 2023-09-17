package by.ITAcademy.UserMicroService.controllers;

import by.itacademy.sharedresource.core.dto.ErrorResponse;
import by.itacademy.sharedresource.core.dto.StructuredErrorResponse;
import by.ITAcademy.UserMicroService.core.exception.IncorrectPasswordException;
import by.ITAcademy.UserMicroService.core.exception.InvalidDtoException;
import by.ITAcademy.UserMicroService.core.exception.LoginException;
import by.ITAcademy.UserMicroService.core.exception.VerificationException;
import by.itacademy.sharedresource.core.enums.ErrorType;
import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


import javax.management.JMException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


@RestControllerAdvice
@Slf4j
public class UserExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StructuredErrorResponse> handleInvalidArgument(ConstraintViolationException violationException) {
        StructuredErrorResponse response = new StructuredErrorResponse(ErrorType.ERROR.STRUCTURED_ERROR, new HashMap<>());

        violationException.getConstraintViolations().stream().forEach(violation -> {
            response.getErrorMap().put(violation.getPropertyPath().toString(), violation.getMessage());
            log.error("Структурная ошибка ", violationException);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    // input dto is invalid
    @ExceptionHandler(InvalidDtoException.class)
    public ResponseEntity<StructuredErrorResponse> handleInvalidArgument(InvalidDtoException invalidDtoException) {
        StructuredErrorResponse response = new StructuredErrorResponse(ErrorType.STRUCTURED_ERROR, new HashMap<>());

        List<ObjectError> errors = invalidDtoException.getErrors();
        invalidDtoException.getErrors().stream().forEach(objectError -> {
            response.getErrorMap().put(objectError.getCode().toString(), objectError.getDefaultMessage());
            log.error("Структурная ошибка ", objectError.getCode() + ": " + objectError.getDefaultMessage());
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> partOfRequestNotFound(MissingServletRequestPartException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, "Отсутствует часть составного запроса. Измените запрос и отправьте его ещё раз. " + ex.getMessage());
        log.error("Структурная ошибка ", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponse> incorrectPassword(IncorrectPasswordException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.STRUCTURED_ERROR, ex.getMessage());
        log.error("Структурная ошибка ", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ErrorResponse> accessFail(AccountStatusException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.STRUCTURED_ERROR, "Permission fail. " + ex.getMessage());
        log.error("\"Permission fail.", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JMException.class)
    public ResponseEntity<ErrorResponse> invalidJwtToken(JMException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, ex.getMessage());
        log.error("JWT token exception", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> requestParameterIsMissing(MissingServletRequestParameterException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, "Отсутствует параметр в запросе. Измените запрос и отправьте его ещё раз." + ex.getMessage());
        log.error("Структурная ошибка ", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> requestParameterIsMissing(LoginException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, ex.getMessage());
        log.error("login error", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataIntegrityViolation(DataIntegrityViolationException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, ex.getMessage());
        log.error("Структурная ошибка ", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, "Метод не поддерживается этим URL. " + ex.getMessage());
        log.error("Структурная ошибка ", ex);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MessagingException.class, MailAuthenticationException.class})
    public ResponseEntity<ErrorResponse> failedToSendEmail(MessagingException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, "Не удалось отправить Email уведомление с кодом активации. ");
        log.error("Ошибка на стороне сервера", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VerificationException.class)
    public ResponseEntity<ErrorResponse> failedToVerificationAccount(VerificationException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, ex.getMessage());
        log.error("Internal server error", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> sqlBadRequest(SQLException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, "Ошибка sql запроса. " + ex.getMessage());
        log.error("Ошибка на стороне серевера ", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(KafkaException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(KafkaException ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, "Метод не поддерживается этим URL. " + ex.getMessage());
        log.error("Ошибка на стороне серевера ", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception ex) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, "Внутренняя ошибка сервера. Сервер не смог корректно обработать запрос " + ex.getMessage());
        log.error("Ошибка на стороне серевера. ", ex);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Error.class)
    public ResponseEntity<ErrorResponse> handleInnerError(Error error) {
        ErrorResponse response = new ErrorResponse(ErrorType.ERROR, "Внутренняя ошибка сервера. Сервер не смог корректно обработать запрос " + error.getMessage());
        log.error("Структурная ошибка ", error);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
