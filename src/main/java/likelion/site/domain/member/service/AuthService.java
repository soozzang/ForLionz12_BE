package likelion.site.domain.member.service;

import jakarta.transaction.Transactional;
import likelion.site.domain.member.api.AuthController;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.domain.RefreshToken;
import likelion.site.domain.member.dto.request.MemberRequest;
import likelion.site.domain.member.dto.response.MemberResponseDto;
import likelion.site.domain.member.dto.response.TokenResponse;
import likelion.site.domain.member.repository.MemberRepository;
import likelion.site.domain.member.repository.RefreshTokenRepository;
import likelion.site.global.exception.CustomError;
import likelion.site.global.exception.exceptions.DuplicateMemberError;
import likelion.site.global.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;

    @Transactional
    public MemberResponseDto signup(MemberRequest memberRequestDto) {
        if (memberRepository.existsByEmail(memberRequestDto.getEmail())) {
            throw new DuplicateMemberError(CustomError.DUPLICATE_MEMBER_ERROR);
        }

        Member member = memberRequestDto.toMember(passwordEncoder);
        return new MemberResponseDto(memberRepository.save(member));
    }

    @Transactional
    public TokenResponse login(AuthController.LoginRequestDto loginRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenResponse tokenDto = tokenProvider.generateTokenDto(authentication);

        if(refreshTokenRepository.findByKey(authentication.getName()).isEmpty()) {
            RefreshToken refreshToken = RefreshToken.builder()
                    .key(authentication.getName())
                    .value(tokenDto.getRefreshToken())
                    .build();
            refreshTokenRepository.save(refreshToken);
        }

        // 5. 토큰 발급
        return tokenDto;
    }

    public ResponseCookie makeCookie(String refreshToken) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .maxAge(1000 * 24 * 60 * 60)
                .path("/https://lionz12.netlify.app")
                .build();
    }

    @Transactional
    public TokenResponse reissue(String refreshToken) {
        // 1. Refresh Token 검증
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token 이 유효하지 않습니다.");
        }

        // 2. Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(refreshToken);



        // 5. 새로운 토큰 생성

        // 6. 저장소 정보 업데이트

        // 토큰 발급
        return tokenProvider.generateAccessTokenDto(authentication);
    }
}