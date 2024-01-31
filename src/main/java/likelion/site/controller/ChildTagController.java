package likelion.site.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.ChildTag;
import likelion.site.domain.ParentTag;
import likelion.site.domain.Part;
import likelion.site.service.ChildTagService;
import likelion.site.service.MemberService;
import likelion.site.service.ParentTagService;
import likelion.site.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ChildTagResponseIdDto> createChildTag(@RequestBody ChildTagRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            ParentTag parentTag = parentTagService.findById(request.parentTagId);
            ChildTag childTag = ChildTag.builder()
                    .name(request.name)
                    .parentTag(parentTag)
                    .build();
            Long id = childTagService.addChildTag(childTag);
            parentTag.addChildTag(childTag);
            return ResponseEntity.ok().body(new ChildTagResponseIdDto(childTag));
        }
        return null;
    }

    @Operation(summary = "특정 부모 태그에 대한 자식태그들 조회")
    @GetMapping
    public ResponseEntity<Result> getChildTags(@RequestParam Long parentTagId) {
        ParentTag parentTag = parentTagService.findById(parentTagId);
        List<ChildTag> childTags = childTagService.findChildTagsByParentTag(parentTag);
        List<ChildTagResponseDto> collect = childTags.stream()
                .map(ChildTagResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
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
