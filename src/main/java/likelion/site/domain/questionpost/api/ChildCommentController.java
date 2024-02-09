package likelion.site.domain.questionpost.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.questionpost.domain.ChildComment;
import likelion.site.domain.questionpost.domain.Comment;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.questionpost.dto.request.ChildCommentRequestDto;
import likelion.site.domain.questionpost.dto.response.comment.ChildCommentIdResponseDto;
import likelion.site.domain.questionpost.dto.response.comment.ChildCommentResponseDto;
import likelion.site.domain.questionpost.service.ChildCommentService;
import likelion.site.domain.questionpost.service.CommentService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ApiResponse<ChildCommentIdResponseDto> createChildComment(@RequestBody ChildCommentRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        Comment comment = commentService.findById(request.getCommentId());
        ChildComment childcomment = ChildComment.builder()
                .member(member)
                .comment(comment)
                .content(request.getContent())
                .build();
        Long id = childCommentService.addChildComment(childcomment);
        commentService.addChildComment(comment, childcomment);
        return ApiResponse.createSuccess(new ChildCommentIdResponseDto(childcomment));
    }

    @Operation(summary = "특정 댓글의 모든 대댓글 조회")
    @GetMapping("{commentId}")
    public ApiResponse<List<ChildCommentResponseDto>> getAllChildComments(@PathVariable Long commentId) {
        Comment comment = commentService.findById(commentId);
        List<ChildComment> childComments = childCommentService.findChildCommentsByComment(comment);
        List<ChildCommentResponseDto> childCommentResponseDtos = new ArrayList<>();

        for (ChildComment childComment : childComments) {
            ChildCommentResponseDto dto = new ChildCommentResponseDto(childComment);
            childCommentResponseDtos.add(dto);
        }
        return ApiResponse.createSuccess(childCommentResponseDtos);
    }
}
