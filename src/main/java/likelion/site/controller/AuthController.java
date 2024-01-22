package likelion.site.controller;


import likelion.site.dto.MemberRequestDto;
import likelion.site.dto.MemberResponseDto;
import likelion.site.dto.TokenDto;
import likelion.site.dto.TokenRequestDto;
import likelion.site.repository.RefreshTokenRepository;
import likelion.site.service.AuthService;
import likelion.site.service.MemberService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/hi")
    public String hi(@RequestBody MemberRequestDto memberRequestDto) {
        return "ResponseEntity.ok(authService.signup(memberRequestDto))";
    }


    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

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