package likelion.site.domain.assignment.dto.response;

import likelion.site.domain.assignment.domain.Assignment;
import lombok.Getter;

@Getter
public class AssignmentIdResponse {

    Long id;

    public AssignmentIdResponse(Assignment assignment) {
        this.id = assignment.getId();
    }
}
