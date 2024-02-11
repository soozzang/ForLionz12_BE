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
    String name;
    String title;
    String content;
    String memberImageUrl;
    LocalDateTime createdAt;

    public QuestionPostResponseDto(QuestionPost questionPost) {
        id = questionPost.getId();
        memberId = questionPost.getMember().getId();
        title = questionPost.getTitle();
        name = questionPost.getMember().getName();
        memberImageUrl = questionPost.getMember().getImageUrl();
        content = questionPost.getContent();
        createdAt = questionPost.getCreatedAt();
    }
}
