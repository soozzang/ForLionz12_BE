package likelion.site.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import likelion.site.domain.*;
import likelion.site.service.AssignmentService;
import likelion.site.service.MemberService;
import likelion.site.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final MemberService memberService;

    /**
     *  관리자 전용
     */
    @PostMapping
    public ResponseEntity<AssignmentIdResponseDto> createAssignment(@RequestBody AssignmentRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            Part part = Part.findByName(request.partName);
            Assignment assignment = Assignment.builder()
                    .title(request.title)
                    .part(part)
                    .content(request.content)
                    .expireAt(request.expireAt)
                    .tags(request.tags)
                    .build();
            Long id = assignmentService.addAssignment(assignment);
            return ResponseEntity.ok().body(new AssignmentIdResponseDto(id));
        }
        return null;
    }

    @PutMapping
    public ResponseEntity<AssignmentIdResponseDto> updateAssignment(@RequestBody AssignmentRequestDto request) {
        Part part = Part.findByName(request.partName);
        assignmentService.updateAssignment(request.getId(), request.getTitle(), request.getContent(), part, request.getExpireAt(), request.getTags());
        return ResponseEntity.ok().body(new AssignmentIdResponseDto(request.getId()));
    }

    @DeleteMapping
    public void deleteAssignment(@RequestParam Long id) {
        assignmentService.delete(id);
    }

    /**
     *  for all
     */

    @GetMapping("all")
    public ResponseEntity<Result> findAllAssignments() {
        List<Assignment> assignments = assignmentService.findAllAssignments();
        List<AssignmentResponseDto> collect = assignments.stream()
                .map(AssignmentResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @GetMapping("/part")
    public ResponseEntity<Result> findAssignmentByPart(@RequestParam String partName) {
        Part part = Part.findByName(partName);
        List<Assignment> assignments = assignmentService.findAssignmentByPart(part);
        List<AssignmentResponseDto> collect = assignments.stream()
                .map(AssignmentResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @GetMapping
    public ResponseEntity<AssignmentResponseDto> getAssignmentDetail(@RequestParam Long id) {
        Assignment assignment = assignmentService.findAssignmentById(id);
        return ResponseEntity.ok().body(new AssignmentResponseDto(assignment));
    }

    @GetMapping("submissions")
    public ResponseEntity<Result> getSubmissions(@RequestParam Long id) {
        Assignment assignment = assignmentService.findAssignmentById(id);
        List<Submission> submissions = assignment.getSubmissions();
        List<SubmissionResponseDto> collect = submissions.stream()
                .map(SubmissionResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @Data
    @AllArgsConstructor
    public static class AssignmentResponseDto {
        Long id;
        String title;
        String content;
        Part part;
        LocalDateTime createdAt;
        LocalDateTime expireAt;
        List<SubmissionResponseDto> submissions;
        List<String> tags;
        Integer submissionCount;

        public AssignmentResponseDto(Assignment assignment) {
            this.id = assignment.getId();
            this.title = assignment.getTitle();
            this.content = assignment.getContent();
            this.part = assignment.getPart();
            this.createdAt = assignment.getCreatedAt();
            this.expireAt = assignment.getExpireAt();
            this.submissions = assignment.getSubmissions().stream()
                    .map(SubmissionResponseDto::new)
                    .collect(toList());
            this.tags = assignment.getTags();
            this.submissionCount = assignment.getSubmissions().size();
        }
    }

    @Data
    @AllArgsConstructor
    public static class SubmissionResponseDto {
        Long id;
        Long memberId;
        String memberName;
        Long assignmentId;
        LocalDateTime createdAt;
        String description;
        String assignmentLink;

        SubmissionResponseDto(Submission submission) {
            id = submission.getId();
            memberId = submission.getMember().getId();
            memberName = submission.getMember().getName();
            assignmentId = submission.getAssignment().getId();
            createdAt = submission.getCreatedAt();
            description = submission.getDescription();
            assignmentLink = submission.getAssignmentLink();
        }
    }

    @Data
    @AllArgsConstructor
    public static class AssignmentRequestDto {
        Long id;
        String title;
        String content;
        String partName;
        List<String> tags;

        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime expireAt;
    }

    @Data
    public static class AssignmentIdResponseDto {
        Long id;

        AssignmentIdResponseDto(Long id) {
            this.id = id;
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
