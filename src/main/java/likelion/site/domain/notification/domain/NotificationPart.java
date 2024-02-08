package likelion.site.domain.notification.domain;

import likelion.site.global.exception.BadPartException;
import likelion.site.global.exception.CustomError;

public enum NotificationPart {
    BE , FE , ALL;

    public static NotificationPart findByName(String name) {
        for (NotificationPart notificationPart : NotificationPart.values()) {
            if (notificationPart.name().equalsIgnoreCase(name)) {
                return notificationPart;
            }
        }
        throw new BadPartException(CustomError.BAD_PART_ERROR);
    }
}
