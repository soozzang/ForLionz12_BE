package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.questionpost.domain.success.CommentSuccess;
import likelion.site.domain.questionpost.dto.request.ChildTagRequestDto;
import likelion.site.domain.questionpost.dto.response.tag.ChildTagResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.ChildTagResponseIdDto;
import likelion.site.domain.questionpost.service.ChildTagService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.service.ParentTagService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ApiResponse<ChildTagResponseIdDto> createChildTag(@RequestBody ChildTagRequestDto request) {
        return ApiResponse.createSuccess(COMMENT_CREATED_SUCCESS, childTagService.addChildTag(request));
    }
}