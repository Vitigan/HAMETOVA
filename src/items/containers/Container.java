package items.containers;

import items.Item;
import items.ItemInteractable;
import items.inventory.Inventory;
import items.inventory.InventoryItem;
import enums.Size;
import entities.LivingBeing;
import java.util.ArrayList;
import java.util.List;

public abstract class Container extends Item implements ItemInteractable {
    protected final Inventory storage;

    public Container(String name, double weight, Size size, double capacity, Size maxItemSize) {
        super(name, weight, size);
        this.storage = new Inventory(capacity, maxItemSize);
    }

    public Inventory getStorage() {
        return storage;
    }

    public boolean store(Item item) {
        boolean added = storage.addItem(item);
        if (added) {
            System.out.println("В " + getName() + " положен: " + item.getName());
        } else {
            System.out.println(getName() + " полон или предмет слишком велик! Нельзя положить: " + item.getName());
        }
        return added;
    }

    public boolean store(String itemName) {
        return storage.addItem(new InventoryItem(itemName, 1, 1.0));
    }

    public boolean store(InventoryItem item) {
        return storage.addItem(item);
    }

    public String take(String name) {
        InventoryItem item = storage.takeItem(name, 1);
        if (item != null) {
            System.out.println("Из " + getName() + " взят: " + item.name());
            return item.name();
        } else {
            System.out.println("Предмет '" + name + "' не найден в " + getName());
            return null;
        }
    }

    public String take() {
        if (storage.isEmpty()) {
            System.out.println("В " + getName() + " ничего нет!");
            return null;
        }
        InventoryItem item = storage.getItems().get(0);
        return take(item.name());
    }

    public InventoryItem drop(String name) {
        InventoryItem item = storage.takeItem(name, 1);
        if (item != null) {
            System.out.println("Выброшен из " + getName() + ": " + item.name());
            return item;
        } else {
            System.out.println("Предмет '" + name + "' не найден в " + getName() + ", нельзя выбросить.");
            return null;
        }
    }

    public List<String> getContents() {
        List<String> names = new ArrayList<>();
        for (InventoryItem item : storage.getItems()) {
            names.add(item.name());
        }
        return names;
    }

    protected String getContentsString() {
        return storage.getItems().toString();
    }

    @Override
    public void interactWithItem(LivingBeing interactor) {
        System.out.println(interactor.getName() + " взаимодействует с " + getName());
        if (!storage.isEmpty()) {
            System.out.println("Внутри: " + getContentsString());
        } else {
            System.out.println(getName() + " пуст.");
        }
    }

    @Override
    public boolean canInteract() {
        return true;
    }

    public void transferAllTo(Inventory target) {
        /*
         * Создаем копию списка, чтобы избежать ConcurrentModificationException
         * Java не позволяет изменять коллекцию во время итерации по ней
         */
        List<InventoryItem> itemsToTransfer = new ArrayList<>(storage.getItems());

        for (InventoryItem item : itemsToTransfer) { // для каждого предмета в списке itemsToTransfer
            if (target.addItem(item)) {
                storage.takeItem(item.name(), item.quantity());
            } else {
                System.out.println("Не удалось перенести " + item.name() + " (нет места).");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Container))
            return false;
        if (!super.equals(o))
            return false;
        Container container = (Container) o;
        return storage.equals(container.storage);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), storage);
    }

    @Override
    public String toString() {
        return "Container{" +
                "name='" + getName() + '\'' +
                ", storage=" + storage +
                '}';
    }
}
