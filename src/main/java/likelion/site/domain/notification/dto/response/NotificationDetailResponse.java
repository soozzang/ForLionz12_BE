package likelion.site.domain.notification.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import likelion.site.domain.notification.domain.Notification;
import likelion.site.domain.notification.domain.NotificationPart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class NotificationDetailResponse {

    Long id;
    String title;
    String content;
    NotificationPart part;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    @Builder
    public NotificationDetailResponse(Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.content = notification.getContent();
        this.part = notification.getNotificationPart();
        this.createdAt = notification.getCreatedAt();
    }

    public static List<NotificationDetailResponse> to(List<Notification> notifications) {
        List<NotificationDetailResponse> notificationDtoList = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDetailResponse dto = new NotificationDetailResponse(notification);
            notificationDtoList.add(dto);
        }
        return notificationDtoList;
    }
}
