package likelion.site.domain.questionpost.api.comment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.questionpost.dto.request.ChildCommentRequestDto;
import likelion.site.domain.questionpost.dto.request.UpdateChildCommentRequest;
import likelion.site.domain.questionpost.dto.response.comment.ChildCommentIdResponseDto;
import likelion.site.domain.questionpost.dto.response.comment.ChildCommentResponseDto;
import likelion.site.domain.questionpost.service.comment.ChildCommentService;
import likelion.site.domain.questionpost.service.comment.CommentService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static likelion.site.domain.questionpost.domain.success.CommentSuccess.COMMENT_CREATED_SUCCESS;
import static likelion.site.domain.questionpost.domain.success.CommentSuccess.GET_COMMENT_SUCCESS;

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
    public ResponseEntity<ApiResponse<ChildCommentIdResponseDto>> createChildComment(@RequestBody ChildCommentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(COMMENT_CREATED_SUCCESS,childCommentService.addChildComment(SecurityUtil.getCurrentMemberId(),request)));
    }

    @Operation(summary = "대댓글 수정" , description = "commentId 는 부모 댓글의 id입니다.")
    @PutMapping
    public ResponseEntity<ApiResponse<ChildCommentIdResponseDto>> updateChildComment(@RequestBody UpdateChildCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(COMMENT_CREATED_SUCCESS,childCommentService.updateChildComment(SecurityUtil.getCurrentMemberId(),request)));
    }

    @Operation(summary = "특정 댓글의 모든 대댓글 조회")
    @GetMapping("{commentId}")
    public ResponseEntity<ApiResponse<List<ChildCommentResponseDto>>> getAllChildComments(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_COMMENT_SUCCESS, childCommentService.findChildCommentsByComment(commentId)));
    }
}
