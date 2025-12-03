package items.tools;

import items.Item;
import exceptions.ItemBrokenException;
import enums.WeaponType;
import java.util.Objects;

public class Weapon extends Item {
    private WeaponType type;
    private int durability;
    private int effectiveness;
    private static final int DEFAULT_DURABILITY = 100;
    private static final int USAGE_COST = 15;
    private static final int MIN_EFFECTIVENESS = 50;
    private static final int MAX_EFFECTIVENESS_VARIANCE = 51;

    public Weapon(WeaponType type) {
        super(type.toString(), 3.0, enums.Size.MEDIUM); // Оружие среднего размера
        this.type = type;
        this.durability = DEFAULT_DURABILITY;
        this.effectiveness = (int) (Math.random() * MAX_EFFECTIVENESS_VARIANCE) + MIN_EFFECTIVENESS; // 50-100%
    }

    public boolean use() throws ItemBrokenException {
        if (durability <= 0) {
            throw new ItemBrokenException("Оружие сломано!");
        }

        durability -= USAGE_COST; // Оружие изнашивается при использовании
        boolean success = Math.random() * 100 < effectiveness; // Шанс успеха

        System.out.println("Использовано " + type + ". Прочность: " + durability + "%");

        if (durability <= 0) {
            throw new ItemBrokenException("Оружие сломалось при использовании!");
        }

        return success;
    }

    public void repair() {
        if (durability < DEFAULT_DURABILITY) {
            durability = DEFAULT_DURABILITY;
            System.out.println(type + " отремонтировано!");
        }
    }

    // Геттеры
    public WeaponType getType() {
        return type;
    }

    public int getDurability() {
        return durability;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    @Override
    public String toString() {
        return String.format("Оружие %s (прочность: %d%%, эффективность: %d%%)",
                type, durability, effectiveness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Weapon weapon = (Weapon) o;
        return durability == weapon.durability &&
                effectiveness == weapon.effectiveness &&
                type == weapon.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, durability, effectiveness);
    }
}
