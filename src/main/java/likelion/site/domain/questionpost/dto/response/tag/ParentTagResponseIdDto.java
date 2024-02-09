package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.questionpost.domain.ParentTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParentTagResponseIdDto {

    Long id;

    public ParentTagResponseIdDto(ParentTag parentTag) {
        this.id = parentTag.getId();
    }
}
