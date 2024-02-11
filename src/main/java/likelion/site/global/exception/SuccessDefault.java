package likelion.site.global.exception;

import org.springframework.http.HttpStatus;

public interface SuccessDefault {

    HttpStatus getHttpStatus();

    String getMessage();

}
