package likelion.site.domain.notification.dto.response;

import likelion.site.domain.notification.domain.Notification;
import lombok.Getter;

@Getter
public class NotificationIdResponse {

    Long id;

    public NotificationIdResponse(Notification notification) {
        this.id = notification.getId();
    }
}
