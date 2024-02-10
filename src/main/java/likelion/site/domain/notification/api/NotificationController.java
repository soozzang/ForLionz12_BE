package likelion.site.domain.notification.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.notification.dto.request.NotificationRequest;
import likelion.site.domain.notification.dto.response.NotificationDetailResponse;
import likelion.site.domain.notification.dto.response.NotificationIdResponse;
import likelion.site.domain.notification.service.NotificationService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<NotificationDetailResponse>> createNotification(@RequestBody NotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(notificationService.addNotification(request,SecurityUtil.getCurrentMemberId())));
    }

    @Operation(summary = "id를 통해 특정 공지사항 상세 조회")
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<NotificationIdResponse>> getNotificationDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(notificationService.findNotificationById(id)));
    }

    @Operation(summary = "모든 공지사항 조회")
    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<NotificationIdResponse>>> findAllNotifications() {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(notificationService.findAllNotifications()));
    }

    @Operation(summary = "파트 별 공지사항 조회", description = "partName에는 BE/FE/ALL이 들어갈 수 있습니다.")
    @GetMapping("part/{part}")
    public ApiResponse<List<NotificationIdResponse>> findNotificationByPart(@PathVariable("part") String part) {
        return ApiResponse.createSuccess(notificationService.findByNotificationPart(part));
    }

    @Operation(summary = "특정 id의 공지사항 업데이트")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<NotificationDetailResponse>> updateNotification(@PathVariable("id") Long id, @RequestBody NotificationRequest request) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(notificationService.update(id, request)));
    }
}
