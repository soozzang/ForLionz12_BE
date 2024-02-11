package likelion.site.domain.questionpost.dto.response.question;

import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.Getter;

@Getter
public class QuestionPostIdResponseDto {

    Long id;

    public QuestionPostIdResponseDto(QuestionPost questionPost) {
        this.id = questionPost.getId();
    }
}
