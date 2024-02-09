package likelion.site.domain.questionpost.dto.response.question;

import lombok.Getter;

@Getter
public class QuestionPostIdResponseDto {

    Long id;

    public QuestionPostIdResponseDto(Long id) {
        this.id = id;
    }
}
