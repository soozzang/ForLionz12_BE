package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.ParentTag;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
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
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            ParentTag parentTag = ParentTag.builder()
                    .name(request.getName())
                    .build();
            Long id = parentTagService.addParentTag(parentTag);
            return ApiResponse.createSuccess(new ParentTagResponseIdDto(parentTag));
        }
        return null;
    }

    @Operation(summary = "특정 부모 태그에 대한 자식태그들 조회")
    @GetMapping
    public ApiResponse<SearchByParentTagResponse> getChildTags(@RequestParam Long parentTagId) {
        ParentTag parentTag = parentTagService.findById(parentTagId);
        List<ChildTag> childTags = childTagService.findChildTagsByParentTag(parentTag);
        List<ChildTagResponseDto> childTagResponseDtos = new ArrayList<>();
        List<QuestionPostResponseDto> questionPostResponseDtos = new ArrayList<>();
        HashSet<QuestionPost> questionPosts = new HashSet<>();

        for (ChildTag childTag : childTags) {
            ChildTagResponseDto dto = new ChildTagResponseDto(childTag);
            childTagResponseDtos.add(dto);

            List<QuestionTagMap> questionTagMaps = questionTagMapService.findByChildTag(childTag);
            for (QuestionTagMap questionTagMap : questionTagMaps) {
                QuestionPost questionPost = questionTagMap.getQuestionPost();
                questionPosts.add(questionPost);
            }
        }

        for (QuestionPost questionPost : questionPosts) {
            QuestionPostResponseDto dto = new QuestionPostResponseDto(questionPost);
            questionPostResponseDtos.add(dto);
        }

        return ApiResponse.createSuccess(new SearchByParentTagResponse(childTagResponseDtos,questionPostResponseDtos));
    }

    @Operation(summary = "모든 부모태그 조회")
    @GetMapping("all")
    public ApiResponse<List<ParentTagResponseDto>> getAllParentTags() {
        List<ParentTag> parentTags = parentTagService.findAll();
        List<ParentTagResponseDto> parentTagResponseDtos = new ArrayList<>();

        for (ParentTag parentTag : parentTags) {
            ParentTagResponseDto dto = new ParentTagResponseDto(parentTag);
            parentTagResponseDtos.add(dto);
        }

        return ApiResponse.createSuccess(parentTagResponseDtos);
    }
}
