package likelion.site.domain.member.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.domain.success.MemberSuccess;
import likelion.site.domain.member.domain.success.TokenSuccess;
import likelion.site.domain.member.dto.request.MemberRequest;
import likelion.site.domain.member.dto.request.TokenDto;
import likelion.site.domain.member.dto.request.TokenRequestDto;
import likelion.site.domain.member.repository.RefreshTokenRepository;
import likelion.site.domain.member.service.AuthService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static likelion.site.domain.member.domain.success.MemberSuccess.LOGIN_SUCCESS;
import static likelion.site.domain.member.domain.success.MemberSuccess.MEMBER_CREATED_SUCCESS;
import static likelion.site.domain.member.domain.success.TokenSuccess.TOKEN_CREATE_SUCCESS;

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
    public ApiResponse<?> signup(@RequestBody MemberRequest memberRequestDto) {
        return ApiResponse.createSuccess(MEMBER_CREATED_SUCCESS,authService.signup(memberRequestDto));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ApiResponse.createSuccess(LOGIN_SUCCESS, authService.login(loginRequestDto));
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ApiResponse<TokenDto> reissue(@RequestBody accessDTO accessDTO) {
        TokenRequestDto tokenRequestDto = new TokenRequestDto();
        tokenRequestDto.setAccessToken(accessDTO.accessToken);
        tokenRequestDto.setRefreshToken(refreshTokenRepository.findByAccessToken(accessDTO.getAccessToken()).get().getValue());
        return ApiResponse.createSuccess(TOKEN_CREATE_SUCCESS, authService.reissue(tokenRequestDto));
    }

    @Data
    @NoArgsConstructor
    static class accessDTO {
        String accessToken;
    }

    @Data
    public static class LoginRequestDto {
        String email;
        String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

}