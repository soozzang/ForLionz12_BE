package likelion.site.controller;

import likelion.site.domain.*;
import likelion.site.service.MemberService;
import likelion.site.service.QuestionPostService;
import likelion.site.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionPostController {

    private final QuestionPostService questionPostService;
    private final MemberService memberService;

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

    @GetMapping("/all")
    public ResponseEntity<Result> findAllQuestionPosts() {
        List<QuestionPost> questionPosts = questionPostService.findAllQuestionPosts();
        List<QuestionPostResponseDto> collect = questionPosts.stream()
                .map(QuestionPostResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @GetMapping
    public ResponseEntity<QuestionPostResponseDto> getQuestionPostDetail(@RequestParam Long id) {
        QuestionPost questionPost = questionPostService.findQuestionPostById(id);
        return ResponseEntity.ok().body(new QuestionPostResponseDto(questionPost));
    }

    @PutMapping("{id}")
    public ResponseEntity<QuestionPostIdResponseDto> updateQuestionPost(@PathVariable("id") Long id, @RequestBody QuestionPostRequestDto request) {
        questionPostService.update(id, request.getTitle(), request.getContent());
        return ResponseEntity.ok().body(new QuestionPostIdResponseDto(id));
    }

    @DeleteMapping
    public void deleteQuestionPost(@RequestParam Long id) {
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
