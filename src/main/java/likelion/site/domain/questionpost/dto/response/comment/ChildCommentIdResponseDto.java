package likelion.site.domain.questionpost.dto.response.comment;

import likelion.site.domain.questionpost.domain.ChildComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChildCommentIdResponseDto {

    Long id;

    public ChildCommentIdResponseDto(ChildComment childComment) {
        this.id = childComment.getId();
    }
}
