package likelion.site.domain.questionpost.dto.response.comment;

import likelion.site.domain.questionpost.domain.ChildComment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChildCommentResponseDto {

    Long childCommentId;
    Long memberId;
    String name;
    String memberImageUrl;
    String part;
    LocalDateTime createdAt;
    String content;

    public ChildCommentResponseDto(ChildComment childComment) {
        this.childCommentId = childComment.getId();
        this.memberId = childComment.getMember().getId();
        this.name = childComment.getMember().getName();
        this.part = childComment.getMember().getPart().toString();
        this.memberImageUrl = childComment.getMember().getImageUrl();
        this.createdAt = childComment.getCreatedAt();
        this.content = childComment.getContent();
    }
}
