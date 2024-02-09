package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.questionpost.domain.ChildTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChildTagResponseDto {

    Long id;
    String name;

    public ChildTagResponseDto(ChildTag childTag) {
        this.id = childTag.getId();
        this.name = childTag.getName();
    }
}
