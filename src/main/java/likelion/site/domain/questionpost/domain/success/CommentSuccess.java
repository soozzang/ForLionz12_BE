package likelion.site.domain.questionpost.domain.success;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum CommentSuccess implements SuccessDefault {

    COMMENT_CREATED_SUCCESS(HttpStatus.CREATED, "댓글 생성 성공"),
    COMMENT_UPDATED_SUCCESS(HttpStatus.OK, "댓글 수정 성공"),
    GET_COMMENT_SUCCESS(HttpStatus.OK, "댓글 조회 성공");

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
