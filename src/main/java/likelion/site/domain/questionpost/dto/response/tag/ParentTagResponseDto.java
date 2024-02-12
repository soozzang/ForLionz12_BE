package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ParentTagResponseDto {

    Long parentTagId;
    String name;

    public ParentTagResponseDto(ParentTag parentTag) {
        this.parentTagId = parentTag.getId();
        this.name = parentTag.getName();
    }
}
