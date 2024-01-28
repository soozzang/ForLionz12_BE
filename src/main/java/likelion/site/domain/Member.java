package likelion.site.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private Part part;

    private String introduction;

    private String githubAddress;

    private String instagramId;


    @Builder
    public Member(String email, String password, Authority authority, Part part) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.part = part;
    }

    public void updateIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void updateGithubAddress(String githubAddress) {
        this.githubAddress = githubAddress;
    }

    public void updateInstagramId(String instagramId) {
        this.instagramId = instagramId;
    }
}