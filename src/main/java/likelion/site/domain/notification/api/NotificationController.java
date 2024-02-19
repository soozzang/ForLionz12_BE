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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static likelion.site.domain.notification.domain.success.NotificationSuccess.*;

@Tag(name = "Notification", description = "공지사항")
@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final MemberService memberService;
    private final NotificationService notificationService;

    @Operation(summary = "공지사항 생성", description = "STAFF인 사용자만 가능합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<NotificationIdResponse>> createNotification(@RequestBody NotificationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.createSuccess(NOTIFICATION_CREATED_SUCCESS,notificationService.addNotification(request,SecurityUtil.getCurrentMemberId())));
    }

    @Operation(summary = "id를 통해 특정 공지사항 상세 조회")
    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<NotificationDetailResponse>> getNotificationDetail(@PathVariable(value = "id", required = false) Long id) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_NOTIFICATION_SUCCESS,notificationService.findNotificationById(id)));
    }

    @Operation(summary = "모든 공지사항 조회")
    @GetMapping("all")
    public ResponseEntity<ApiResponse<List<NotificationDetailResponse>>> findAllNotifications() {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_NOTIFICATION_SUCCESS,notificationService.findAllNotifications()));
    }

    @Operation(summary = "파트 별 공지사항 조회", description = "partName에는 BE/FE/ALL이 들어갈 수 있습니다.")
    @GetMapping("part/{part}")
    public ResponseEntity<ApiResponse<List<NotificationDetailResponse>>> findNotificationByPart(@PathVariable("part") String part) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(GET_NOTIFICATION_SUCCESS,notificationService.findByNotificationPart(part)));
    }

    @Operation(summary = "특정 id의 공지사항 업데이트")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<NotificationIdResponse>> updateNotification(@PathVariable("id") Long id, @RequestBody NotificationRequest request) {
        return ResponseEntity.ok().body(ApiResponse.createSuccess(NOTIFICATION_UPDATED_SUCCESS,notificationService.update(id, request)));
    }
}
