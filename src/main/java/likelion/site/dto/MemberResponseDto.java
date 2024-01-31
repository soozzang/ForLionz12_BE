package likelion.site.dto;

import likelion.site.domain.Member;
import likelion.site.domain.Part;
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

    public MemberResponseDto(Member member) {
    }

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getEmail(), member.getId(),member.getName(), member.getPart(), member.getIntroduction(), member.getGithubAddress(), member.getInstagramId());
    }
}