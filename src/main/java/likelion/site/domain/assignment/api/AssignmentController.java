package likelion.site.domain.assignment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.AssignmentMainContent;
import likelion.site.domain.assignment.domain.AssignmentPart;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.assignment.service.AssignmentService;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Operation(summary = "과제 생성" , description = "파트가 STAFF인 멤버만 가능합니다. + assignmentMainContent에는 HTML , REACT , DJANGO , AWS , JS , CSS , Docker, Git 중 하나가 들어갈 수 있습니다.")
    public ApiResponse<AssignmentIdResponseDto> createAssignment(@RequestBody AssignmentRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            AssignmentPart assignmentPart = AssignmentPart.findByName(request.part);
            AssignmentMainContent assignmentMainContent = AssignmentMainContent.findByName(request.category);
            Assignment assignment = Assignment.builder()
                    .title(request.title)
                    .assignmentPart(assignmentPart)
                    .assignmentMainContent(assignmentMainContent)
                    .content(request.content)
                    .expireAt(request.expireAt)
                    .tags(request.tags)
                    .build();
            Long id = assignmentService.addAssignment(assignment);
            return ApiResponse.createSuccess(new AssignmentIdResponseDto(id));
        }
        return null;
    }

    @Operation(summary = "과제물 수정")
    @PutMapping("{id}")
    public ApiResponse<AssignmentIdResponseDto> updateAssignment(@PathVariable("id") Long id,@RequestBody AssignmentRequestDto request) {
        AssignmentPart assignmentPart = AssignmentPart.findByName(request.part);
        AssignmentMainContent assignmentMainContent = AssignmentMainContent.findByName(request.category);
        assignmentService.updateAssignment(id,assignmentMainContent,request.getTitle(), request.getContent(),assignmentPart, request.getExpireAt(), request.getTags());
        return ApiResponse.createSuccess(new AssignmentIdResponseDto(id));
    }

//    @DeleteMapping
//    public void deleteAssignment(@RequestParam Long id) {
//        assignmentService.delete(id);
//    }

    /**
     *  for all
     */

    @Operation(summary = "파트별 과제공지 조회" , description = "part에는 BE/FE/ALL이 들어갈 수 있습니다.")
    @GetMapping("/part/{part}")
    public ApiResponse<List<AssignmentResponseDto>> findAssignmentByPart(@PathVariable("part") String part) {
        AssignmentPart assignmentPart = AssignmentPart.findByName(part);
        List<Assignment> assignments = assignmentService.findAssignmentByPart(assignmentPart);
        List<AssignmentResponseDto> assignmentResponseDtos = new ArrayList<>();

        for (Assignment assignment : assignments) {
            AssignmentResponseDto dto = new AssignmentResponseDto(assignment);
            assignmentResponseDtos.add(dto);
        }
        return ApiResponse.createSuccess(assignmentResponseDtos);
    }

    @Operation(summary = "모든 과제 공지 조회")
    @GetMapping("all")
    public ApiResponse<List<AssignmentResponseDto>> findAllAssignment() {
        List<Assignment> assignments = assignmentService.findAllAssignments();
        List<AssignmentResponseDto> assignmentResponseDtos = new ArrayList<>();

        for (Assignment assignment : assignments) {
            AssignmentResponseDto dto = new AssignmentResponseDto(assignment);
            assignmentResponseDtos.add(dto);
        }
        return ApiResponse.createSuccess(assignmentResponseDtos);
    }

    @Operation(summary = "id로 과제공지 상세 조회")
    @GetMapping("{id}")
    public ApiResponse<AssignmentResponseDto> getAssignmentDetail(@PathVariable("id") Long id) {
        Assignment assignment = assignmentService.findAssignmentById(id);
        return ApiResponse.createSuccess(new AssignmentResponseDto(assignment));
    }

    @Operation(summary = "특정 과제의 id로 모든 과제 제출물 조회")
    @GetMapping("{id}/submissions")
    public ApiResponse<List<SubmissionResponseDto>> getSubmissions(@PathVariable("id") Long id) {
        Assignment assignment = assignmentService.findAssignmentById(id);
        List<Submission> submissions = assignment.getSubmissions();
        List<SubmissionResponseDto> submissionResponseDtos = new ArrayList<>();

        for (Submission submission : submissions) {
            SubmissionResponseDto dto = new SubmissionResponseDto(submission);
            submissionResponseDtos.add(dto);
        }
        return ApiResponse.createSuccess(submissionResponseDtos);
    }

    @Data
    @AllArgsConstructor
    public static class AssignmentResponseDto {
        Long id;
        AssignmentMainContent category;
        String title;
        String content;
        LocalDateTime createdAt;
        LocalDateTime expireAt;
        List<SubmissionResponseDto> submissions;
        List<String> tags;
        Integer submissionCount;
        AssignmentPart part;

        public AssignmentResponseDto(Assignment assignment) {
            this.id = assignment.getId();
            this.category = assignment.getAssignmentMainContent();
            this.title = assignment.getTitle();
            this.content = assignment.getContent();
            this.part = assignment.getAssignmentPart();
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
        String category;
        String title;
        String content;
        String part;
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
