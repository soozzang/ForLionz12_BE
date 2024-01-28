package likelion.site.domain;

import jakarta.persistence.*;
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

    private String title;

    @Column(length = 50000)
    private String content;

    @Enumerated(EnumType.STRING)
    private Part part;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime expireAt;

    @ElementCollection
    private List<String> tags = new ArrayList<String>();

    @OneToMany(mappedBy = "assignment")
    private List<Submission> submissions = new ArrayList<Submission>();

    @Builder
    public Assignment(String title, Part part, String content, LocalDateTime expireAt, List<String> tags) {
        this.title = title;
        this.part = part;
        this.content = content;
        this.expireAt = expireAt;
        this.tags = tags;
    }

    public void updateAssignment(String title, String content, Part part, LocalDateTime expireAt, List<String> tags) {
        this.title = title;
        this.content = content;
        this.part = part;
        this.expireAt = expireAt;
        this.tags = tags;
    }
}
