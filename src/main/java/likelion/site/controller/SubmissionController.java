package likelion.site.controller;

import likelion.site.domain.Assignment;
import likelion.site.domain.Member;
import likelion.site.domain.Part;
import likelion.site.domain.Submission;
import likelion.site.repository.SubmissionRepository;
import likelion.site.service.AssignmentService;
import likelion.site.service.MemberService;
import likelion.site.service.SubmissionService;
import likelion.site.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/submission")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final MemberService memberService;
    private final AssignmentService assignmentService;

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

    @PutMapping
    public ResponseEntity<SubmissionIdResponseDto> updateSubmission(@RequestBody SubmissionRequestDto request) {
        submissionService.updateSubmission(request.id, request.getDescription() , request.getAssignmentLink());
        return ResponseEntity.ok().body(new SubmissionIdResponseDto(request.id));
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
        Long id;
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
