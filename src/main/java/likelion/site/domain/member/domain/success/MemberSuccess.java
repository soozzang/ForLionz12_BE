package likelion.site.domain.member.domain.success;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MemberSuccess implements SuccessDefault {

    MEMBER_CREATED_SUCCESS(HttpStatus.CREATED, "멤버 생성 성공"),
    MEMBER_UPDATED_SUCCESS(HttpStatus.OK, "멤버 정보 수정 성공"),
    GET_MEMBER_SUCCESS(HttpStatus.OK, "멤버 조회 성공"),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공");

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
