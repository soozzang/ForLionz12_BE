package likelion.site.domain.questionpost.dto.request;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.QuestionPost;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QuestionPostRequestDto {

    String title;
    String content;
    List<String> postImageUrls;

    public QuestionPost toEntity(Member member) {
        return QuestionPost.builder()
                .title(title)
                .content(content)
                .imageUrls(postImageUrls)
                .member(member)
                .build();
    }
}
