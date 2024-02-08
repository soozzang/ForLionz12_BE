package likelion.site.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CustomError {

    CURRENT_MEMBER_ERROR(HttpStatus.UNAUTHORIZED, "로그인 된 사용자가 없습니다."),
    DUPLICATE_MEMBER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "이미 가입되어 있는 유저입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
