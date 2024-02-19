package likelion.site.domain.questionpost.dto.request;

import lombok.Getter;

@Getter
public class UpdateChildCommentRequest {

    Long ChildCommentId;
    String content;
}
