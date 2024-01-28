package likelion.site.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/childTag")
public class ChildTagController {

    private final ChildTagService childTagService;
    private final ParentTagService parentTagService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ChildTagResponseIdDto> createChildTag(@RequestBody ChildTagRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            ChildTag childTag = ChildTag.builder()
                    .name(request.name)
                    .parentTag(request.parentTag)
                    .build();
            Long id = childTagService.addChildTag(childTag);
            request.parentTag.addChildTag(childTag);
            return ResponseEntity.ok().body(new ChildTagResponseIdDto(childTag));
        }
        return null;
    }

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
        ParentTag parentTag;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
