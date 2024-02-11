package likelion.site.domain.questionpost.dto.request;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.ChildComment;
import likelion.site.domain.questionpost.domain.Comment;
import lombok.Getter;

@Getter
public class ChildCommentRequestDto {

    String content;
    Long commentId;

    public ChildComment toEntity(Comment comment, Member member) {
        return ChildComment.builder()
                .content(content)
                .comment(comment)
                .member(member)
                .build();
    }
}
