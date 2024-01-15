package likelion.site.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
public class QuestionPost {
    @Id @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Column(length = 50000)
    private String content;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "questionPost")
    private List<Comment> comments;
}
