package entities;

public record InventoryItem(String name, int quantity, double weight) {
    @Override
    public String toString() {
        return String.format("%s (количество: %d, вес: %.1f кг)", name, quantity, weight);
    }
}