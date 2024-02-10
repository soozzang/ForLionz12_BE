package likelion.site.domain.questionpost.dto.response.question;

import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QuestionPostResponseDto {

    Long id;
    Long memberId;
    String title;
    String content;
    LocalDateTime createdAt;

    public QuestionPostResponseDto(QuestionPost questionPost) {
        id = questionPost.getId();
        memberId = questionPost.getMember().getId();
        title = questionPost.getTitle();
        content = questionPost.getContent();
        createdAt = questionPost.getCreatedAt();
    }
}
