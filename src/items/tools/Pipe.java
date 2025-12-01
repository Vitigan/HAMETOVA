package items.tools;

import items.Item;
import enums.Material;
import exceptions.InteractionException;

public class Pipe extends Item {
    private Material material;
    private int quality; // 1-10
    private boolean isSmokable;

    public Pipe(Material material) {
        super("Трубка");
        this.material = material;
        this.quality = (int) (Math.random() * 10) + 1; // 1-10
        this.isSmokable = quality >= 5;
    }

    public void smoke() {
        if (!isSmokable) {
            throw new InteractionException("Эта трубка не пригодна для курения!");
        }

        System.out.println("Робинзон закуривает трубку... Приятный дым!");
    }

    // Геттеры
    public Material getMaterial() {
        return material;
    }

    public int getQuality() {
        return quality;
    }

    public boolean isSmokable() {
        return isSmokable;
    }

}
