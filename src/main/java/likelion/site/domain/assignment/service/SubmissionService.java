package likelion.site.domain.assignment.service;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.assignment.repository.SubmissionRepository;
import likelion.site.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    @Transactional
    public Long addSubmission(Submission submission) {
        submissionRepository.save(submission);
        return submission.getId();
    }

    @Transactional
    public void updateSubmission(Long id, String description, String assignmentLink) {
        Submission submission = submissionRepository.findById(id).get();
        submission.updateSubmission(description,assignmentLink);
    }

    public Submission findByAssignmentAndMember(Assignment assignment, Member member) {
        try {
            System.out.println("hi");
            return submissionRepository.findSubmissionByAssignmentAndMember(assignment, member);
        } catch (Exception e) {
            throw new IllegalArgumentException("해당 과제에 제출물이 없습니다.");
        }
    }
}
