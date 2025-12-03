package items;

import enums.Size;

public abstract class Item {
    protected String name;
    protected double weight;
    protected Size size;

    public Item(String name, double weight, Size size) {
        this.name = name;
        this.weight = weight;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public Size getSize() {
        return size;
    }
}
