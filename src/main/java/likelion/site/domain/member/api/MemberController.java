package likelion.site.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.dto.request.GithubAddressUpdateRequest;
import likelion.site.domain.member.dto.request.InstagramIdUpdateRequest;
import likelion.site.domain.member.dto.request.IntroductionUpdateRequest;
import likelion.site.domain.member.dto.request.PasswordUpdateRequest;
import likelion.site.domain.member.dto.response.MemberIdResponseDto;
import likelion.site.domain.member.dto.response.MemberResponseDto;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import likelion.site.global.exception.exceptions.BadFileFormatException;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static likelion.site.domain.member.domain.success.MemberSuccess.GET_MEMBER_SUCCESS;
import static likelion.site.domain.member.domain.success.MemberSuccess.MEMBER_UPDATED_SUCCESS;

@Tag(name = "Member", description = "사용자")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 정보 조회", description = "해당 토큰의 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberResponseDto>> findMyInfo() {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_MEMBER_SUCCESS,memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId())));
    }

    @Operation(summary = "id를 통해 특정 멤버를 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<MemberResponseDto>> findMemberInfoById(@RequestParam Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_MEMBER_SUCCESS,memberService.findMemberInfoById(id)));
    }

    @Operation(summary = "나의 한 줄 소개 업데이트", description = "해당 토큰의 사용자의 한 줄 소개를 업데이트 합니다.")
    @PutMapping("/comment")
    public ResponseEntity<ApiResponse<MemberIdResponseDto>> updateMemberIntroduction(@RequestBody IntroductionUpdateRequest request) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(MEMBER_UPDATED_SUCCESS,memberService.updateIntroduction(SecurityUtil.getCurrentMemberId(),request)));
    }

    @Operation(summary = "나의 깃허브 주소 업데이트", description = "해당 토큰의 사용자의 깃허브 주소를 업데이트 합니다.")
    @PutMapping("/github")
    public ResponseEntity<ApiResponse<MemberIdResponseDto>> updateMemberGithubAddress(@RequestBody GithubAddressUpdateRequest request) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(MEMBER_UPDATED_SUCCESS, memberService.updateGithubAddress(SecurityUtil.getCurrentMemberId(), request)));
    }

    @Operation(summary = "비밀번호 변경", description = "해당 토큰의 사용자의 비밀번호를 업데이트 합니다.")
    @PutMapping("/password")
    public ResponseEntity<ApiResponse<MemberIdResponseDto>> updateMemberPassword(@RequestBody PasswordUpdateRequest request) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(MEMBER_UPDATED_SUCCESS, memberService.updatePassword(SecurityUtil.getCurrentMemberId(), request)));
    }

    @Operation(summary = "프로필 사진 업로드", description = "해당 토큰의 사용자의 프로필 이미자를 업로드 합니다.")
    @PostMapping(value = "/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<?>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(MEMBER_UPDATED_SUCCESS, memberService.updateImageUrl(SecurityUtil.getCurrentMemberId(),file)));
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse<?>> badFileException(BadFileFormatException e) {
        return ResponseEntity.badRequest().body(ApiResponse.createError(e.getCustomError().getHttpStatus() ,e.getMessage()));
    }

    @Operation(summary = "프사 내리기")
    @PutMapping("/imageDelete")
    public ResponseEntity<ApiResponse<MemberIdResponseDto>> deleteProfileImage() {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(MEMBER_UPDATED_SUCCESS,memberService.deleteImage(SecurityUtil.getCurrentMemberId())));
    }

    @Operation(summary = "나의 인스타그램 아이디 업데이트", description = "해당 토큰의 사용자의 인스타그램 아이디를 업데이트 합니다.")
    @PutMapping("/instagram")
    public ResponseEntity<ApiResponse<MemberIdResponseDto>> updateMemberInstagramId(@RequestBody InstagramIdUpdateRequest request) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(MEMBER_UPDATED_SUCCESS,memberService.updateInstagramId(SecurityUtil.getCurrentMemberId(), request)));
    }

    @Operation(summary = "모든 멤버 조회")
    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> findAllMember() {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_MEMBER_SUCCESS,memberService.findAll()));
    }
}
