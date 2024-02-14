package likelion.site.domain.questionpost.api.tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.dto.request.ParentTagRequestDto;
import likelion.site.domain.questionpost.dto.response.tag.ParentTagResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.ParentTagResponseIdDto;
import likelion.site.domain.questionpost.dto.response.tag.SearchByParentTagResponse;
import likelion.site.domain.questionpost.service.tag.ChildTagService;
import likelion.site.domain.questionpost.service.tag.ParentTagService;
import likelion.site.domain.questionpost.service.tag.QuestionTagMapService;
import likelion.site.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<ParentTagResponseIdDto>> createParentTag(@RequestBody ParentTagRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(TAG_CREATED_SUCCESS,parentTagService.addParentTag(request)));
    }

    @Operation(summary = "특정 부모 태그에 대한 자식태그들&게시글 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<SearchByParentTagResponse>> getChildTags(@RequestParam Long parentTagId) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_TAG_SUCCESS,parentTagService.findChildsAndPosts(parentTagId)));
    }

    @Operation(summary = "모든 부모태그 조회")
    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<ParentTagResponseDto>>> getAllParentTags() {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_TAG_SUCCESS,parentTagService.findAll()));
    }
}
