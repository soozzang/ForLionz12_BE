package likelion.site.domain.member.api;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.dto.request.GithubAddressUpdateRequestDto;
import likelion.site.domain.member.dto.request.InstagramIdUpdateRequestDto;
import likelion.site.domain.member.dto.request.IntroductionUpdateRequestDto;
import likelion.site.domain.member.dto.request.PasswordUpdateRequestDto;
import likelion.site.domain.member.dto.response.MemberIdResponseDto;
import likelion.site.domain.member.dto.response.MemberResponseDto;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import likelion.site.global.exception.exceptions.BadFileFormatException;
import likelion.site.global.exception.CustomError;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Member", description = "사용자")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Operation(summary = "내 정보 조회", description = "해당 토큰의 사용자의 정보를 조회합니다.")
    @GetMapping("/me")
    public ApiResponse<MemberResponseDto> findMyInfo() {
        return ApiResponse.createSuccess(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()));
    }

    @Operation(summary = "id를 통해 특정 멤버를 조회")
    @GetMapping
    public ApiResponse<MemberResponseDto> findMemberInfoById(@RequestParam Long id) {
        return ApiResponse.createSuccess(memberService.findMemberInfoById(id));
    }

    @Operation(summary = "나의 한 줄 소개 업데이트", description = "해당 토큰의 사용자의 한 줄 소개를 업데이트 합니다.")
    @PutMapping("/comment")
    public ApiResponse<MemberIdResponseDto> updateMemberIntroduction(@RequestBody IntroductionUpdateRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updateIntroduction(member.getId(), request.getIntroduction());
        return ApiResponse.createSuccess(new MemberIdResponseDto(member));
    }

    @Operation(summary = "나의 깃허브 주소 업데이트", description = "해당 토큰의 사용자의 깃허브 주소를 업데이트 합니다.")
    @PutMapping("/github")
    public ApiResponse<MemberIdResponseDto> updateMemberGithubAddress(@RequestBody GithubAddressUpdateRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updateGithubAddress(member.getId(), request.getGithubAddress());
        return ApiResponse.createSuccess(new MemberIdResponseDto(member));
    }

    @Operation(summary = "비밀번호 변경", description = "해당 토큰의 사용자의 비밀번호를 업데이트 합니다.")
    @PutMapping("/password")
    public ApiResponse<MemberIdResponseDto> updateMemberPassword(@RequestBody PasswordUpdateRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updatePassword(member.getId(), request.getPassword());
        return ApiResponse.createSuccess(new MemberIdResponseDto(member));
    }

    @Operation(summary = "프로필 사진 업로드", description = "해당 토큰의 사용자의 프로필 이미자를 업로드 합니다.")
    @PostMapping(value = "/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
            String fileName = file.getOriginalFilename() + "." + member.getName();
//            LocalDateTime date = LocalDateTime.now();
            String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + fileName;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
            memberService.updateImageUrl(SecurityUtil.getCurrentMemberId(), fileUrl);
            return ApiResponse.createSuccess(fileUrl);
        } catch (IOException e) {
            throw new BadFileFormatException(CustomError.BAD_FILE_EXCEPTION);
        }
    }

    @Operation(summary = "나의 인스타그램 아이디 업데이트", description = "해당 토큰의 사용자의 인스타그램 아이디를 업데이트 합니다.")
    @PutMapping("/instagram")
    public ApiResponse<MemberIdResponseDto> updateMemberInstagramId(@RequestBody InstagramIdUpdateRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        memberService.updateInstagramId(member.getId(), request.getInstagramId());
        return ApiResponse.createSuccess(new MemberIdResponseDto(member));
    }

    @Operation(summary = "모든 멤버 조회")
    @GetMapping("all")
    public ApiResponse<List<MemberResponseDto>> findAllMember() {
        List<Member> memberList = memberService.findAll();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();

        for (Member member : memberList) {
            MemberResponseDto dto = new MemberResponseDto(member);
            memberResponseDtos.add(dto);
        }
        return ApiResponse.createSuccess(memberResponseDtos);
    }
}
