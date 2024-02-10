package likelion.site.domain.notification.dto.request;

import likelion.site.domain.notification.domain.Notification;
import likelion.site.domain.notification.domain.NotificationPart;
import lombok.Getter;

@Getter
public class NotificationRequest {

    String title;
    String content;
    String part;

    public Notification toEntity() {
        NotificationPart notificationPart = NotificationPart.findByName(part);
        return Notification.builder()
                .title(title)
                .notificationPart(notificationPart)
                .content(content)
                .build();
    }
}
