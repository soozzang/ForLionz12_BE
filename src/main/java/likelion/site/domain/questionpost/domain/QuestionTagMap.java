package likelion.site.domain.questionpost.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class QuestionTagMap {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questiontagmap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    @NotNull(message = "질문 글의 id는 null이 될 수 없습니다.")
    private QuestionPost questionPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    @NotNull(message = "자식 태그의 id는 null이 될 수 없습니다.")
    private ChildTag childTag;

    @Builder
    public QuestionTagMap(QuestionPost questionPost, ChildTag childTag) {
        this.questionPost = questionPost;
        this.childTag = childTag;
    }
}
