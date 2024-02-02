package likelion.site.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.ChildComment;
import likelion.site.domain.Comment;
import likelion.site.domain.Member;
import likelion.site.service.ChildCommentService;
import likelion.site.service.CommentService;
import likelion.site.service.MemberService;
import likelion.site.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ChildComment", description = "대댓글")
@RestController
@RequiredArgsConstructor
@RequestMapping("/childcomment")
public class ChildCommentController {

    private final MemberService memberService;
    private final ChildCommentService childCommentService;
    private final CommentService commentService;

    @Operation(summary = "대댓글 작성" , description = "commentId 는 부모 댓글의 id입니다.")
    @PostMapping
    public ResponseEntity<ChildCommentResponseIdDto> createChildComment(@RequestBody ChildCommentRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        Comment comment = commentService.findById(request.commentId);
        ChildComment childcomment = ChildComment.builder()
                .member(member)
                .comment(comment)
                .content(request.content)
                .build();
        Long id = childCommentService.addChildComment(childcomment);
        commentService.addChildComment(comment, childcomment);
        return ResponseEntity.ok().body(new ChildCommentResponseIdDto(childcomment));
    }

    @Operation(summary = "특정 댓글의 모든 대댓글 조회")
    @GetMapping("{commentId}")
    public ResponseEntity<Result> getAllChildComments(@PathVariable Long commentId) {
        Comment comment = commentService.findById(commentId);
        List<ChildComment> childComments = childCommentService.findChildCommentsByComment(comment);
        System.out.println("hello");
        List<ChildCommentResponseDto> collect = childComments.stream()
                .map(ChildCommentResponseDto::new)
                .toList();
        System.out.println("hi");
        return ResponseEntity.ok().body(new Result(collect));
    }

    @Data
    public static class ChildCommentResponseDto {
        Long id;
        Long memberId;
        String content;

        public ChildCommentResponseDto(ChildComment childComment) {
            this.id = childComment.getId();
            this.memberId = childComment.getMember().getId();
            this.content = childComment.getContent();
        }
    }

    @Data
    public static class ChildCommentResponseIdDto {
        Long id;

        public ChildCommentResponseIdDto(ChildComment childComment) {
            this.id = childComment.getId();
        }
    }

    @Data
    public static class ChildCommentRequestDto {
        String content;
        Long commentId;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
