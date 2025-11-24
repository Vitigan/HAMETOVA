package items.containers;

import entities.LivingBeing;
import interfaces.Craftable;
import interfaces.ItemInteractable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Backpack implements Craftable, ItemInteractable {
    private boolean isCrafted;
    private int capacity;
    private List<String> contents;
    private String material;
    private int durability;
    private boolean isWorn;

    public Backpack() {
        this.isCrafted = false;
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

    @Override
    public void craft() {
        if (isCrafted) {
            throw new exceptions.InteractionException("Рюкзак уже создан!");
        }
        isCrafted = true;
        System.out.println("Создан " + material + " рюкзак!");
    }

    @Override
    public boolean isFinished() {
        return isCrafted;
    }

    public boolean store(String item) {
        if (!isCrafted) {
            throw new exceptions.InteractionException("Сначала создай рюкзак!");
        }
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
        if (!isCrafted) {
            throw new exceptions.InteractionException("Сначала создай рюкзак!");
        }
        if (contents.isEmpty()) {
            System.out.println("В рюкзаке ничего нет!");
            return null;
        }

        String item = contents.remove(contents.size() - 1);
        System.out.println("Из рюкзака взят: " + item);
        return item;
    }

    public void wear() {
        if (!isCrafted) {
            throw new exceptions.InteractionException("Не могу надеть - рюкзак не создан!");
        }
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

        if (!isCrafted) {
            throw new exceptions.InteractionException("Рюкзак еще не создан!");
        }

        if (contents.isEmpty()) {
            throw new exceptions.InteractionException("Рюкзак пуст - нечего брать!");
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
        return isCrafted;
    }

    public void repair() {
        if (isCrafted && durability < 100) {
            durability = 100;
            System.out.println("Рюкзак отремонтирован! Прочность восстановлена.");
        }
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    // Геттеры
    public boolean isCrafted() {
        return isCrafted;
    }

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
        return String.format("Backpack{material='%s', capacity=%d, crafted=%s, worn=%s, contents=%s}",
                material, capacity, isCrafted, isWorn, contents.size());
    }
}