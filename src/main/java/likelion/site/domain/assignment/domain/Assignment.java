package likelion.site.domain.assignment.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;

    @NotNull(message = "제목은 null이 될 수 없습니다.")
    private String title;

    @Column(length = 50000)
    @NotNull(message = "글 내용은 null이 될 수 없습니다.")
    private String content;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "과제의 파트는 null이 될 수 없습니다.")
    private AssignmentPart assignmentPart;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "과제의 카테고리는 null이 될 수 없습니다.")
    private AssignmentMainContent assignmentMainContent;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @NotNull(message = "과제의 마감기한은 null이 될 수 없습니다.")
    private LocalDateTime expireAt;

    @ElementCollection
    private List<String> tags = new ArrayList<String>();

    @OneToMany(mappedBy = "assignment")
    private List<Submission> submissions = new ArrayList<Submission>();

    @Builder
    public Assignment(String title, AssignmentMainContent assignmentMainContent , AssignmentPart assignmentPart, String content, LocalDateTime expireAt, List<String> tags) {
        this.title = title;
        this.assignmentMainContent = assignmentMainContent;
        this.assignmentPart = assignmentPart;
        this.content = content;
        this.expireAt = expireAt;
        this.tags = tags;
    }

    public void updateAssignment(String title, String content, AssignmentMainContent assignmentMainContent ,  AssignmentPart assignmentPart, LocalDateTime expireAt, List<String> tags) {
        this.title = title;
        this.content = content;
        this.assignmentPart = assignmentPart;
        this.assignmentMainContent = assignmentMainContent;
        this.expireAt = expireAt;
        this.tags = tags;
    }
}
