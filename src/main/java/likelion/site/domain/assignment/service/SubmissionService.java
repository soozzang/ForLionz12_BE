package likelion.site.domain.assignment.service;

import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.assignment.repository.SubmissionRepository;
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
        Submission submission = submissionRepository.findById(id);
        submission.updateSubmission(description,assignmentLink);
    }
}
