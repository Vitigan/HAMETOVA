package items.tools;

import interfaces.Craftable;
import enums.Material;

public class Pipe implements Craftable {
    private boolean isCrafted;
    private Material material;
    private int quality; // 1-10
    private boolean isSmokable;

    public Pipe(Material material) {
        this.material = material;
        this.isCrafted = false;
        this.quality = 0;
        this.isSmokable = false;
    }

    @Override
    public void craft() {
        if (isCrafted) {
            throw new exceptions.InteractionException("Трубка уже создана!");
        }
        isCrafted = true;

        this.quality = (int) (Math.random() * 10) + 1; // 1-10

        // УСЛОВИЕ: трубка пригодна для курения если качество >= 5
        this.isSmokable = quality >= 5;

        System.out.println("Создана трубка из " + material +
                " (качество: " + quality + ")");

        if (isSmokable) {
            System.out.println("Ура! Трубка пригодна для курения!");
        } else {
            System.out.println("Увы, трубка не пригодна для курения...");
        }
    }

    @Override
    public boolean isFinished() {
        return isCrafted;
    }

    public void smoke() {
        if (!isCrafted) {
            throw new exceptions.InteractionException("Сначала создай трубку!");
        }
        if (!isSmokable) {
            throw new exceptions.InteractionException("Эта трубка не пригодна для курения!");
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