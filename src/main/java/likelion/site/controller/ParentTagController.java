package likelion.site.controller;

import likelion.site.domain.*;
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
@RequestMapping("/parentTag")
public class ParentTagController {

    private final ParentTagService parentTagService;
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ParentTagResponseIdDto> createParentTag(@RequestBody ParentTagRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            ParentTag parentTag = ParentTag.builder()
                    .name(request.name)
                    .build();
            Long id = parentTagService.addParentTag(parentTag);
            return ResponseEntity.ok().body(new ParentTagResponseIdDto(parentTag));
        }
        return null;
    }

    @GetMapping
    public ResponseEntity<Result> getAllParentTags() {
        List<ParentTag> parentTags = parentTagService.findAll();
        List<ParentTagResponseDto> collect = parentTags.stream()
                .map(ParentTagResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
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
