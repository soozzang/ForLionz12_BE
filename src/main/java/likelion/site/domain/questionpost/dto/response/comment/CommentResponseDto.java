package likelion.site.domain.questionpost.dto.response.comment;

import likelion.site.domain.questionpost.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    Long id;
    Long memberId;
    String content;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.memberId = comment.getMember().getId();
        this.content = comment.getContent();
    }
}
