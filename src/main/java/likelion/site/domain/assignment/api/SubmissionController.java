package likelion.site.domain.assignment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.success.SubmissionSuccess;
import likelion.site.domain.assignment.dto.request.SubmissionRequest;
import likelion.site.domain.assignment.dto.request.SubmissionUpdateRequest;
import likelion.site.domain.assignment.dto.response.SubmissionIdResponse;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.assignment.service.AssignmentService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.assignment.service.SubmissionService;
import likelion.site.global.ApiResponse;
import likelion.site.global.exception.CustomError;
import likelion.site.global.exception.exceptions.OverSubmissionException;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static likelion.site.domain.assignment.domain.success.SubmissionSuccess.SUBMISSION_CREATED_SUCCESS;
import static likelion.site.domain.assignment.domain.success.SubmissionSuccess.SUBMISSION_UPDATED_SUCCESS;

@Tag(name = "Submission", description = "과제 제출")
@RestController
@RequiredArgsConstructor
@RequestMapping("/submission")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final MemberService memberService;
    private final AssignmentService assignmentService;

    @Operation(summary = "과제 제출하기")
    @PostMapping
    public ApiResponse<?> createSubmission(@RequestBody SubmissionRequest request) {
        return ApiResponse.createSuccess(SUBMISSION_CREATED_SUCCESS, submissionService.addSubmission(SecurityUtil.getCurrentMemberId(), request));
    }

    @Operation(summary = "특정id에 해당하는 제출란 업데이트")
    @PutMapping("{id}")
    public ApiResponse<SubmissionIdResponse> updateSubmission(@PathVariable("id") Long id, @RequestBody SubmissionUpdateRequest request) {
        return ApiResponse.createSuccess(SUBMISSION_UPDATED_SUCCESS,submissionService.updateSubmission(id,SecurityUtil.getCurrentMemberId(),request));
    }
}
