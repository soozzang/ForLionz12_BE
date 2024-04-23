package likelion.site.domain.questionpost.dto.response.question;

import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class QuestionPostDetailResponseDto {

    Long questionId;
    Long memberId;
    String name;
    String memberImageUrl;
    String title;
    String content;
    List<String> postImageUrls;
    LocalDateTime createdAt;
    List<String> childTags;
    Integer commentCount;
    Integer likes;
    boolean isLiked;

    public QuestionPostDetailResponseDto(QuestionPost questionPost, List<String> childTag, Boolean liked) {
        questionId = questionPost.getId();
        postImageUrls = questionPost.getImageUrls();
        memberId = questionPost.getMember().getId();
        title = questionPost.getTitle();
        name = questionPost.getMember().getName();
        memberImageUrl = questionPost.getMember().getImageUrl();
        content = questionPost.getContent();
        createdAt = questionPost.getCreatedAt();
        childTags = childTag;
        commentCount = getCommentCount(questionPost.getComments());
        likes = questionPost.getLikes().size();
        isLiked = liked;
    }

    public int getCommentCount(List<Comment> comments) {
        int commentCount = comments.size();
        for (Comment comment : comments) {
            commentCount += comment.getChildComments().size();
        }
        return commentCount;
    }
}
