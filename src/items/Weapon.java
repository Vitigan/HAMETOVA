package items;

import interfaces.Craftable;
import exceptions.ItemBrokenException;
import enums.WeaponType;
import java.util.Objects;

public class Weapon implements Craftable {
    private WeaponType type;
    private boolean isCrafted;
    private int durability;
    private int effectiveness;

    public Weapon(WeaponType type) {
        this.type = type;
        this.isCrafted = false;
        this.durability = 100;
        this.effectiveness = (int)(Math.random() * 51) + 50; // 50-100%
    }

    @Override
    public void craft() {
        if (!isCrafted) {
            isCrafted = true;
            System.out.println(type + " создано! Эффективность: " + effectiveness + "%");
        }
    }

    @Override
    public boolean isFinished() {
        return isCrafted;
    }

    public boolean use() throws ItemBrokenException {
        if (!isCrafted) {
            throw new ItemBrokenException("Оружие еще не создано!");
        }
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
        if (isCrafted && durability < 100) {
            durability = 100;
            System.out.println(type + " отремонтировано!");
        }
    }

    // Геттеры
    public WeaponType getType() {
        return type;
    }

    public boolean isCrafted() {
        return isCrafted;
    }

    public int getDurability() {
        return durability;
    }

    public int getEffectiveness() {
        return effectiveness;
    }

    @Override
    public String toString() {
        return String.format("Оружие %s (создано: %s, прочность: %d%%, эффективность: %d%%)",
                type, isCrafted ? "да" : "нет", durability, effectiveness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weapon weapon = (Weapon) o;
        return isCrafted == weapon.isCrafted &&
                durability == weapon.durability &&
                effectiveness == weapon.effectiveness &&
                type == weapon.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, isCrafted, durability, effectiveness);
    }
}