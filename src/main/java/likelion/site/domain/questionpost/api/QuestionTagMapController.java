package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.questionpost.domain.ChildTag;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.domain.QuestionTagMap;
import likelion.site.domain.questionpost.dto.request.QuestionTagMapRequestDto;
import likelion.site.domain.questionpost.dto.response.tag.QuestionTagMapResponseDto;
import likelion.site.domain.questionpost.dto.response.tag.QuestionTagMapResponseIdDto;
import likelion.site.domain.questionpost.service.ChildTagService;
import likelion.site.domain.questionpost.service.QuestionPostService;
import likelion.site.domain.questionpost.service.QuestionTagMapService;
import likelion.site.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "QuestionTagMap", description = "질문글-태그 중계모델, 태그로 조회 + 질문글 생성시 해당 질문 글에 특정 태그들 매핑")
@RestController
@RequiredArgsConstructor
@RequestMapping("/questionTagMap")
public class QuestionTagMapController {

    private final QuestionTagMapService questionTagMapService;
    private final QuestionPostService questionPostService;
    private final ChildTagService childTagService;

    @Operation(summary = "질문 글 생성시 특정 태그들과 매핑")
    @PostMapping
    public ApiResponse<QuestionTagMapResponseIdDto> createQuestionTagMap(@RequestBody QuestionTagMapRequestDto request) {
        QuestionPost questionPost = questionPostService.findQuestionPostById(request.getQuestionPostId());
        ChildTag childTag = childTagService.findById(request.getChildTagId());
        QuestionTagMap questionTagMap = QuestionTagMap.builder()
                .questionPost(questionPost)
                .childTag(childTag)
                .build();
        Long id = questionTagMapService.addQuestionTagMap(questionTagMap);
        return ApiResponse.createSuccess(new QuestionTagMapResponseIdDto(questionTagMap));
    }

    @Operation(summary = "특정 자식태그id에 해당하는 자식태그와 매핑된 질문글 리스트 조회")
    @GetMapping
    public ApiResponse<List<QuestionTagMapResponseDto>> getQuestionTagMap(@RequestParam Long childTagId) {
        ChildTag childTag = childTagService.findById(childTagId);
        List<QuestionTagMap> questionTagMaps = questionTagMapService.findByChildTag(childTag);
        List<QuestionTagMapResponseDto> questionTagMapResponseDtos = new ArrayList<>();

        for (QuestionTagMap questionTagMap : questionTagMaps) {
            QuestionTagMapResponseDto dto = new QuestionTagMapResponseDto(questionTagMap);
            questionTagMapResponseDtos.add(dto);
        }
        return ApiResponse.createSuccess(questionTagMapResponseDtos);
    }

    @Operation(summary = "모든 자식태그-게시글 매핑 데이터 조회")
    @GetMapping("all")
    public ApiResponse<List<QuestionTagMapResponseDto>> getAllQuestionTagMap() {
        List<QuestionTagMapResponseDto> questionTagMapResponseDtos = new ArrayList<>();
        List<QuestionTagMap> questionTagMaps = questionTagMapService.findAllTagMap();
        for (QuestionTagMap questionTagMap : questionTagMaps) {
            QuestionTagMapResponseDto dto = new QuestionTagMapResponseDto(questionTagMap);
            questionTagMapResponseDtos.add(dto);
        }
        return ApiResponse.createSuccess(questionTagMapResponseDtos);
    }
}
