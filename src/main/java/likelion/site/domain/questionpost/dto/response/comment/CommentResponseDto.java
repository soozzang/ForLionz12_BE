package likelion.site.domain.questionpost.dto.response.comment;

import likelion.site.domain.questionpost.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    Long commentId;
    String name;
    String memberImageUrl;
    String part;
    LocalDateTime createdAt;
    String content;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.name = comment.getMember().getName();
        this.memberImageUrl = comment.getMember().getImageUrl();
        this.part = comment.getMember().getPart().toString();
        this.createdAt = comment.getCreatedAt();
        this.content = comment.getContent();
    }
}
