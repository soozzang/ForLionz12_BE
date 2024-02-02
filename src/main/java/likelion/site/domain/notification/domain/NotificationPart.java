package likelion.site.domain.notification.domain;

public enum NotificationPart {
    BE , FE , ALL;

    public static NotificationPart findByName(String name) {
        for (NotificationPart notificationPart : NotificationPart.values()) {
            if (notificationPart.name().equalsIgnoreCase(name)) {
                return notificationPart;
            }
        }
        return null;
    }
}
