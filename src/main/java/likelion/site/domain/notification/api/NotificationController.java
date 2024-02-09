package likelion.site.domain.notification.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import likelion.site.domain.notification.domain.Notification;
import likelion.site.domain.notification.domain.NotificationPart;
import likelion.site.domain.member.domain.Part;
import likelion.site.domain.member.service.MemberService;
import likelion.site.domain.notification.dto.request.NotificationRequestDto;
import likelion.site.domain.notification.dto.response.NotificationIdResponseDto;
import likelion.site.domain.notification.dto.response.NotificationResponseDto;
import likelion.site.domain.notification.service.NotificationService;
import likelion.site.global.ApiResponse;
import likelion.site.global.util.SecurityUtil;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ApiResponse<NotificationIdResponseDto> createNotification(@RequestBody NotificationRequestDto request) {
        if(memberService.findMemberInfoById(SecurityUtil.getCurrentMemberId()).getPart() == Part.STAFF) {
            NotificationPart notificationPart = NotificationPart.findByName(request.getPart());
            Notification notification = Notification.builder()
                    .title(request.getTitle())
                    .notificationPart(notificationPart)
                    .content(request.getContent())
                    .build();

            Long id = notificationService.addNotification(notification);
            return ApiResponse.createSuccess(new NotificationIdResponseDto(id));
        }
        return null;
    }

    @Operation(summary = "id를 통해 특정 공지사항 상세 조회")
    @GetMapping("{id}")
    public ApiResponse<NotificationResponseDto> getNotificationDetail(@PathVariable("id") Long id) {
        Notification notification = notificationService.findNotificationById(id);
        return ApiResponse.createSuccess(new NotificationResponseDto(notification));
    }

    @Operation(summary = "모든 공지사항 조회")
    @GetMapping("all")
    public ApiResponse<List<NotificationResponseDto>> findAllNotifications() {
        List<Notification> notifications = notificationService.findAllNotifications();
        List<NotificationResponseDto> notificationDtoList = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationResponseDto dto = new NotificationResponseDto(notification);
            notificationDtoList.add(dto);
        }
        return ApiResponse.createSuccess(notificationDtoList);
    }

    @Operation(summary = "파트 별 공지사항 조회", description = "partName에는 BE/FE/ALL이 들어갈 수 있습니다.")
    @GetMapping("part/{part}")
    public ApiResponse<List<NotificationResponseDto>> findNotificationByPart(@PathVariable("part") String part) {
        NotificationPart notificationPart = NotificationPart.findByName(part);

        List<NotificationResponseDto> notificationDtoList = new ArrayList<>();
        List<Notification> notifications = notificationService.findByNotificationPart(notificationPart);

        for (Notification notification : notifications) {
            NotificationResponseDto dto = new NotificationResponseDto(notification);
            notificationDtoList.add(dto);
        }
        return ApiResponse.createSuccess(notificationDtoList);
    }

    @Operation(summary = "특정 id의 공지사항 업데이트")
    @PutMapping("{id}")
    public ApiResponse<NotificationIdResponseDto> updateNotification(@PathVariable("id") Long id, @RequestBody NotificationRequestDto request) {
        NotificationPart notificationPart = NotificationPart.findByName(request.getPart());
        notificationService.update(id, request.getTitle(), request.getContent(), notificationPart);
        return ApiResponse.createSuccess(new NotificationIdResponseDto(id));
    }
}
