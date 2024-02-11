package likelion.site.domain.questionpost.dto.request;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.Getter;

@Getter
public class QuestionPostRequestDto {

    String title;
    String content;

    public QuestionPost toEntity(Member member) {
        return QuestionPost.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }
}
