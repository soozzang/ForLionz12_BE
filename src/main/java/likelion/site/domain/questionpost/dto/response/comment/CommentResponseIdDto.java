package likelion.site.domain.questionpost.dto.response.comment;

import likelion.site.domain.questionpost.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponseIdDto {

    Long id;

    public CommentResponseIdDto(Comment comment) {
        this.id = comment.getId();
    }
}
