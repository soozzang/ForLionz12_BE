package likelion.site.domain.member.dto;

import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.domain.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private Long id;
    private String name;
    private Part part;
    private String introduction;
    private String githubAddress;
    private String instagramId;
    private String imageUrl;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.part = member.getPart();
        this.introduction = member.getIntroduction();
        this.githubAddress = member.getGithubAddress();
        this.instagramId = member.getInstagramId();
        this.imageUrl = member.getImageUrl();
    }
}