package items.inventory;

import items.Item;
import enums.Size;

public record InventoryItem(String name, int quantity, double weightPerUnit, Size size, Item itemReference) {

    public InventoryItem(String name, int quantity, double weightPerUnit) {
        this(name, quantity, weightPerUnit, Size.SMALL, null); // По умолчанию мелкий
    }

    public double getTotalWeight() {
        return quantity * weightPerUnit;
    }

    public InventoryItem withQuantity(int newQuantity) {
        return new InventoryItem(name, newQuantity, weightPerUnit, size, itemReference);
    }

    @Override
    public String toString() {
        return String.format("%s: %d шт (%.1f кг)", name, quantity, getTotalWeight());
    }
}
