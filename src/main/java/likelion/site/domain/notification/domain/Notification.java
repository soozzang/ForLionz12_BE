package likelion.site.domain.notification.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String title;

    @Column(length = 50000)
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationPart notificationPart;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Notification(String title, NotificationPart notificationPart, String content) {
        this.title = title;
        this.notificationPart = notificationPart;
        this.content = content;
    }

    public void updateNotification(String title, String content, NotificationPart notificationPart) {
        this.title = title;
        this.content = content;
        this.notificationPart = notificationPart;
    }
}
