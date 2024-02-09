package likelion.site.domain.member.dto.request;

import likelion.site.domain.member.domain.Authority;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.domain.Part;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDto {

    private String email;
    private String name;
    private String password;
    private String partName;

    public Member toMember(PasswordEncoder passwordEncoder) {
        Part part = Part.findByName(partName);
        return Member.builder()
                .email(email)
                .name(name)
                .password(passwordEncoder.encode(password))
                .authority(Authority.ROLE_USER)
                .part(part)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }

}