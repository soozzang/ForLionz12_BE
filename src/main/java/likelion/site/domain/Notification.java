package likelion.site.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

public class Notification {
    @Id @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String title;

    @Column(length = 50000)
    private String content;

    @Enumerated(EnumType.STRING)
    private Part part;

    private LocalDateTime createdAt;
}
