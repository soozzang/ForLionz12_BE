package likelion.site.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.dto.MemberResponseDto;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.util.SecurityUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "사용자")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "내 정보 조회", description = "해당 토큰의 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMyInfo() {
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @Operation(summary = "id를 통해 특정 멤버를 조회")
    @GetMapping
    public ResponseEntity<MemberResponseDto> findMemberInfoById(@RequestParam Long id) {
        return ResponseEntity.ok(memberService.findMemberInfoById(id));
    }

    @Operation(summary = "나의 한 줄 소개 업데이트", description = "해당 토큰의 사용자의 한 줄 소개를 업데이트 합니다.")
    @PutMapping("/comment")
    public ResponseEntity<MemberResponse> updateMemberIntroduction(@RequestBody MemberIntroductionUpdateRequest request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updateIntroduction(member.getId(), request.getIntroduction());
        return ResponseEntity.ok().body(new MemberResponse(member));
    }

    @Operation(summary = "나의 깃허브 주소 업데이트", description = "해당 토큰의 사용자의 깃허브 주소를 업데이트 합니다.")
    @PutMapping("/github")
    public ResponseEntity<MemberResponse> updateMemberGithubAddress(@RequestBody MemberGithubAddressUpdateRequest request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updateGithubAddress(member.getId(), request.getGithubAddress());
        return ResponseEntity.ok().body(new MemberResponse(member));
    }

    @Operation(summary = "나의 인스타그램 아이디 업데이트", description = "해당 토큰의 사용자의 인스타그램 아이디를 업데이트 합니다.")
    @PutMapping("/instagram")
    public ResponseEntity<MemberResponse> updateMemberInstagramId(@RequestBody MemberInstagramIdUpdateRequest request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updateIntroduction(member.getId(), request.getInstagramId());
        return ResponseEntity.ok(new MemberResponse(member));
    }

    @Data
    public static class MemberResponse {
        Long id;

        public MemberResponse(Member member) {
            this.id =member.getId();
        }
    }

    @Data
    public static class MemberIntroductionUpdateRequest {
        String introduction;
    }

    @Data
    public static class MemberGithubAddressUpdateRequest {
        String githubAddress;
    }

    @Data
    public static class MemberInstagramIdUpdateRequest {
        String instagramId;
    }

}