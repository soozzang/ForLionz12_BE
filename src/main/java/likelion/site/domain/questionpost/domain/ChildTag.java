package likelion.site.domain.questionpost.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChildTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @NotNull(message = "부모 태그의 id는 null이 될 수 없습니다.")
    private ParentTag parenttag;

    @NotNull(message = "자식 태그의 이름은 null이 될 수 없습니다.")
    private String name;

    @Builder
    public ChildTag(String name,ParentTag parentTag) {
        this.name = name;
        this.parenttag = parentTag;
    }

}