package likelion.site.service;

import likelion.site.domain.Member;
import likelion.site.domain.Notification;
import likelion.site.domain.Part;
import likelion.site.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        return notificationRepository.findById(notificationId);
    }

    public List<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> findNotificationByPart(Part part) {
        return notificationRepository.findByPart(part);
    }

    @Transactional
    public void update(Long id, String title, String content, Part part) {
        notificationRepository.update(id, title, content, part);
    }

    @Transactional
    public void delete(Long id) {
        notificationRepository.delete(id);
    }

}
