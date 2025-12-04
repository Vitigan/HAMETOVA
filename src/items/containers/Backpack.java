package items.containers;

import items.Item;
import entities.LivingBeing;
import java.util.Objects;
import exceptions.InteractionException;

public class Backpack extends Container {
    private String material;
    private int durability;
    private boolean isWorn;

    private static final int DEFAULT_CAPACITY = 15;
    private static final String DEFAULT_MATERIAL = "кожа";
    private static final int DEFAULT_DURABILITY = 100;
    private static final double WEAR_CHANCE = 0.1;
    private static final int WEAR_AMOUNT = 5;

    public Backpack() {
        this(DEFAULT_CAPACITY, DEFAULT_MATERIAL);
    }

    public Backpack(int capacity, String material) {
        super("Рюкзак", 1.5, enums.Size.MEDIUM, (double) capacity, enums.Size.MEDIUM);
        this.material = material;
        this.durability = DEFAULT_DURABILITY;
        this.isWorn = false;
    }

    @Override
    public boolean store(Item item) {
        boolean added = super.store(item);
        if (added) {
            if (Math.random() < WEAR_CHANCE) {
                durability -= WEAR_AMOUNT;
                System.out.println("Рюкзак немного износился. Прочность: " + durability + "%");
            }
        }
        return added;
    }

    public void wear() {
        isWorn = true;
        System.out.println("Надеваю рюкзак на спину. Внутри: " + getContentsString());
    }

    public void takeOff() {
        if (isWorn) {
            isWorn = false;
            System.out.println("Снимаю рюкзак");
        }
    }

    @Override
    public void interactWithItem(LivingBeing interactor) {
        System.out.println(interactor.getName() + " взаимодействует с рюкзаком");

        if (storage.isEmpty()) {
            throw new InteractionException("Рюкзак пуст - нечего брать!");
        }

        System.out.println("В рюкзаке: " + getContentsString());

        if (isWorn) {
            System.out.println("Рюкзак надет на спину");
        }
    }

    @Override
    public String getDescription() {
        String status = isWorn ? "надет" : "снят";
        return material + " рюкзак (" + storage.getCurrentWeight() + "/" + storage.getMaxWeight() +
                ", прочность: " + durability + "%, " + status + ")";
    }

    public void repair() {
        if (durability < DEFAULT_DURABILITY) {
            durability = DEFAULT_DURABILITY;
            System.out.println("Рюкзак отремонтирован! Прочность восстановлена.");
        }
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    public int getCapacity() {
        return (int) storage.getMaxWeight();
    }

    public String getMaterial() {
        return material;
    }

    public int getDurability() {
        return durability;
    }

    public boolean isWorn() {
        return isWorn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Backpack))
            return false;
        if (!super.equals(o))
            return false;
        Backpack backpack = (Backpack) o;
        return durability == backpack.durability &&
                isWorn == backpack.isWorn &&
                Objects.equals(material, backpack.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), material, durability, isWorn);
    }

    @Override
    public String toString() {
        return "Backpack{" +
                "material='" + material + '\'' +
                ", durability=" + durability +
                ", isWorn=" + isWorn +
                ", " + super.toString() +
                '}';
    }
}
