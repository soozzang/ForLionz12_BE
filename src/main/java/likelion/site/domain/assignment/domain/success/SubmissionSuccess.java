package likelion.site.domain.assignment.domain.success;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum SubmissionSuccess implements SuccessDefault {

    SUBMISSION_CREATED_SUCCESS(HttpStatus.CREATED, "과제 제출 성공"),
    SUBMISSION_UPDATED_SUCCESS(HttpStatus.OK, "과제 제출물 수정 성공"),
    GET_SUBMISSION_SUCCESS(HttpStatus.OK, "과제 제출물 조회 성공");

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
