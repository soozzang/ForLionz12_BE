package likelion.site.domain.questionpost.dto.response.comment;

import likelion.site.domain.questionpost.domain.ChildComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChildCommentResponseDto {

    Long id;
    Long memberId;
    String content;

    public ChildCommentResponseDto(ChildComment childComment) {
        this.id = childComment.getId();
        this.memberId = childComment.getMember().getId();
        this.content = childComment.getContent();
    }
}
