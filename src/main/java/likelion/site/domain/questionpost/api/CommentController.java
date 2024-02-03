package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.domain.QuestionPost;
import likelion.site.domain.questionpost.service.CommentService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.questionpost.service.QuestionPostService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
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
    public ApiResponse<CommentResponseIdDto> createComment(@RequestBody CommentRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        QuestionPost questionPost = questionPostService.findQuestionPostById(request.questionPostId);
        Comment comment = Comment.builder()
                .member(member)
                .questionPost(questionPost)
                .content(request.content)
                .build();
        Long id = commentService.addComment(comment);
        return ApiResponse.createSuccess(new CommentResponseIdDto(comment));
    }

    @Operation(summary = "특정 질문글에 대한 모든 댓글 조회")
    @GetMapping("questionPostId")
    public ApiResponse<List<CommentResponseDto>> getAllComments(@PathVariable Long questionPostId) {
        QuestionPost questionPost = questionPostService.findQuestionPostById(questionPostId);
        List<Comment> comments = commentService.findCommentsByQuestionPost(questionPost);
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponseDto dto = new CommentResponseDto(comment);
            commentResponseDtos.add(dto);
        }

        return ApiResponse.createSuccess(commentResponseDtos);
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
