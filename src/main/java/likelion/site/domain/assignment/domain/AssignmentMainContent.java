package likelion.site.domain.assignment.domain;

public enum AssignmentMainContent {
    HTML , REACT , DJANGO , AWS , JS , CSS , Docker, Git, PYTHON;

    public static AssignmentMainContent findByName(String name) {
        for (AssignmentMainContent assignmentMainContent : AssignmentMainContent.values()) {
            if (assignmentMainContent.name().equalsIgnoreCase(name)) {
                return assignmentMainContent;
            }
        }
        return null;
    }
}
