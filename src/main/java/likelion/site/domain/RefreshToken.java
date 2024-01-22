package likelion.site.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
@Entity
public class RefreshToken {
    @Id
    @Column(name = "rt_key")
    private String key;

    @Column(name = "rt_value")
    private String value;

    @Column(name = "rt_access")
    private String accessToken;
    @Builder
    public RefreshToken(String key, String value, String accessToken) {
        this.key = key;
        this.value = value;
        this.accessToken = accessToken;
    }

    public RefreshToken updateValue(String token, String accessToken) {
        this.value = token;
        this.accessToken = accessToken;
        return this;
    }

}