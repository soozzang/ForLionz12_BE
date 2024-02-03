package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.service.ParentTagService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "ParentTag", description = "부모태그")
@RestController
@RequiredArgsConstructor
@RequestMapping("/parentTag")
public class ParentTagController {

    private final ParentTagService parentTagService;
    private final MemberService memberService;

    @Operation(summary = "부모태그 생성", description = "STAFF만 가능합니다.")
    @PostMapping
    public ApiResponse<ParentTagResponseIdDto> createParentTag(@RequestBody ParentTagRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            ParentTag parentTag = ParentTag.builder()
                    .name(request.name)
                    .build();
            Long id = parentTagService.addParentTag(parentTag);
            return ApiResponse.createSuccess(new ParentTagResponseIdDto(parentTag));
        }
        return null;
    }

    @Operation(summary = "모든 부모태그 조회")
    @GetMapping
    public ApiResponse<List<ParentTagResponseDto>> getAllParentTags() {
        List<ParentTag> parentTags = parentTagService.findAll();
        List<ParentTagResponseDto> parentTagResponseDtos = new ArrayList<>();

        for (ParentTag parentTag : parentTags) {
            ParentTagResponseDto dto = new ParentTagResponseDto(parentTag);
            parentTagResponseDtos.add(dto);
        }

        return ApiResponse.createSuccess(parentTagResponseDtos);
    }

    @Data
    public static class ParentTagResponseIdDto {
        Long id;

        public ParentTagResponseIdDto(ParentTag parentTag) {
            this.id = parentTag.getId();
        }
    }

    @Data
    public static class ParentTagResponseDto {
        Long id;
        String name;
        List<ChildTag> childTags;

        public ParentTagResponseDto(ParentTag parentTag) {
            this.id = parentTag.getId();
            this.name = parentTag.getName();
        }
    }

    @Data
    public static class ParentTagRequestDto {
        String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
