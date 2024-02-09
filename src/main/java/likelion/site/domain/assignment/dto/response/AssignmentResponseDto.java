package likelion.site.domain.assignment.dto.response;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.AssignmentMainContent;
import likelion.site.domain.assignment.domain.AssignmentPart;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@AllArgsConstructor
public class AssignmentResponseDto {

    Long id;
    AssignmentMainContent category;
    String title;
    String content;
    LocalDateTime createdAt;
    LocalDateTime expireAt;
    List<SubmissionResponseDto> submissions;
    List<String> tags;
    Integer submissionCount;
    AssignmentPart part;

    public AssignmentResponseDto(Assignment assignment) {
        this.id = assignment.getId();
        this.category = assignment.getAssignmentMainContent();
        this.title = assignment.getTitle();
        this.content = assignment.getContent();
        this.part = assignment.getAssignmentPart();
        this.createdAt = assignment.getCreatedAt();
        this.expireAt = assignment.getExpireAt();
        this.submissions = assignment.getSubmissions().stream()
                .map(SubmissionResponseDto::new)
                .collect(toList());
        this.tags = assignment.getTags();
        this.submissionCount = assignment.getSubmissions().size();
    }
}
