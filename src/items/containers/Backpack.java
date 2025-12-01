package items.containers;

import items.Item;
import entities.LivingBeing;
import items.ItemInteractable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import exceptions.InteractionException;

public class Backpack extends Item implements ItemInteractable {
    private int capacity;
    private List<String> contents;
    private String material;
    private int durability;
    private boolean isWorn;

    public Backpack() {
        super("Рюкзак");
        this.capacity = 15; // Меньше чем у корзины, но удобнее для переноски
        this.contents = new ArrayList<>();
        this.material = "кожа";
        this.durability = 100;
        this.isWorn = false;
    }

    public Backpack(int capacity, String material) {
        this();
        this.capacity = capacity;
        this.material = material;
    }

    public boolean store(String item) {
        if (contents.size() >= capacity) {
            System.out.println("Рюкзак полон! Нельзя положить: " + item);
            return false;
        }
        contents.add(item);
        System.out.println("В рюкзак положен: " + item);

        // Рюкзак изнашивается при использовании
        if (Math.random() < 0.1) {
            durability -= 5;
            System.out.println("Рюкзак немного износился. Прочность: " + durability + "%");
        }
        return true;
    }

    public String take() {
        if (contents.isEmpty()) {
            System.out.println("В рюкзаке ничего нет!");
            return null;
        }

        String item = contents.remove(contents.size() - 1);
        System.out.println("Из рюкзака взят: " + item);
        return item;
    }

    public void wear() {
        isWorn = true;
        System.out.println("Надеваю рюкзак на спину. Внутри: " + contents);
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

        if (contents.isEmpty()) {
            throw new InteractionException("Рюкзак пуст - нечего брать!");
        }

        System.out.println("В рюкзаке: " + contents);

        if (isWorn) {
            System.out.println("Рюкзак надет на спину");
        }
    }

    @Override
    public String getDescription() {
        String status = isWorn ? "надет" : "снят";
        return material + " рюкзак (" + contents.size() + "/" + capacity +
                ", прочность: " + durability + "%, " + status + ")";
    }

    @Override
    public boolean canInteract() {
        return true;
    }

    public void repair() {
        if (durability < 100) {
            durability = 100;
            System.out.println("Рюкзак отремонтирован! Прочность восстановлена.");
        }
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    // Геттеры
    public int getCapacity() {
        return capacity;
    }

    public List<String> getContents() {
        return new ArrayList<>(contents);
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
        Backpack backpack = (Backpack) o;
        return capacity == backpack.capacity &&
                Objects.equals(material, backpack.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, material);
    }

    @Override
    public String toString() {
        return String.format("Backpack{material='%s', capacity=%d, worn=%s, contents=%s}",
                material, capacity, isWorn, contents.size());
    }
}
