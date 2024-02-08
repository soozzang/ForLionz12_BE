package likelion.site.global.exception.exceptions;

import likelion.site.global.exception.CustomError;
import lombok.Getter;

@Getter
public class DuplicateMemberError extends RuntimeException {

    private final CustomError customError;
    private final String message;

    public DuplicateMemberError(CustomError customError) {
        this.customError = customError;
        this.message = customError.getMessage();
    }
}
