package likelion.site.domain.notification.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Entity
@NoArgsConstructor
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    @NotNull(message = "제목은 null이 될 수 없습니다.")
    private String title;

    @NotNull(message = "글 내용은 null이 될 수 없습니다.")
    @Column(length = 50000)
    private String content;

    @NotNull(message = "공지사항의 파트는 null이 될 수 없습니다.")
    @Enumerated(EnumType.STRING)
    private NotificationPart notificationPart;

    private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

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
