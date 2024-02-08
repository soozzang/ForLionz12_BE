package likelion.site.global.exception;

import lombok.Getter;

@Getter
public class BadCategoryException extends RuntimeException{

    private final CustomError customError;
    private final String message;

    public BadCategoryException(CustomError customError) {
        this.customError = customError;
        this.message = customError.getMessage();
    }
}
