package likelion.site.domain.assignment.domain;

import likelion.site.global.exception.exceptions.BadCategoryException;
import likelion.site.global.exception.CustomError;

public enum AssignmentMainContent {
    HTML , REACT , DJANGO , AWS , JS , CSS , Docker, Git, PYTHON;

    public static AssignmentMainContent findByName(String name) {
        for (AssignmentMainContent assignmentMainContent : AssignmentMainContent.values()) {
            if (assignmentMainContent.name().equalsIgnoreCase(name)) {
                return assignmentMainContent;
            }
        }
        throw new BadCategoryException(CustomError.BAD_CATEGORY_ERROR);
    }
}
