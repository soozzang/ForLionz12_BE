package likelion.site.domain.assignment.domain.success;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum AssignmentSuccess implements SuccessDefault {

    ASSIGNMENT_CREATED_SUCCESS(HttpStatus.CREATED, "과제 생성 성공"),
    ASSIGNMENT_UPDATED_SUCCESS(HttpStatus.OK, "과제 수정 성공"),
    GET_ASSIGNMENT_SUCCESS(HttpStatus.OK, "과제 조회 성공");

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