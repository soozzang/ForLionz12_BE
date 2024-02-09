package likelion.site.domain.assignment.dto.response;

import likelion.site.domain.assignment.domain.Submission;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SubmissionResponseDto {

    Long id;
    Long memberId;
    String memberName;
    Long assignmentId;
    LocalDateTime createdAt;
    String description;
    String assignmentLink;

    public SubmissionResponseDto(Submission submission) {
        id = submission.getId();
        memberId = submission.getMember().getId();
        memberName = submission.getMember().getName();
        assignmentId = submission.getAssignment().getId();
        createdAt = submission.getCreatedAt();
        description = submission.getDescription();
        assignmentLink = submission.getAssignmentLink();
    }
}
