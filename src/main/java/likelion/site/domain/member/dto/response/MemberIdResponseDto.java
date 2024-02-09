package likelion.site.domain.member.dto.response;

import likelion.site.domain.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberIdResponseDto {

    Long id;

    public MemberIdResponseDto(Member member) {
        this.id = member.getId();
    }
}
