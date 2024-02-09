package likelion.site.domain.notification.dto.response;

import lombok.Getter;

@Getter
public class NotificationIdResponseDto {

    Long id;

    public NotificationIdResponseDto(Long id) {
        this.id = id;
    }
}
