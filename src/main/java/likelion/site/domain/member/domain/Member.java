package likelion.site.domain.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "이름을 입력하세요.")
    @Column(unique = true)
    private String email;

    @NotNull(message = "이름을 입력하세요.")
    private String name;

    @NotNull(message = "비밀번호를 입력하세요.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @NotNull(message = "파트를 입력하세요.")
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