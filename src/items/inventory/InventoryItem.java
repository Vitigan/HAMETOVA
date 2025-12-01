package items.inventory;

public record InventoryItem(String name, int quantity, double weightPerUnit) {
    public double getTotalWeight() {
        return quantity * weightPerUnit;
    }

    public InventoryItem withQuantity(int newQuantity) {
        return new InventoryItem(name, newQuantity, weightPerUnit);
    }

    @Override
    public String toString() {
        return String.format("%s: %d шт (%.1f кг)", name, quantity, getTotalWeight());
    }
}
