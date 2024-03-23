package likelion.site.domain.questionpost.api.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.dto.request.QuestionPostRequestDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostIdResponseDto;
import likelion.site.domain.questionpost.dto.response.question.QuestionPostResponseDto;
import likelion.site.domain.questionpost.service.post.QuestionPostService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static likelion.site.domain.questionpost.domain.success.QuestionPostSuccess.*;

@Tag(name = "QuestionPost", description = "질문글(Q&A)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionPostController {

    private final QuestionPostService questionPostService;
    private final MemberService memberService;

    @Operation(summary = "질문글 생성", description = "STAFF는 불가능합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<QuestionPostIdResponseDto>> createQuestionPost(@RequestBody QuestionPostRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(QUESTION_POST_CREATED_SUCCESS, questionPostService.addQuestionPost(SecurityUtil.getCurrentMemberId(), request)));
    }

    @Operation(summary = "모든 질문글 조회")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<QuestionPostResponseDto>>> findAllQuestionPosts() {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_QUESTION_POST_SUCCESS, questionPostService.findAllQuestionPosts()));
    }

    @Operation(summary = "id를 통해 질문글 상세 조회")
    @GetMapping
    public ResponseEntity<ApiResponse<QuestionPostResponseDto>> getQuestionPostDetail(@RequestParam Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_QUESTION_POST_SUCCESS, questionPostService.findQuestionPostById(id)));
    }

    @Operation(summary = "특정 id의 질문글 수정", description = "접속 중인 사용자 본인의 글만 수정할 수 있습니다.")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<QuestionPostIdResponseDto>> updateQuestionPost(@PathVariable("id") Long id, @RequestBody QuestionPostRequestDto request) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(QUESTION_POST_UPDATED_SUCCESS, questionPostService.update(id, SecurityUtil.getCurrentMemberId(), request)));
    }

    @Operation(summary = "파일 url 응답")
    @PostMapping(value = "/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(FILE_CONVERT_SUCCESS, questionPostService.convertFile(file)));
    }

    @Operation(summary = "특정 id의 질문글 삭제", description = "접속 중인 사용자 본인의 글만 삭제할 수 있습니다.")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<QuestionPostIdResponseDto>> deleteQuestionPost(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(DELETE_QUESTION_POST_SUCCESS, questionPostService.delete(id)));
    }

    @Operation(summary = "게시글 좋아요 누르기")
    @PostMapping("/like/{id}")
    public ResponseEntity<ApiResponse<QuestionPostIdResponseDto>> likeQuestionPost(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(LIKE_SUCCESS, questionPostService.like(id, SecurityUtil.getCurrentMemberId())));
    }
}
