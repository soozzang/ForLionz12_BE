package likelion.site.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

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
    private Part part;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public Notification(String title, Part part, String content) {
        this.title = title;
        this.part = part;
        this.content = content;
    }

    public void updateNotification(String title, String content, Part part) {
        this.title = title;
        this.content = content;
        this.part = part;
    }
}
