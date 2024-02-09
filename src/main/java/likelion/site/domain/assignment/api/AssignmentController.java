package likelion.site.domain.assignment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.AssignmentMainContent;
import likelion.site.domain.assignment.domain.AssignmentPart;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.assignment.dto.request.AssignmentRequestDto;
import likelion.site.domain.assignment.dto.response.AssignmentIdResponseDto;
import likelion.site.domain.assignment.dto.response.AssignmentResponseDto;
import likelion.site.domain.assignment.dto.response.SubmissionResponseDto;
import likelion.site.domain.assignment.service.AssignmentService;
import likelion.site.domain.assignment.service.SubmissionService;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Assignment", description = "과제관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/assignment")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final MemberService memberService;
    private final SubmissionService submissionService;

    /**
     *  관리자 전용
     */
    @PostMapping
    @Operation(summary = "과제 생성" , description = "파트가 STAFF인 멤버만 가능합니다. + assignmentMainContent에는 HTML , REACT , DJANGO , AWS , JS , CSS , Docker, Git 중 하나가 들어갈 수 있습니다.")
    public ApiResponse<AssignmentIdResponseDto> createAssignment(@RequestBody AssignmentRequestDto request) {
        if (memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            AssignmentPart assignmentPart = AssignmentPart.findByName(request.getPart());
            AssignmentMainContent assignmentMainContent = AssignmentMainContent.findByName(request.getCategory());
            Assignment assignment = Assignment.builder()
                    .title(request.getTitle())
                    .assignmentPart(assignmentPart)
                    .assignmentMainContent(assignmentMainContent)
                    .content(request.getContent())
                    .expireAt(request.getExpireAt())
                    .tags(request.getTags())
                    .build();
            Long id = assignmentService.addAssignment(assignment);
            return ApiResponse.createSuccess(new AssignmentIdResponseDto(id));
        }
        return null;
    }

    @Operation(summary = "과제물 수정")
    @PutMapping("{id}")
    public ApiResponse<AssignmentIdResponseDto> updateAssignment(@PathVariable("id") Long id, @RequestBody AssignmentRequestDto request) {
        AssignmentPart assignmentPart = AssignmentPart.findByName(request.getPart());
        AssignmentMainContent assignmentMainContent = AssignmentMainContent.findByName(request.getCategory());
        assignmentService.updateAssignment(id,assignmentMainContent,request.getTitle(), request.getContent(),assignmentPart, request.getExpireAt(), request.getTags());
        return ApiResponse.createSuccess(new AssignmentIdResponseDto(id));
    }

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

    @Operation(summary = "특정 과제의 id로 현재 접속한 사용자의 제출물을 확인", description = "제출물이 없다면, 제출물이 없다는 메시지를 응답합니다")
    @GetMapping("{id}/mysubmission")
    public ApiResponse<?> getMySubmission(@PathVariable("id") Long id) {
        Assignment assignment = assignmentService.findAssignmentById(id);
        Member member = memberService.findMemberById(SecurityUtil.getCurrentMemberId()).get();
        try{
            return ApiResponse.createSuccess(new SubmissionResponseDto(submissionService.findByAssignmentAndMember(assignment,member)));
        } catch (NullPointerException e){
            return ApiResponse.createSuccessWithNoContent("해당과제에 접속 유저의 제출물이 없습니다.");
        }
    }
}
