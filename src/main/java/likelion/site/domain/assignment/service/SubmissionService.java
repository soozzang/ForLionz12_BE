package likelion.site.domain.assignment.service;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.assignment.dto.request.SubmissionRequest;
import likelion.site.domain.assignment.dto.request.SubmissionUpdateRequest;
import likelion.site.domain.assignment.dto.response.SubmissionIdResponse;
import likelion.site.domain.assignment.repository.AssignmentRepository;
import likelion.site.domain.assignment.repository.SubmissionRepository;
import likelion.site.domain.member.domain.Member;
import likelion.site.domain.member.repository.MemberRepository;
import likelion.site.global.exception.CustomError;
import likelion.site.global.exception.exceptions.BadElementException;
import likelion.site.global.exception.exceptions.OverSubmissionException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SubmissionIdResponse addSubmission(Long id,SubmissionRequest request) {
        Optional<Assignment> assignment = assignmentRepository.findById(request.getAssignmentId());
        Member member = memberRepository.findById(id).get();
        if(assignment.isEmpty()) {
            throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
        };
        if(submissionRepository.findSubmissionByAssignmentAndMember(assignment.get(),member).isPresent()){
            throw new OverSubmissionException(CustomError.OVER_SUBMISSION_EXCEPTION);
        }
        Submission submission = request.toEntity(assignment.get(),member);
        submissionRepository.save(submission);
        return new SubmissionIdResponse(submission);
    }

    @Transactional
    public SubmissionIdResponse updateSubmission(Long assignmentId, Long memberId, SubmissionUpdateRequest request) {
        Optional<Assignment> assignment = assignmentRepository.findById(assignmentId);
        Member member = memberRepository.findById(memberId).get();
        if(assignment.isEmpty()) {
            throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
        };

        Optional<Submission> submission = submissionRepository.findSubmissionByAssignmentAndMember(assignment.get() ,member);
        if (submission.isEmpty()) {
            throw new BadElementException(CustomError.BAD_ELEMENT_ERROR);
        }

        submission.get().updateSubmission(request.getDescription(),request.getAssignmentLink());
        submissionRepository.save(submission.get());
        return new SubmissionIdResponse(submission.get());
    }
}
