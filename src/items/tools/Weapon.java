package items.tools;

import items.Item;
import exceptions.ItemBrokenException;
import enums.WeaponType;
import java.util.Objects;

public class Weapon extends Item {
    private WeaponType type;
    private int durability;
    private int effectiveness;

    public Weapon(WeaponType type) {
        super(type.toString());
        this.type = type;
        this.durability = 100;
        this.effectiveness = (int) (Math.random() * 51) + 50; // 50-100%
    }

    public boolean use() throws ItemBrokenException {
        if (durability <= 0) {
            throw new ItemBrokenException("Оружие сломано!");
        }

        durability -= 15; // Оружие изнашивается при использовании
        boolean success = Math.random() * 100 < effectiveness; // Шанс успеха

        System.out.println("Использовано " + type + ". Прочность: " + durability + "%");

        if (durability <= 0) {
            throw new ItemBrokenException("Оружие сломалось при использовании!");
        }

        return success;
    }

    public void repair() {
        if (durability < 100) {
            durability = 100;
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
