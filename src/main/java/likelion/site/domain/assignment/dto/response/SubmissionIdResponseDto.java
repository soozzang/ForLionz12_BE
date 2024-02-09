package likelion.site.domain.assignment.dto.response;

import lombok.Getter;

@Getter
public class SubmissionIdResponseDto {
    Long id;

    public SubmissionIdResponseDto(Long id) {
        this.id = id;
    }

}
