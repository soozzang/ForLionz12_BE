package likelion.site.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private Part part;
    @Column(length = 250)
    private String introduction;
    @Nullable
    private String githubAddress;
    @Nullable
    private String instagramId;
    // 프로필 사진 해야함

    private String password;
    private String name;

    @Builder
    public User(String email, Part part, String password, String name) {
        this.email = email;
        this.part = part;
        this.password = password;
        this.name = name;
    }
}
