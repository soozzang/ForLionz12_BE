package likelion.site.domain.questionpost.domain.success;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum TagSuccess implements SuccessDefault {

    TAG_CREATED_SUCCESS(HttpStatus.CREATED, "태그 생성 성공"),
    GET_TAG_SUCCESS(HttpStatus.OK, "태그 조회 성공");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
