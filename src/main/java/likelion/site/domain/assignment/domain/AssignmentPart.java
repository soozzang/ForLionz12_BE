package likelion.site.domain.assignment.domain;

import likelion.site.global.exception.exceptions.BadPartException;
import likelion.site.global.exception.CustomError;

public enum AssignmentPart {
    BE , FE , ALL;

    public static AssignmentPart findByName(String name) {
        for (AssignmentPart Assignmentpart : AssignmentPart.values()) {
            if (Assignmentpart.name().equalsIgnoreCase(name)) {
                return Assignmentpart;
            }
        }
        throw new BadPartException(CustomError.BAD_PART_ERROR);
    }
}
