package likelion.site.domain.questionpost.dto.request;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import lombok.Getter;

@Getter
public class ChildTagRequestDto {

    String name;
    Long parentTagId;

    public ChildTag toEntity(ParentTag parentTag) {
        return ChildTag.builder()
                .name(name)
                .parentTag(parentTag)
                .build();
    }
}
