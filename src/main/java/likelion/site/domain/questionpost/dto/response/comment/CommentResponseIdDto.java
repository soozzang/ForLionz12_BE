package likelion.site.domain.questionpost.dto.response.comment;

import lombok.Getter;

@Getter
public class CommentResponseIdDto {

    Long id;

    public CommentResponseIdDto(Long commentId) {
        this.id = commentId;
    }
}
