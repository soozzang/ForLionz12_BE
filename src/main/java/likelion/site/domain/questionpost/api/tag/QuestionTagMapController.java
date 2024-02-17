package likelion.site.domain.questionpost.api.tag;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.questionpost.dto.request.QuestionTagMapRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import likelion.site.domain.questionpost.service.post.QuestionPostService;
import likelion.site.domain.questionpost.service.tag.ChildTagService;
import likelion.site.domain.questionpost.service.tag.QuestionTagMapService;
import likelion.site.global.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static likelion.site.domain.questionpost.domain.success.MapSuccess.GET_MAP_SUCCESS;

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
    public ResponseEntity<ApiResponse<?>> createQuestionTagMap(@RequestBody QuestionTagMapRequestDto request) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @Operation(summary = "특정 자식태그id(들)에 해당하는 자식태그와 매핑된 질문글 리스트 조회", description = "1,2,3 형태의 쿼리 파라미터 형태로 요청하세요.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionPostResponseDto>>> getQuestionTagMap(@RequestParam List<Long> childTagIds) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_MAP_SUCCESS,questionTagMapService.findByChildTag(childTagIds)));
    }
}
