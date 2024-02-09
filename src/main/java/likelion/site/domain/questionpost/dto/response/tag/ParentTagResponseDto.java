package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ParentTagResponseDto {

    Long id;
    String name;
//    List<ChildTag> childTags;
    List<ChildTagResponseDto> childTagResponseDto;

    public ParentTagResponseDto(ParentTag parentTag, List<ChildTagResponseDto> childTagResponseDto) {
        this.id = parentTag.getId();
        this.name = parentTag.getName();
        this.childTagResponseDto = childTagResponseDto;
    }
}
