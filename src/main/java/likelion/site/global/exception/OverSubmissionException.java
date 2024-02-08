package likelion.site.global.exception;

import lombok.Getter;

@Getter
public class OverSubmissionException extends RuntimeException {

    private final CustomError customError;
    private final String message;

    public OverSubmissionException(CustomError customError) {
        this.customError = customError;
        this.message = customError.getMessage();
    }
}
