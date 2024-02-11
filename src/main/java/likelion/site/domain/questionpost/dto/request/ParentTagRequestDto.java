package likelion.site.domain.questionpost.dto.request;

import likelion.site.domain.questionpost.domain.ParentTag;
import lombok.Getter;

@Getter
public class ParentTagRequestDto {

    String name;

    public ParentTag toEntity() {
        return ParentTag.builder()
                .name(name)
                .build();
    }
}
