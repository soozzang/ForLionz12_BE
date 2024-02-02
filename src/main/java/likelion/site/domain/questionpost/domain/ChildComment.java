package likelion.site.domain.questionpost.domain;

import jakarta.persistence.*;
import likelion.site.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChildComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "childcomment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 50000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment comment;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @Builder
    public ChildComment(Member member, String content, Comment comment) {
        this.member = member;
        this.content = content;
        this.comment = comment;
    }
}
