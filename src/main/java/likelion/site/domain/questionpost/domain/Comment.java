package likelion.site.domain.questionpost.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import likelion.site.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @NotNull(message = "질문 글의 id는 null이 될 수 없습니다.")
    private QuestionPost questionPost;

    @Column(length = 50000)
    @NotNull(message = "댓글의 내용은 null이 될 수 없습니다.")
    private String content;

    private final LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<ChildComment> childComments = new ArrayList<>();

    public void addChildComment(ChildComment childToAdd) {
        this.childComments.add(childToAdd);
    }

    @Builder
    public Comment(Member member, QuestionPost questionPost, String content) {
        this.member = member;
        this.questionPost = questionPost;
        this.content = content;
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
