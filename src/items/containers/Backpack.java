package items.containers;

import items.Item;
import entities.LivingBeing;
import items.ItemInteractable;
import items.inventory.HoldsItems;
import items.inventory.InventoryItem;
import items.inventory.InventoryStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import exceptions.InteractionException;
import entities.human.Robinson;

public class Backpack extends Item implements ItemInteractable, HoldsItems {
    private final InventoryStorage storage;
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
        super("Рюкзак", 1.5, enums.Size.MEDIUM);
        this.storage = new InventoryStorage((double) capacity, enums.Size.MEDIUM);
        this.material = material;
        this.durability = DEFAULT_DURABILITY;
        this.isWorn = false;
    }

    @Override
    public InventoryStorage getStorage() {
        return storage;
    }

    public boolean store(String item) {
        // Создаем предмет с дефолтным весом 1.0 и размером SMALL
        return storage.addItem(new InventoryItem(item, 1, 1.0));
    }

    public boolean store(Item item) {
        boolean added = storage.addItem(item);
        if (added) {
            System.out.println("В рюкзак положен: " + item.getName());
            if (Math.random() < WEAR_CHANCE) {
                durability -= WEAR_AMOUNT;
                System.out.println("Рюкзак немного износился. Прочность: " + durability + "%");
            }
        } else {
            System.out.println("Рюкзак полон или предмет слишком велик! Нельзя положить: " + item.getName());
        }
        return added;
    }

    public String take() {
        if (storage.isEmpty()) {
            System.out.println("В рюкзаке ничего нет!");
            return null;
        }
        // Берем первый попавшийся предмет
        InventoryItem item = storage.getItems().get(0);
        return take(item.name());
    }

    public String take(String name) {
        InventoryItem item = storage.takeItem(name, 1);
        if (item != null) {
            System.out.println("Из рюкзака взят: " + item.name());
            return item.name();
        } else {
            System.out.println("Предмет '" + name + "' не найден в рюкзаке.");
            return null;
        }
    }

    public InventoryItem drop(String name) {
        InventoryItem item = storage.takeItem(name, 1);
        if (item != null) {
            System.out.println("Выброшен из рюкзака: " + item.name());
            return item;
        } else {
            System.out.println("Предмет '" + name + "' не найден, нельзя выбросить.");
            return null;
        }
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

    @Override
    public boolean canInteract() {
        return true;
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

    // Геттеры
    public int getCapacity() {
        return (int) storage.getMaxWeight();
    }

    // Вспомогательный метод для получения списка названий (для совместимости)
    public List<String> getContents() {
        List<String> names = new ArrayList<>();
        for (InventoryItem item : storage.getItems()) {
            names.add(item.name());
        }
        return names;
    }

    private String getContentsString() {
        return storage.getItems().toString();
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
        return getCapacity() == backpack.getCapacity() &&
                Objects.equals(material, backpack.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCapacity(), material);
    }

    @Override
    public String toString() {
        return String.format("Backpack{material='%s', capacity=%d, worn=%s, contents=%s}",
                material, getCapacity(), isWorn, storage.getItems().size());
    }
}
