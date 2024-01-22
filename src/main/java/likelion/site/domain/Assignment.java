package likelion.site.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Assignment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long id;

    private String title;

    @Column(length = 50000)
    private String content;

    @Enumerated(EnumType.STRING)
    private Part part;

    private LocalDateTime createdAt;

    private LocalDateTime expireAt;

    @ElementCollection
    private List<String> tag = new ArrayList<String>();

    @OneToMany(mappedBy = "assignment")
    private List<Submission> submissions = new ArrayList<Submission>();
}
