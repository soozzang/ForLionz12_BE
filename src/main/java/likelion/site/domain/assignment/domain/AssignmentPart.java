package likelion.site.domain.assignment.domain;

public enum AssignmentPart {
    BE , FE , ALL;

    public static AssignmentPart findByName(String name) {
        for (AssignmentPart Assignmentpart : AssignmentPart.values()) {
            if (Assignmentpart.name().equalsIgnoreCase(name)) {
                return Assignmentpart;
            }
        }
        return null;
    }
}
