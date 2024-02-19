package likelion.site.domain.questionpost.dto.request;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    Long commentId;
    String content;
}
