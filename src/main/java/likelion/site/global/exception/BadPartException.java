package likelion.site.global.exception;

import lombok.Getter;

@Getter
public class BadPartException extends RuntimeException {

    private final CustomError customError;
    private final String message;

    public BadPartException(CustomError customError) {
        this.customError = customError;
        this.message = customError.getMessage();
    }
}
