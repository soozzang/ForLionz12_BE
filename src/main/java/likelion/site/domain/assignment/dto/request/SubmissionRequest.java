package likelion.site.domain.assignment.dto.request;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.Submission;
import likelion.site.domain.member.domain.Member;
import lombok.Getter;

@Getter
public class SubmissionRequest {

    Long assignmentId;
    String description;
    String assignmentLink;

    public Submission toEntity(Assignment assignment, Member member) {
        return Submission.builder()
                .assignment(assignment)
                .member(member)
                .description(description)
                .assignmentLink(assignmentLink)
                .build();
    }
}
