package likelion.site.domain.questionpost.api.tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.dto.request.ChildTagRequestDto;
import likelion.site.domain.questionpost.dto.response.tag.ChildTagResponseIdDto;
import likelion.site.domain.questionpost.service.tag.ChildTagService;
import likelion.site.domain.questionpost.service.tag.ParentTagService;
import likelion.site.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static likelion.site.domain.questionpost.domain.success.CommentSuccess.COMMENT_CREATED_SUCCESS;

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
    public ResponseEntity<ApiResponse<ChildTagResponseIdDto>> createChildTag(@RequestBody ChildTagRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(COMMENT_CREATED_SUCCESS, childTagService.addChildTag(request)));
    }
}