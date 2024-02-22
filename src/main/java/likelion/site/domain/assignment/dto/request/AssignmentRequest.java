package likelion.site.domain.assignment.dto.request;

import likelion.site.domain.assignment.domain.Assignment;
import likelion.site.domain.assignment.domain.AssignmentMainContent;
import likelion.site.domain.assignment.domain.AssignmentPart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequest {

    String category;
    String title;
    String content;
    String link;
    String part;
    List<String> tags;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime expireAt;

    public Assignment toEntity(){
        AssignmentPart assignmentPart = AssignmentPart.findByName(part);
        AssignmentMainContent assignmentMainContent = AssignmentMainContent.findByName(category);
        return Assignment.builder()
                .title(title)
                .content(content)
                .githubLink(link)
                .assignmentPart(assignmentPart)
                .expireAt(expireAt)
                .tags(tags)
                .assignmentMainContent(assignmentMainContent)
                .build();
    }
}
