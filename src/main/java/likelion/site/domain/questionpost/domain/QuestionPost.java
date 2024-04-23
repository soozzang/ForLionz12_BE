package likelion.site.domain.questionpost.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import likelion.site.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class QuestionPost {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull(message = "질문 글의 제목은 null이 될 수 없습니다.")
    private String title;

    @Column(length = 50000)
    @NotNull(message = "질문 글의 내용은 null이 될 수 없습니다.")
    private String content;

    private final LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @OneToMany(mappedBy = "questionPost", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> imageUrls;

    @OneToMany(mappedBy = "questionPost", cascade = CascadeType.REMOVE)
    private List<Likes> likes;

    @Builder
    public QuestionPost(Member member, String title, String content, List<String> imageUrls) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
    }

    public void updateQuestionPost(String title, String content, List<String> imageUrls) {
        this.title = title;
        this.content = content;
        this.imageUrls = imageUrls;
    }
}
