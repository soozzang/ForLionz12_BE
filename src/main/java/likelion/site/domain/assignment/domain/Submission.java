package likelion.site.domain.assignment.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import likelion.site.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Submission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "submission_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(length = 500)
    private String description;

    @NotNull(message = "과제 링크는 null이 될 수 없습니다.")
    private String assignmentLink;

    @Builder
    public Submission(Member member, Assignment assignment, String description, String assignmentLink) {
        this.member = member;
        this.assignment = assignment;
        this.description = description;
        this.assignmentLink = assignmentLink;
    }

    public void updateSubmission(String description, String assignmentLink) {
        this.description = description;
        this.assignmentLink = assignmentLink;
    }
}
