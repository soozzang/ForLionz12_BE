package likelion.site.global.exception;

import jakarta.validation.ConstraintViolationException;
import likelion.site.global.ApiResponse;
import likelion.site.global.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalAdvice {

    final String ERROR_MESSAGE = "[ERROR] ";

//    @ExceptionHandler(Exception.class)
//    public ApiResponse<?> unexpectedException(Exception e) {
//        return ApiResponse.createError(ERROR_MESSAGE + "예기치 못한 오류가 발생하였습니다.");
//    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse<?> constraintViolationException(ConstraintViolationException e) {
        return ApiResponse.createError(ERROR_MESSAGE + extractErrorMessage(e));
    }

    private String extractErrorMessage(ConstraintViolationException e) {
        String[] tmp = e.getMessage().split("messageTemplate='");
        String[] message = tmp[1].split("'}\n]");
        return message[0];
    }


    @ExceptionHandler(CurrentMemberException.class)
    public ResponseEntity<ApiResponse<?>> currentMemberException(CurrentMemberException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage()));
    }

    @ExceptionHandler(DuplicateMemberError.class)
    public ResponseEntity<ApiResponse<?>> duplicateMemberException(DuplicateMemberError e) {
        return ResponseEntity.badRequest().body(ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage()));
    }

    @ExceptionHandler(BadCategoryException.class)
    public ResponseEntity<ApiResponse<?>> badCategoryException(BadCategoryException e) {
        return ResponseEntity.badRequest().body(ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage()));
    }

    @ExceptionHandler(BadPartException.class)
    public ResponseEntity<ApiResponse<?>> badPartException(BadPartException e) {
        return ResponseEntity.badRequest().body(ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage()));
    }

    @ExceptionHandler(BadElementException.class)
    public ResponseEntity<ApiResponse<?>> noSuchElementException(BadElementException e) {
        return ResponseEntity.badRequest().body(ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage()));
    }

    @ExceptionHandler(OverSubmissionException.class)
    public ResponseEntity<ApiResponse<?>> overSubmissionException(OverSubmissionException e) {
        return ResponseEntity.badRequest().body(ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage()));
    }

    @ExceptionHandler(BadFileFormatException.class)
    public ResponseEntity<ApiResponse<?>> badFileFormatException(BadFileFormatException e) {
        return ResponseEntity.badRequest().body(ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage()));
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<ApiResponse<?>> authorizationException(AuthorizationException e) {
        return ResponseEntity.badRequest().body(ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage()));
    }
}
