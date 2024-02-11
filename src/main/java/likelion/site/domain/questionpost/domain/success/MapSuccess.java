package likelion.site.domain.questionpost.domain.success;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MapSuccess implements SuccessDefault {

    MAP_CREATED_SUCCESS(HttpStatus.CREATED, "매핑 정보 생성 성공"),
    GET_MAP_SUCCESS(HttpStatus.OK, "매핑 정보 조회 성공");

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
