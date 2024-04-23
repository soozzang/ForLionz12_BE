package likelion.site.domain.questionpost.domain.success;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum QuestionPostSuccess implements SuccessDefault {

    QUESTION_POST_CREATED_SUCCESS(HttpStatus.CREATED, "질문 게시글 생성 성공"),
    QUESTION_POST_UPDATED_SUCCESS(HttpStatus.OK, "질문 게시글 수정 성공"),
    GET_QUESTION_POST_SUCCESS(HttpStatus.OK, "질문 게시글 조회 성공"),
    DELETE_QUESTION_POST_SUCCESS(HttpStatus.OK, "질문 게시글 삭제 성공"),
    FILE_CONVERT_SUCCESS(HttpStatus.OK, "파일 이미지 변환 성공"),
    LIKE_SUCCESS(HttpStatus.OK, "좋아요 누르기 성공"),
    IS_LIKED_SUCCESS(HttpStatus.OK, "해당 게시글 좋아요 여부 조회 성공");

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
