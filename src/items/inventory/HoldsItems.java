package items.inventory;

public interface HoldsItems {
    InventoryStorage getStorage();

    default boolean put(InventoryItem item) {
        boolean result = getStorage().addItem(item);
        if (result) {
            System.out.println("Положил " + item.name() + " в хранилище.");
        } else {
            System.out.println("Не удалось положить " + item.name() + " (нет места).");
        }
        return result;
    }

    default InventoryItem take(String name, int quantity) {
        return getStorage().takeItem(name, quantity);
    }
}
