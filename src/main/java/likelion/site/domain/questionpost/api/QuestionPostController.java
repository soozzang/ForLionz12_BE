package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.service.QuestionPostService;
import likelion.site.global.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "QuestionPost", description = "질문글(Q&A)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionPostController {

    private final QuestionPostService questionPostService;
    private final MemberService memberService;

    @Operation(summary = "질문글 생성", description = "STAFF는 불가능합니다.")
    @PostMapping
    public ResponseEntity<QuestionPostIdResponseDto> createQuestionPost(@RequestBody QuestionPostRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        QuestionPost questionPost = QuestionPost.builder()
                .member(member)
                .title(request.title)
                .content(request.content)
                .build();
        Long id = questionPostService.addQuestionPost(questionPost);
        return ResponseEntity.ok().body(new QuestionPostIdResponseDto(id));
    }

    @Operation(summary = "모든 질문글 조회")
    @GetMapping("/all")
    public ResponseEntity<Result> findAllQuestionPosts() {
        List<QuestionPost> questionPosts = questionPostService.findAllQuestionPosts();
        List<QuestionPostResponseDto> collect = questionPosts.stream()
                .map(QuestionPostResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @Operation(summary = "id를 통해 질문글 상세 조회")
    @GetMapping
    public ResponseEntity<QuestionPostResponseDto> getQuestionPostDetail(@RequestParam Long id) {
        QuestionPost questionPost = questionPostService.findQuestionPostById(id);
        return ResponseEntity.ok().body(new QuestionPostResponseDto(questionPost));
    }

    @Operation(summary = "특정 id의 질문글 수정", description = "접속 중인 사용자 본인의 글만 수정할 수 있습니다.")
    @PutMapping("{id}")
    public ResponseEntity<QuestionPostIdResponseDto> updateQuestionPost(@PathVariable("id") Long id, @RequestBody QuestionPostRequestDto request) {
        QuestionPost questionPost = questionPostService.findQuestionPostById(id);
        if(questionPost.getMember()==memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get()){
            questionPostService.update(id, request.getTitle(), request.getContent());
            return ResponseEntity.ok().body(new QuestionPostIdResponseDto(id));
        }
        return null;
    }

    @Operation(summary = "특정 id의 질문글 삭제", description = "접속 중인 사용자 본인의 글만 삭제할 수 있습니다.")
    @DeleteMapping("{id}")
    public void deleteQuestionPost(@PathVariable("id") Long id) {
        questionPostService.delete(id);
    }

    @Data
    public static class QuestionPostResponseDto {
        Long id;
        Long memberId;
        String title;
        String content;
        LocalDateTime createdAt;
        List<Comment> comments;

        QuestionPostResponseDto(QuestionPost questionPost) {
            id = questionPost.getId();
            memberId = questionPost.getMember().getId();
            title = questionPost.getTitle();
            content = questionPost.getContent();
            createdAt = questionPost.getCreatedAt();
            comments = questionPost.getComments();
        }
    }


    @Data
    public static class QuestionPostIdResponseDto {
        Long id;

        QuestionPostIdResponseDto(Long id) {
            this.id = id;
        }
    }

    @Data
    public static class QuestionPostRequestDto {
        String title;
        String content;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
