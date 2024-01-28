package likelion.site.domain;

import jakarta.persistence.*;
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
