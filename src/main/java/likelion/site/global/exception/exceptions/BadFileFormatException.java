package likelion.site.global.exception.exceptions;

import likelion.site.global.exception.CustomError;
import lombok.Getter;

@Getter
public class BadFileFormatException extends RuntimeException{

    private final CustomError customError;
    private final String message;

    public BadFileFormatException(CustomError customError) {
        this.customError = customError;
        this.message = customError.getMessage();
    }
}
