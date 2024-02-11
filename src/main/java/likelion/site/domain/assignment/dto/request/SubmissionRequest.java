package likelion.site.domain.assignment.dto.request;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.Submission;
import lombok.Getter;

@Getter
public class SubmissionRequest {

    Long assignmentId;
    String description;
    String assignmentLink;

    public Submission toEntity(Assignment assignment) {
        return Submission.builder()
                .assignment(assignment)
                .description(description)
                .assignmentLink(assignmentLink)
                .build();
    }
}
