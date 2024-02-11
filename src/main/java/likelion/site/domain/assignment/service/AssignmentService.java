package likelion.site.domain.assignment.service;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.AssignmentMainContent;
import likelion.site.domain.assignment.domain.AssignmentPart;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.assignment.dto.request.AssignmentRequest;
import likelion.site.domain.assignment.dto.response.AssignmentIdResponse;
import likelion.site.domain.assignment.dto.response.AssignmentResponse;
import likelion.site.domain.assignment.dto.response.SubmissionResponse;
import likelion.site.domain.assignment.repository.AssignmentRepository;
import likelion.site.domain.assignment.repository.SubmissionRepository;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.member.repository.MemberRepository;
import likelion.site.global.exception.CustomError;
import likelion.site.global.exception.exceptions.AuthorizationException;
import likelion.site.global.exception.exceptions.BadElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;
    private final SubmissionRepository submissionRepository;

    @Transactional
    public AssignmentIdResponse addAssignment(Long id, AssignmentRequest request){
        Member member = memberRepository.findById(id).get();
        Assignment assignment = request.toEntity();
        assignmentRepository.save(assignment);
        return new AssignmentIdResponse(assignment);
    }

    public List<AssignmentResponse> findAllAssignments() {
        List<Assignment> assignments = assignmentRepository.findAll();
        List<AssignmentResponse> assignmentResponses = new ArrayList<>();

        for (Assignment assignment : assignments) {
            AssignmentResponse dto = new AssignmentResponse(assignment);
            assignmentResponses.add(dto);
        }
        return assignmentResponses;
    }

    public AssignmentResponse findAssignmentById(Long assignmentId) {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        if(assignment.isPresent()){
            return new AssignmentResponse(assignment.get());
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    public List<SubmissionResponse> findSubmissionsByAssignment(Long assignmentId) {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        if(assignment.isPresent()){
            List<Submission> submissions = assignment.get().getSubmissions();
            List<SubmissionResponse> submissionResponses = new ArrayList<>();

            for (Submission submission : submissions) {
                SubmissionResponse dto = new SubmissionResponse(submission);
                submissionResponses.add(dto);
            }
            return submissionResponses;
        }
        throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
    }

    public List<AssignmentResponse> findAssignmentByPart(String part) {
        AssignmentPart assignmentPart = AssignmentPart.findByName(part);
        List<Assignment> assignments = assignmentRepository.findByAssignmentPart(assignmentPart);
        List<AssignmentResponse> assignmentResponseDtos = new ArrayList<>();

        for (Assignment assignment : assignments) {
            AssignmentResponse dto = new AssignmentResponse(assignment);
            assignmentResponseDtos.add(dto);
        }
        return assignmentResponseDtos;
    }


    @Transactional
    public AssignmentIdResponse updateAssignment(Long id, AssignmentRequest request) {
        Assignment assignment = assignmentRepository.findById(id).get();
        AssignmentPart assignmentPart = AssignmentPart.findByName(request.getPart());
        AssignmentMainContent assignmentMainContent = AssignmentMainContent.findByName(request.getCategory());
        assignment.updateAssignment(request.getTitle(), request.getContent(), assignmentMainContent, assignmentPart,request.getExpireAt(), request.getTags());
        assignmentRepository.save(assignment);
        return new AssignmentIdResponse(assignment);
    }

    public SubmissionResponse findByAssignmentAndMember(Long assignmentId, Long memberId) {
        Member member = memberRepository.findById(memberId).get();
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        if (member.getPart() == Part.STAFF) {
            throw new AuthorizationException(CustomError.AUTHORIZATION_EXCEPTION);
        }
        if(submissionRepository.findSubmissionByAssignmentAndMember(assignment, member).isEmpty()) {
            throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
        };
        return new SubmissionResponse(submissionRepository.findSubmissionByAssignmentAndMember(assignment, member).get());

    }

    @Transactional
    public void delete(Long id) {
        assignmentRepository.delete(assignmentRepository.findById(id).get());
    }

//    @Transactional
//    public
}
