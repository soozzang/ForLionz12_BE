package likelion.site.domain.assignment.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.assignment.dto.request.AssignmentRequest;
import likelion.site.domain.assignment.dto.response.AssignmentIdResponse;
import likelion.site.domain.assignment.dto.response.AssignmentResponse;
import likelion.site.domain.assignment.dto.response.SubmissionResponse;
import likelion.site.domain.assignment.service.AssignmentService;
import likelion.site.domain.assignment.service.SubmissionService;
import likelion.site.domain.member.service.MemberService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static likelion.site.domain.assignment.domain.success.AssignmentSuccess.*;

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
    public ResponseEntity<ApiResponse<AssignmentIdResponse>> createAssignment(@RequestBody AssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(ASSIGNMENT_CREATED_SUCCESS,assignmentService.addAssignment(SecurityUtil.getCurrentMemberId(), request)));
    }

    @Operation(summary = "과제물 수정")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<AssignmentIdResponse>> updateAssignment(@PathVariable("id") Long id, @RequestBody AssignmentRequest request) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(ASSIGNMENT_UPDATED_SUCCESS,assignmentService.updateAssignment(id,request)));
    }

    @Operation(summary = "파트별 과제공지 조회" , description = "part에는 BE/FE/ALL이 들어갈 수 있습니다.")
    @GetMapping("/part/{part}")
    public ResponseEntity<ApiResponse<List<AssignmentResponse>>> findAssignmentByPart(@PathVariable("part") String part) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_ASSIGNMENT_SUCCESS,assignmentService.findAssignmentByPart(part)));
    }

    @Operation(summary = "모든 과제 공지 조회")
    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<AssignmentResponse>>> findAllAssignment() {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_ASSIGNMENT_SUCCESS, assignmentService.findAllAssignments()));
    }

    @Operation(summary = "id로 과제공지 상세 조회")
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<AssignmentResponse>> getAssignmentDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_ASSIGNMENT_SUCCESS, assignmentService.findAssignmentById(id)));
    }

    @Operation(summary = "특정 과제의 id로 모든 과제 제출물 조회")
    @GetMapping("{id}/submissions")
    public ResponseEntity<ApiResponse<List<SubmissionResponse>>> getSubmissions(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_ASSIGNMENT_SUCCESS, assignmentService.findSubmissionsByAssignment(id)));
    }

    @Operation(summary = "특정 과제의 id로 현재 접속한 사용자의 제출물을 확인", description = "제출물이 없다면, 제출물이 없다는 메시지를 응답합니다")
    @GetMapping("{id}/mysubmission")
    public ResponseEntity<ApiResponse<?>> getMySubmission(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_ASSIGNMENT_SUCCESS,assignmentService.findByAssignmentAndMember(id, SecurityUtil.getCurrentMemberId())));
    }
}
