package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.questionpost.domain.ChildTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChildTagResponseIdDto {

    Long childTagId;

    public ChildTagResponseIdDto(ChildTag childTag) {
        this.childTagId = childTag.getId();
    }
}

