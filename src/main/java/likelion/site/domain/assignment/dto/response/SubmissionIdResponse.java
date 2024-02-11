package likelion.site.domain.assignment.dto.response;

import likelion.site.domain.assignment.domain.Submission;
import lombok.Getter;

@Getter
public class SubmissionIdResponse {
    Long id;

    public SubmissionIdResponse(Submission submission) {
        this.id = submission.getId();
    }

}
