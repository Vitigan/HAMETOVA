package enums;

public enum Size {
    SMALL, // Мелкий (яблоко, мышь, трубка)
    MEDIUM, // Средний (человек, коза, рюкзак)
    LARGE, // Крупный (лодка, корова)
    GIANT; // Гигантский (слон, дом)

    public boolean canFitIn(Size other) {
        return this.ordinal() <= other.ordinal();
    }
}
