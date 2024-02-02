package likelion.site.domain.notification.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import likelion.site.domain.notification.domain.Notification;
import likelion.site.domain.notification.domain.NotificationPart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Notification notification){
        em.persist(notification);
    }

    public List<Notification> findAll() {
        return em.createQuery("select n from Notification n", Notification.class)
                .getResultList();
    }

    public Notification findById(Long id) {
        return em.find(Notification.class, id);
    }

    public void delete(Long id) {
        em.createQuery(
                        "delete from Notification n where n.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public List<Notification> findByNotificationPart(NotificationPart notificationPart) {
        return em.createQuery("select n from Notification n where n.notificationPart=:notificationPart",Notification.class)
                .setParameter("part",notificationPart)
                .getResultList();
    }

    public void update(Long id, String title, String content, NotificationPart notificationPart) {
        Notification notification = findById(id);
        notification.updateNotification(title,content,notificationPart);
    }
}
