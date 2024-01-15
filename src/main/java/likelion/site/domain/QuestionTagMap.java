package likelion.site.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class QuestionTagMap {
    @Id @GeneratedValue
    @Column(name = "questiontagmap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionPost questionPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;
}
