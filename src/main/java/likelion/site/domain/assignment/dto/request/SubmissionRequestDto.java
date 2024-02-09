package likelion.site.domain.assignment.dto.request;

import lombok.Getter;

@Getter
public class SubmissionRequestDto {

    Long assignmentId;
    String description;
    String assignmentLink;
}
