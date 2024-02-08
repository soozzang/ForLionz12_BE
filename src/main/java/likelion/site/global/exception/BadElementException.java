package likelion.site.global.exception;

import lombok.Getter;

@Getter
public class BadElementException extends java.util.NoSuchElementException {

    private final CustomError customError;
    private final String message;

    public BadElementException(CustomError customError) {
        this.customError = customError;
        this.message = customError.getMessage();
    }
}
