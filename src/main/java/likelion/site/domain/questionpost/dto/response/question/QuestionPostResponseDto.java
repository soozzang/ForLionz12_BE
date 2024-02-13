package likelion.site.domain.questionpost.dto.response.question;

import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QuestionPostResponseDto {

    Long questionId;
    Long memberId;
    String name;
    String memberImageUrl;
    String title;
    String content;
    List<String> postImageUrls;
    LocalDateTime createdAt;
    List<String> childTags;

    public QuestionPostResponseDto(QuestionPost questionPost, List<String> childTag) {
        questionId = questionPost.getId();
        postImageUrls = questionPost.getImageUrls();
        memberId = questionPost.getMember().getId();
        title = questionPost.getTitle();
        name = questionPost.getMember().getName();
        memberImageUrl = questionPost.getMember().getImageUrl();
        content = questionPost.getContent();
        createdAt = questionPost.getCreatedAt();
        childTags = childTag;
    }
}
