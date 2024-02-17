package likelion.site.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CustomError {

    CURRENT_MEMBER_ERROR(HttpStatus.UNAUTHORIZED, "로그인 된 사용자가 없습니다."),
    DUPLICATE_MEMBER_ERROR(HttpStatus.BAD_REQUEST, "이미 가입되어 있는 유저입니다."),
    BAD_CATEGORY_ERROR(HttpStatus.BAD_REQUEST, "카테고리 형식이 올바르지 않습니다."),
    BAD_PART_ERROR(HttpStatus.BAD_REQUEST, "파트 형식이 올바르지 않습니다."),
    BAD_ELEMENT_ERROR(HttpStatus.BAD_REQUEST, "없는 데이터에 대한 요청입니다."),
    OVER_SUBMISSION_EXCEPTION(HttpStatus.BAD_REQUEST, "한 과제물 당 하나의 제출물만 제출 가능합니다"),
    BAD_FILE_EXCEPTION(HttpStatus.BAD_REQUEST, "이미지 파일 형식이 올바르지 않습니다."),
    AUTHORIZATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    REFRESHTOKEN_EXCEPTION(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh토큰입니다."),
    NO_SUBMISSION_EXCEPTION(HttpStatus.OK, "제출 과제가 없습니다."),
    NO_CONTENT_EXCEPTION(HttpStatus.OK, "내용이 없는 응답입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
