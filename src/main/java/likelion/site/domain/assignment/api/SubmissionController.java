package likelion.site.domain.assignment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.assignment.service.AssignmentService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.assignment.service.SubmissionService;
import likelion.site.global.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SubmissionIdResponseDto> createSubmission(@RequestBody SubmissionRequestDto request) {
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        Assignment assignment = assignmentService.findAssignmentById(request.assignmentId);
        if (member.getPart() == Part.BE || member.getPart() == Part.FE) {
            Submission submission = Submission.builder()
                    .member(member)
                    .assignment(assignment)
                    .description(request.description)
                    .assignmentLink(request.assignmentLink)
                    .build();
            Long id = submissionService.addSubmission(submission);
            return ResponseEntity.ok().body(new SubmissionIdResponseDto(id));
        }
        return null;
    }

    @Operation(summary = "특정id에 해당하는 제출란 업데이트")
    @PutMapping("{id}")
    public ResponseEntity<SubmissionIdResponseDto> updateSubmission(@PathVariable("id") Long id, @RequestBody SubmissionRequestDto request) {
        submissionService.updateSubmission(id, request.getDescription() , request.getAssignmentLink());
        return ResponseEntity.ok().body(new SubmissionIdResponseDto(id));
    }



    @Data
    public static class SubmissionIdResponseDto {
        Long id;

        SubmissionIdResponseDto(Long id) {
            this.id = id;
        }
    }

    @Data
    public static class SubmissionRequestDto {
        Long assignmentId;
        String description;
        String assignmentLink;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
