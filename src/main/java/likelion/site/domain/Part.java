package likelion.site.domain;

public enum Part {
    BE, FE, STAFF;

    public static Part findByName(String name) {
        for (Part part : Part.values()) {
            if (part.name().equalsIgnoreCase(name)) {
                return part;
            }
        }
        return null;
    }
}
