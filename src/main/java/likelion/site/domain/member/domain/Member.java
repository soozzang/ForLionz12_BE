package likelion.site.domain.member.domain;

import jakarta.persistence.*;
import lombok.*;

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
    public Member(String email, String name, String password, Authority authority, Part part) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.part = part;
        this.name = name;
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

    public void updatePassword(String password) {
        this.password = password;
    }
}