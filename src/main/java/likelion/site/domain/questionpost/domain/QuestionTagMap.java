package likelion.site.domain.questionpost.domain;

import jakarta.persistence.*;
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
    private QuestionPost questionPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private ChildTag childTag;

    @Builder
    public QuestionTagMap(QuestionPost questionPost, ChildTag childTag) {
        this.questionPost = questionPost;
        this.childTag = childTag;
    }
}
