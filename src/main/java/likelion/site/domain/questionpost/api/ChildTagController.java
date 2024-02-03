package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.questionpost.service.ChildTagService;
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

@Tag(name = "ChildTag", description = "자식태그")
@RestController
@RequiredArgsConstructor
@RequestMapping("/childTag")
public class ChildTagController {

    private final ChildTagService childTagService;
    private final ParentTagService parentTagService;
    private final MemberService memberService;

    @Operation(summary = "자식태그 생성")
    @PostMapping
    public ApiResponse<ChildTagResponseIdDto> createChildTag(@RequestBody ChildTagRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            ParentTag parentTag = parentTagService.findById(request.parentTagId);
            ChildTag childTag = ChildTag.builder()
                    .name(request.name)
                    .parentTag(parentTag)
                    .build();
            Long id = childTagService.addChildTag(childTag);
            parentTag.addChildTag(childTag);
            return ApiResponse.createSuccess(new ChildTagResponseIdDto(childTag));
        }
        return null;
    }

    @Operation(summary = "특정 부모 태그에 대한 자식태그들 조회")
    @GetMapping
    public ApiResponse<List<ChildTagResponseDto>> getChildTags(@RequestParam Long parentTagId) {
        ParentTag parentTag = parentTagService.findById(parentTagId);
        List<ChildTag> childTags = childTagService.findChildTagsByParentTag(parentTag);
        List<ChildTagResponseDto> childTagResponseDtos = new ArrayList<>();

        for (ChildTag childTag : childTags) {
            ChildTagResponseDto dto = new ChildTagResponseDto(childTag);
            childTagResponseDtos.add(dto);
        }

        return ApiResponse.createSuccess(childTagResponseDtos);
    }


    @Data
    public static class ChildTagResponseIdDto {
        Long id;

        public ChildTagResponseIdDto(ChildTag childTag) {
            this.id = childTag.getId();
        }
    }

    @Data
    public static class ChildTagResponseDto {
        Long id;
        String name;

        public ChildTagResponseDto(ChildTag childTag) {
            this.id = childTag.getId();
            this.name = childTag.getName();
        }
    }

    @Data
    public static class ChildTagRequestDto {
        String name;
        Long parentTagId;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
