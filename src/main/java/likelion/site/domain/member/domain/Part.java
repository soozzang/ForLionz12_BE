package likelion.site.domain.member.domain;

import likelion.site.global.exception.exceptions.BadPartException;
import likelion.site.global.exception.CustomError;

public enum Part {
    BE, FE, STAFF;

    public static Part findByName(String name) {
        for (Part part : Part.values()) {
            if (part.name().equalsIgnoreCase(name)) {
                return part;
            }
        }
        throw new BadPartException(CustomError.BAD_PART_ERROR);
    }

    public boolean isStaff() {
        return this == Part.STAFF;
    }
}
