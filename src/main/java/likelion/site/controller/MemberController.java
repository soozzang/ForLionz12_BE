package likelion.site.controller;

import likelion.site.domain.Member;
import likelion.site.dto.MemberRequestDto;
import likelion.site.dto.MemberResponseDto;
import likelion.site.service.MemberService;
import likelion.site.util.SecurityUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMyInfo() {
        return ResponseEntity.ok(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @GetMapping
    public ResponseEntity<MemberResponseDto> findMemberInfoById(@RequestParam Long id) {
        return ResponseEntity.ok(memberService.findMemberInfoById(id));
    }

    @PutMapping("/comment")
    public ResponseEntity<MemberResponse> updateMemberIntroduction(@RequestBody MemberIntroductionUpdateRequest request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updateIntroduction(member.getId(), request.getIntroduction());
        return ResponseEntity.ok().body(new MemberResponse(member));
    }

    @PutMapping("/github")
    public ResponseEntity<MemberResponse> updateMemberGithubAddress(@RequestBody MemberGithubAddressUpdateRequest request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updateGithubAddress(member.getId(), request.getGithubAddress());
        return ResponseEntity.ok().body(new MemberResponse(member));
    }

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
