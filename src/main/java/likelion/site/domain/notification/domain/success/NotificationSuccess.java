package likelion.site.domain.notification.domain.success;

import likelion.site.global.exception.SuccessDefault;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum NotificationSuccess implements SuccessDefault {

    NOTIFICATION_CREATED_SUCCESS(HttpStatus.CREATED, "공지사항 생성 성공"),
    NOTIFICATION_UPDATED_SUCCESS(HttpStatus.OK, "공지사항 수정 성공"),
    GET_NOTIFICATION_SUCCESS(HttpStatus.OK, "공지사항 조회 성공");

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
