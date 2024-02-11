package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import likelion.site.domain.questionpost.domain.success.TagSuccess;
import likelion.site.domain.questionpost.dto.request.ParentTagRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.*;
import likelion.site.domain.questionpost.service.ChildTagService;
import likelion.site.domain.questionpost.service.ParentTagService;
import likelion.site.domain.questionpost.service.QuestionTagMapService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static likelion.site.domain.questionpost.domain.success.TagSuccess.GET_TAG_SUCCESS;
import static likelion.site.domain.questionpost.domain.success.TagSuccess.TAG_CREATED_SUCCESS;

@Tag(name = "ParentTag", description = "부모태그")
@RestController
@RequiredArgsConstructor
@RequestMapping("/parentTag")
public class ParentTagController {

    private final ParentTagService parentTagService;
    private final ChildTagService childTagService;
    private final MemberService memberService;
    private final QuestionTagMapService questionTagMapService;

    @Operation(summary = "부모태그 생성", description = "STAFF만 가능합니다.")
    @PostMapping
    public ApiResponse<ParentTagResponseIdDto> createParentTag(@RequestBody ParentTagRequestDto request) {
        return ApiResponse.createSuccess(TAG_CREATED_SUCCESS,parentTagService.addParentTag(request));
    }

    @Operation(summary = "특정 부모 태그에 대한 자식태그들&게시글 조회")
    @GetMapping
    public ApiResponse<SearchByParentTagResponse> getChildTags(@RequestParam Long parentTagId) {
        return ApiResponse.createSuccess(GET_TAG_SUCCESS,parentTagService.findChildsAndPosts(parentTagId));
    }

    @Operation(summary = "모든 부모태그 조회")
    @GetMapping("all")
    public ApiResponse<List<ParentTagResponseDto>> getAllParentTags() {
        return ApiResponse.createSuccess(GET_TAG_SUCCESS,parentTagService.findAll());
    }
}
