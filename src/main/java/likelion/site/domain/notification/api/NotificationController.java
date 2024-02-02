package likelion.site.domain.notification.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.notification.domain.Notification;
import likelion.site.domain.notification.domain.NotificationPart;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.notification.service.NotificationService;
import likelion.site.global.util.SecurityUtil;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Notification", description = "공지사항")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final MemberService memberService;
    private final NotificationService notificationService;

    @Operation(summary = "공지사항 생성", description = "STAFF인 사용자만 가능합니다.")
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@RequestBody NotificationRequest request) {
        if(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            NotificationPart notificationPart = NotificationPart.findByName(request.part);
            Notification notification = Notification.builder()
                    .title(request.title)
                    .notificationPart(notificationPart)
                    .content(request.content)
                    .build();

            Long id = notificationService.addNotification(notification);
            return ResponseEntity.ok().body(new NotificationResponse(id));
        }
        return null;
    }

    @Operation(summary = "id를 통해 특정 공지사항 상세 조회")
    @GetMapping("{id}")
    public ResponseEntity<NotificationDto> getNotificationDetail(@PathVariable("id") Long id) {
        Notification notification = notificationService.findNotificationById(id);
        return ResponseEntity.ok().body(new NotificationDto(notification));
    }

    @Operation(summary = "파트 별 공지사항 조회" , description = "partName에는 BE/FE/ALL이 들어갈 수 있습니다.")
    @GetMapping("part/{part}")
    public ResponseEntity<Result> findNotificationByPart(@PathVariable("part") String part) {
        NotificationPart notificationPart = NotificationPart.findByName(part);
        List<Notification> notifications = notificationService.findNotificationByPart(notificationPart);
        List<NotificationDto> collect = notifications.stream()
                .map(NotificationDto::new)
                .toList();
        return ResponseEntity.ok().body(new Result(collect));
    }

    @Operation(summary = "특정 id의 공지사항 업데이트")
    @PutMapping("{id}")
    public ResponseEntity<NotificationResponse> updateNotification(@PathVariable("id") Long id, @RequestBody NotificationRequest request) {
        NotificationPart notificationPart = NotificationPart.findByName(request.part);
        notificationService.update(id, request.getTitle(), request.getContent(), notificationPart);
        return ResponseEntity.ok().body(new NotificationResponse(id));
    }

//    @DeleteMapping
//    public void deleteNotification(@RequestParam Long id) {
//        notificationService.delete(id);
//    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class NotificationRequest {
        String title;
        String content;
        String part;
    }

    @Data
    @Setter(AccessLevel.NONE)
    public static class NotificationResponse {
        Long id;

        public NotificationResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    public static class NotificationDto {
        Long id;
        String title;
        String content;
        NotificationPart notificationPart;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt;

        public NotificationDto(Notification notification) {
            this.id = notification.getId();
            this.title = notification.getTitle();
            this.content = notification.getContent();
            this.notificationPart = notification.getNotificationPart();
            this.createdAt = notification.getCreatedAt();
        }
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
