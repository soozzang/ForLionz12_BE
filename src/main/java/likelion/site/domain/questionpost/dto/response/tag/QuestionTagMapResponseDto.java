package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.questionpost.domain.QuestionTagMap;

public class QuestionTagMapResponseDto {

    Long childTagId;
    Long questionPostId;

    public QuestionTagMapResponseDto(QuestionTagMap questionTagMap) {
        this.childTagId = questionTagMap.getChildTag().getId();
        this.questionPostId = questionTagMap.getQuestionPost().getId();
    }
}
