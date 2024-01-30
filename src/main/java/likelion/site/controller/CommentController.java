package likelion.site.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.ChildComment;
import likelion.site.domain.Comment;
import likelion.site.domain.Member;
import likelion.site.domain.QuestionPost;
import likelion.site.service.CommentService;
import likelion.site.service.MemberService;
import likelion.site.service.QuestionPostService;
import likelion.site.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comment", description = "댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;
    private final QuestionPostService questionPostService;

    @Operation(summary = "댓글 생성")
    @PostMapping
    public ResponseEntity<CommentResponseIdDto> createComment(@RequestBody CommentRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        QuestionPost questionPost = questionPostService.findQuestionPostById(request.questionPostId);
        Comment comment = Comment.builder()
                .member(member)
                .questionPost(questionPost)
                .content(request.content)
                .build();
        Long id = commentService.addComment(comment);
        return ResponseEntity.ok().body(new CommentResponseIdDto(comment));
    }

    @Operation(summary = "특정 질문글에 대한 모든 댓글 조회")
    @GetMapping
    public ResponseEntity<Result> getAllComments(@RequestParam Long questionPostId) {
        QuestionPost questionPost = questionPostService.findQuestionPostById(questionPostId);
        List<Comment> comments = commentService.findCommentsByQuestionPost(questionPost);
        List<CommentResponseDto> collect = comments.stream()
                .map(CommentResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @Data
    public static class CommentResponseDto {
        Long id;
        Long memberId;
        String content;

        public CommentResponseDto(Comment comment) {
            this.id = comment.getId();
            this.memberId = comment.getMember().getId();
            this.content = comment.getContent();
        }
    }

    @Data
    public static class CommentResponseIdDto {
        Long id;

        public CommentResponseIdDto(Comment comment) {
            this.id = comment.getId();
        }
    }

    @Data
    public static class CommentRequestDto {
        String content;
        Long questionPostId;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

}
