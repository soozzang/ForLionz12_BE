package likelion.site.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Assignment", description = "과제관련 API")
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
    @Operation(summary = "과제 생성" , description = "파트가 STAFF인 멤버만 가능합니다.")
    public ResponseEntity<AssignmentIdResponseDto> createAssignment(@RequestBody AssignmentRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            AssignmentPart assignmentPart = AssignmentPart.findByName(request.partName);
            AssignmentMainContent assignmentMainContent = AssignmentMainContent.findByName(request.assignmentMainContentName);
            Assignment assignment = Assignment.builder()
                    .title(request.title)
                    .assignmentPart(assignmentPart)
                    .assignmentMainContent(assignmentMainContent)
                    .content(request.content)
                    .expireAt(request.expireAt)
                    .tags(request.tags)
                    .build();
            Long id = assignmentService.addAssignment(assignment);
            return ResponseEntity.ok().body(new AssignmentIdResponseDto(id));
        }
        return null;
    }

    @Operation(summary = "과제물 수정")
    @PutMapping("{id}")
    public ResponseEntity<AssignmentIdResponseDto> updateAssignment(@PathVariable("id") Long id,@RequestBody AssignmentRequestDto request) {
        AssignmentPart assignmentPart = AssignmentPart.findByName(request.partName);
        AssignmentMainContent assignmentMainContent = AssignmentMainContent.findByName(request.assignmentMainContentName);
        assignmentService.updateAssignment(id,assignmentMainContent,request.getTitle(), request.getContent(),assignmentPart, request.getExpireAt(), request.getTags());
        return ResponseEntity.ok().body(new AssignmentIdResponseDto(id));
    }

//    @DeleteMapping
//    public void deleteAssignment(@RequestParam Long id) {
//        assignmentService.delete(id);
//    }

    /**
     *  for all
     */

    @Operation(summary = "파트별 과제공지 조회" , description = "partName에는 BE/FE/ALL이 들어갈 수 있습니다.")
    @GetMapping("{part}")
    public ResponseEntity<Result> findAssignmentByPart(@PathVariable("part") String part) {
        AssignmentPart assignmentPart = AssignmentPart.findByName(part);
        List<Assignment> assignments = assignmentService.findAssignmentByPart(assignmentPart);
        List<AssignmentResponseDto> collect = assignments.stream()
                .map(AssignmentResponseDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @Operation(summary = "id로 과제공지 상세 조회")
    @GetMapping("{id}")
    public ResponseEntity<AssignmentResponseDto> getAssignmentDetail(@PathVariable("id") Long id) {
        Assignment assignment = assignmentService.findAssignmentById(id);
        return ResponseEntity.ok().body(new AssignmentResponseDto(assignment));
    }

    @Operation(summary = "특정 과제의 id로 모든 과제 제출물 조회")
    @GetMapping("{id}/submissions")
    public ResponseEntity<Result> getSubmissions(@PathVariable("id") Long id) {
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
        AssignmentMainContent assignmentMainContent;
        String title;
        String content;
        Part part;
        LocalDateTime createdAt;
        LocalDateTime expireAt;
        List<SubmissionResponseDto> submissions;
        List<String> tags;
        Integer submissionCount;
        AssignmentPart assignmentPart;

        public AssignmentResponseDto(Assignment assignment) {
            this.id = assignment.getId();
            this.assignmentMainContent = assignment.getAssignmentMainContent();
            this.title = assignment.getTitle();
            this.content = assignment.getContent();
            this.assignmentPart = assignment.getAssignmentPart();
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
        String assignmentMainContentName;
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
