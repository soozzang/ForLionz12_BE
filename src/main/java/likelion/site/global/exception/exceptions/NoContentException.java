package likelion.site.global.exception.exceptions;

import likelion.site.global.exception.CustomError;
import lombok.Getter;

@Getter
public class NoContentException extends RuntimeException {

    private final CustomError customError;
    private final String message;

    public NoContentException(CustomError customError) {
        this.customError = customError;
        this.message = customError.getMessage();
    }
}
