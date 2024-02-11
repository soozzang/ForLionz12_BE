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
    List<MultipartFile> file = new ArrayList<>();

    public QuestionPost toEntity(Member member, List<String> imageUrl) {
        return QuestionPost.builder()
                .title(title)
                .content(content)
                .imageUrl(imageUrl)
                .member(member)
                .build();
    }

    public QuestionPost toEntityWithImage(Member member, List<String> imageUrl) {
        return QuestionPost.builder()
                .title(title)
                .imageUrl(imageUrl)
                .content(content)
                .imageUrl(imageUrl)
                .member(member)
                .build();
    }
}
