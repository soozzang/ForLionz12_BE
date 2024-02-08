package likelion.site.domain.questionpost.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ParentTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_tag_id")
    private Long id;

    @NotNull(message = "부모 태그의 이름은 null이 될 수 없습니다.")
    private String name;

    @OneToMany(mappedBy = "parenttag")
    private List<ChildTag> childtags = new ArrayList<>();

    public void addChildTag(ChildTag childTag) {
        this.childtags.add(childTag);
    }

    @Builder
    public ParentTag(String name) {
        this.name = name;
    }


}
