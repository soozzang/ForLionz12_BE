package likelion.site.global.exception;

import jakarta.validation.ConstraintViolationException;
import likelion.site.global.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

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
    public ApiResponse<?> currentMemberException(CurrentMemberException e) {
        return ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage());
    }

    @ExceptionHandler(DuplicateMemberError.class)
    public ApiResponse<?> duplicateMemberException(DuplicateMemberError e) {
        return ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage());
    }

    @ExceptionHandler(BadCategoryException.class)
    public ApiResponse<?> badCategoryException(BadCategoryException e) {
        return ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage());
    }

    @ExceptionHandler(BadPartException.class)
    public ApiResponse<?> badCategoryException(BadPartException e) {
        return ApiResponse.createError(ERROR_MESSAGE + e.getCustomError().getHttpStatus() + " " + e.getMessage());
    }
}
