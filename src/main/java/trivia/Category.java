package trivia;

public enum Category {
    POP,
    SCIENCE,
    SPORTS,
    ROCK;

    /** Get the name of the category with the first letter capitalized. */
    @Override
    public String toString() {
        String name = name();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}
