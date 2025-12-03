package items.inventory;

import items.Item;
import enums.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryStorage {
    private final double capacity;
    private final Size maxItemSize; // Максимальный размер предмета, который влезает
    private final List<InventoryItem> items;

    public InventoryStorage(double capacity) {
        this(capacity, Size.MEDIUM); // По умолчанию вмещает средние предметы
    }

    public InventoryStorage(double capacity, Size maxItemSize) {
        this.capacity = capacity;
        this.maxItemSize = maxItemSize;
        this.items = new ArrayList<>();
    }

    public boolean addItem(InventoryItem item) {
        if (!item.size().canFitIn(maxItemSize)) {
            System.out.println("Предмет " + item.name() + " (" + item.size() + ") слишком большой для этого хранилища ("
                    + maxItemSize + ")!");
            return false;
        }
        if (getCurrentWeight() + item.getTotalWeight() <= capacity) {
            items.add(item);
            return true;
        }
        return false;
    }

    public boolean addItem(Item item) {
        InventoryItem invItem = new InventoryItem(item.getName(), 1, item.getWeight(), item.getSize(), item);
        return addItem(invItem);
    }

    public double getCurrentWeight() {
        double total = 0;
        for (InventoryItem item : items) {
            total += item.getTotalWeight();
        }
        return total;
    }

    public void printContents() {
        if (items.isEmpty()) {
            System.out.println("Инвентарь пуст.");
        } else {
            for (InventoryItem item : items) {
                System.out.println(item);
            }
            System.out.printf("Общий вес: %.1f / %.1f кг%n", getCurrentWeight(), capacity);
        }
    }

    public List<InventoryItem> getItems() {
        return new ArrayList<>(items);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof InventoryStorage that))
            return false;
        return Double.compare(that.capacity, capacity) == 0 &&
                items.equals(that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capacity, items);
    }

    @Override
    public String toString() {
        return "InventoryStorage{capacity=" + capacity + ", items=" + items + "}";
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getMaxWeight() {
        return capacity;
    }

    // Возвращает InventoryItem если предмет найден и взят, иначе null
    public InventoryItem takeItem(String name, int quantity) {
        for (int i = 0; i < items.size(); i++) {
            InventoryItem item = items.get(i);
            if (item.name().equalsIgnoreCase(name)) {
                if (item.quantity() >= quantity) {
                    // Уменьшаем количество или удаляем
                    if (item.quantity() == quantity) {
                        items.remove(i);
                        return item;
                    } else {
                        // Заменяем на предмет с меньшим количеством
                        int remaining = item.quantity() - quantity;
                        items.set(i, item.withQuantity(remaining));
                        return new InventoryItem(item.name(), quantity, item.weightPerUnit(), item.size(),
                                item.itemReference());
                    }
                }
            }
        }
        return null;
    }
}
