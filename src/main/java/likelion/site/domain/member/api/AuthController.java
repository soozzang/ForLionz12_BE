package likelion.site.domain.member.api;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import likelion.site.domain.member.dto.request.MemberRequest;
import likelion.site.domain.member.dto.response.TokenResponse;
import likelion.site.domain.member.dto.request.TokenRequest;
import likelion.site.domain.member.repository.RefreshTokenRepository;
import likelion.site.domain.member.service.AuthService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<?>> signup(@RequestBody MemberRequest memberRequestDto) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(MEMBER_CREATED_SUCCESS,authService.signup(memberRequestDto)));
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@RequestBody LoginRequestDto loginRequestDto) {
        TokenResponse tokenResponse = authService.login(loginRequestDto);
//        ResponseCookie cookie = authService.makeCookie(tokenResponse.getRefreshToken());
        return ResponseEntity.ok()
//                .header("Set-Cookie", cookie.toString())
                .body(ApiResponse.createSuccess(LOGIN_SUCCESS, tokenResponse));
    }

    @CrossOrigin(origins = "https://lionz12.netlify.app/", allowCredentials = "true" )
    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<TokenResponse>> reissue(@CookieValue("refreshToken") String refreshToken) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(TOKEN_CREATE_SUCCESS, authService.reissue(refreshToken)));
    }

//    @Data
//    @NoArgsConstructor
//    static class accessDTO {
//        String r;
//    }

    @Data
    public static class LoginRequestDto {
        String email;
        String password;

        public UsernamePasswordAuthenticationToken toAuthentication() {
            return new UsernamePasswordAuthenticationToken(email, password);
        }
    }

}