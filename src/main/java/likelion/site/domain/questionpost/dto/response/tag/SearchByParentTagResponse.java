package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchByParentTagResponse {

    List<ChildTagResponseDto> childTags;
    List<QuestionPostResponseDto> questionPosts;
}
