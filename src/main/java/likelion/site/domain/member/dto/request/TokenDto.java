package likelion.site.domain.member.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenExpiresIn;
}