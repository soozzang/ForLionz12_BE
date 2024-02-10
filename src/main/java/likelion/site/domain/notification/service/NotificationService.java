package likelion.site.domain.notification.service;

import likelion.site.domain.member.repository.MemberRepository;
import likelion.site.domain.notification.domain.Notification;
import likelion.site.domain.notification.domain.NotificationPart;
import likelion.site.domain.notification.dto.request.NotificationRequest;
import likelion.site.domain.notification.dto.response.NotificationIdResponse;
import likelion.site.domain.notification.dto.response.NotificationDetailResponse;
import likelion.site.domain.notification.repository.NotificationRepository;
import likelion.site.global.exception.CustomError;
import likelion.site.global.exception.exceptions.AuthorizationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public NotificationIdResponse addNotification(NotificationRequest request, Long memberId){
        if (memberRepository.findById(memberId).get().getPart().isStaff()){
            Notification notification = request.toEntity();
            notificationRepository.save(notification);
            return new NotificationIdResponse(notification);
        }
        throw new AuthorizationException(CustomError.AUTHORIZATION_EXCEPTION);
    }

    public NotificationDetailResponse findNotificationById(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).get();
        return new NotificationDetailResponse(notification);
    }

    public List<NotificationDetailResponse> findAllNotifications() {
        return NotificationDetailResponse.to(notificationRepository.findAll());
    }

    public List<NotificationDetailResponse> findByNotificationPart(String partName) {
        NotificationPart notificationPart = NotificationPart.findByName(partName);
        return NotificationDetailResponse.to(notificationRepository.findByNotificationPart(notificationPart));
    }

    @Transactional
    public NotificationIdResponse update(Long id, NotificationRequest request) {
        NotificationPart notificationPart = NotificationPart.findByName(request.getPart());
        Notification notification = notificationRepository.findById(id).get();
        notification.updateNotification(request.getTitle(), request.getContent(), notificationPart);
        return new NotificationIdResponse(notification);
    }

    @Transactional
    public void delete(Long id) {
        notificationRepository.delete(notificationRepository.findById(id).get());
    }

}
