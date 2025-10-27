package items;

import interfaces.Craftable;
import exceptions.ItemBrokenException;
import enums.Material;
import java.util.Objects;

public class Tool implements Craftable {
    private String name;
    private Material material;
    private boolean isCrafted;
    private int durability;
    private int efficiency;

    public Tool(String name, Material material) {
        this.name = name;
        this.material = material;
        this.isCrafted = false;
        this.durability = 0; // Пока не создан - прочность 0
        this.efficiency = (int)(Math.random() * 51) + 50; // 50-100%
    }

    @Override
    public void craft() {
        if (!isCrafted) {
            isCrafted = true;
            durability = 100; // При создании полная прочность
            System.out.println(name + " из " + material + " создан! Эффективность: " + efficiency + "%");
        }
    }

    @Override
    public boolean isFinished() {
        return isCrafted;
    }

    public boolean use() throws ItemBrokenException {
        if (!isCrafted) {
            throw new ItemBrokenException("Сначала создай инструмент " + name + "!");
        }
        if (durability <= 0) {
            throw new ItemBrokenException("Инструмент " + name + " сломан!");
        }

        // Уменьшаем прочность на 5-15 единиц
        int wear = (int)(Math.random() * 11) + 5;
        durability -= wear;

        // Проверяем успех на основе эффективности
        boolean success = Math.random() * 100 < efficiency;

        System.out.println(name + " использован. Прочность: " + durability + "%");

        if (durability <= 0) {
            throw new ItemBrokenException("Инструмент " + name + " сломался при использовании!");
        }

        return success;
    }

    public void repair() {
        if (isCrafted && durability < 100) {
            durability = 100;
            System.out.println(name + " отремонтирован! Прочность восстановлена.");
        }
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    // Геттеры
    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isCrafted() {
        return isCrafted;
    }

    public int getDurability() {
        return durability;
    }

    public int getEfficiency() {
        return efficiency;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Tool)) return false;
        Tool other = (Tool) obj;
        return name.equals(other.name) &&
                material == other.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, material);
    }

    @Override
    public String toString() {
        return String.format("Инструмент '%s' из %s (создан: %s, прочность: %d%%, эффективность: %d%%)",
                name, material, isCrafted ? "да" : "нет", durability, efficiency);
    }
}