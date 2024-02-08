package likelion.site.global.exception.exceptions;

import likelion.site.global.exception.CustomError;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class BadElementException extends NoSuchElementException {

    private final CustomError customError;
    private final String message;

    public BadElementException(CustomError customError) {
        this.customError = customError;
        this.message = customError.getMessage();
    }
}
