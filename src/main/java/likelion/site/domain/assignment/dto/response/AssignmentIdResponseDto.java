package likelion.site.domain.assignment.dto.response;

import lombok.Getter;

@Getter
public class AssignmentIdResponseDto {

    Long id;

    public AssignmentIdResponseDto(Long id) {
        this.id = id;
    }
}
