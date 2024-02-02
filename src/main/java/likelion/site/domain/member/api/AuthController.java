package likelion.site.domain.member.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.dto.MemberRequestDto;
import likelion.site.domain.member.dto.MemberResponseDto;
import likelion.site.domain.member.dto.TokenDto;
import likelion.site.domain.member.dto.TokenRequestDto;
import likelion.site.domain.member.repository.RefreshTokenRepository;
import likelion.site.domain.member.service.AuthService;
import likelion.site.domain.member.service.MemberService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "회원가입/로그인/토큰재발급")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Operation(summary = "회원가입", description = "partName에는 BE/FE/ALL이 들어갈 수 있습니다.")
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody accessDTO accessDTO) {
        TokenRequestDto tokenRequestDto = new TokenRequestDto();
        tokenRequestDto.setAccessToken(accessDTO.accessToken);
        tokenRequestDto.setRefreshToken(refreshTokenRepository.findByAccessToken(accessDTO.getAccessToken()).get().getValue());
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @Data
    @NoArgsConstructor
    static class accessDTO {
        String accessToken;
    }
}