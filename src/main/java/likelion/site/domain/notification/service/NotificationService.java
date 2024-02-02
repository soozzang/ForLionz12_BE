package likelion.site.domain.notification.service;

import likelion.site.domain.notification.domain.Notification;
import likelion.site.domain.notification.domain.NotificationPart;
import likelion.site.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public Long addNotification(Notification notification){
        notificationRepository.save(notification);
        return notification.getId();
    }

    public Notification findNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId).get();
    }

    public List<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> findByNotificationPart(NotificationPart notificationPart) {
        return notificationRepository.findByNotificationPart(notificationPart);
    }

    @Transactional
    public void update(Long id, String title, String content, NotificationPart notificationPart) {
        Notification notification = notificationRepository.findById(id).get();
        notification.updateNotification(title, content, notificationPart);
    }

    @Transactional
    public void delete(Long id) {
        notificationRepository.delete(notificationRepository.findById(id).get());
    }

}
