package likelion.site.domain.questionpost.dto.request;

import lombok.Getter;

@Getter
public class ChildCommentRequestDto {

    String content;
    Long commentId;
}
