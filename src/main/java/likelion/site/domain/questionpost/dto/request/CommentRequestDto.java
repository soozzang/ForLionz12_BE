package likelion.site.domain.questionpost.dto.request;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    String content;
    Long questionPostId;

    public Comment toEntity(QuestionPost questionPost, Member member) {
        return Comment.builder()
                .content(content)
                .questionPost(questionPost)
                .member(member)
                .build();
    }
}
