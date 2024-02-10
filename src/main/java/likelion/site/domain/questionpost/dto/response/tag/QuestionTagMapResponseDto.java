package likelion.site.domain.questionpost.dto.response.tag;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionTagMapResponseDto {

    Long childTagId;
    Long questionPostId;
    String title;
    String content;
    String author;
    LocalDateTime createdAt;

    public QuestionTagMapResponseDto(QuestionTagMap questionTagMap) {
        this.childTagId = questionTagMap.getChildTag().getId();
        this.questionPostId = questionTagMap.getQuestionPost().getId();
        this.title = questionTagMap.getQuestionPost().getTitle();
        this.content = questionTagMap.getQuestionPost().getContent();
        this.author = questionTagMap.getQuestionPost().getMember().getName();
        this.createdAt = questionTagMap.getQuestionPost().getCreatedAt();
    }
}
