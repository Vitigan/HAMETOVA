package items.tools.traps;

import entities.LivingBeing;
import entities.animals.Goat;
import entities.human.Robinson;
import enums.Emotion;
import interfaces.Craftable;
import interfaces.ItemInteractable;
import java.util.Objects;

public class Snare implements Craftable, ItemInteractable {
    private boolean isCrafted;
    private boolean isSet;
    private LivingBeing capturedAnimal;
    private double catchChance;
    private String material;
    private int durability;

    public Snare(String material) {
        this.isCrafted = false;
        this.isSet = false;
        this.capturedAnimal = null;
        this.material = material;
        this.durability = 100;

        // Шанс поимки и прочность зависят от материала
        if (material.equals("проволока")) {
            this.catchChance = 0.8;
        } else if (material.equals("бечевка")) {
            this.catchChance = 0.4;
        } else {
            this.catchChance = 0.6; // для других материалов
        }
    }

    @Override
    public void craft() {
        if (isCrafted) {
            throw new exceptions.InteractionException("Силки уже созданы!");
        }
        isCrafted = true;
        System.out.println("Созданы силки из " + material + "!");
    }

    @Override
    public boolean isFinished() {
        return isCrafted;
    }

    public void setTrap() {
        if (!isCrafted) {
            throw new exceptions.InteractionException("Сначала нужно создать силки!");
        }
        isSet = true;
        System.out.println("Силки установлены в месте кормежки коз!");
    }

    // ItemInteractable implementation
    @Override
    public void interactWithItem(LivingBeing interactor) {
        // Животное взаимодействует с силками
        if (isSet && capturedAnimal == null) {
            System.out.println(interactor.getName() + " приближается к силкам...");

            if (Math.random() < catchChance) {
                capturedAnimal = interactor;
                System.out.println(interactor.getName() + " попал в силки!");
                interactor.setEmotion(Emotion.SCARED);

                if (interactor instanceof Goat) {
                    ((Goat) interactor).getTrapped();
                }

                // Силки изнашиваются при поимке
                durability -= 20;
            } else {
                System.out.println(interactor.getName() + " избежал силок!");

                // Проверяем, не порвались ли силки (как в тексте)
                if (material.equals("бечевка") && Math.random() < 0.6) {
                    System.out.println("Силки из бечевки порвались!");
                    isSet = false;
                    durability = 0;
                }
            }

            // Проверяем износ
            if (durability <= 0) {
                System.out.println("Силки полностью износились!");
                isSet = false;
            }
        } else if (capturedAnimal != null) {
            System.out.println("В силках уже поймана " + capturedAnimal.getName());
        }
    }

    @Override
    public String getDescription() {
        String status = isSet ? "установлены" : "не установлены";
        String content = capturedAnimal != null ? " с " + capturedAnimal.getName() : " пустые";
        return "Силки из " + material + " (" + status + ")" + content +
                " (прочность: " + durability + "%)";
    }

    @Override
    public boolean canInteract() {
        return isCrafted;
    }

    public LivingBeing collectAnimal() {
        if (capturedAnimal == null) {
            System.out.println("В силках ничего нет!");
            return null;
        }

        LivingBeing animal = capturedAnimal;
        capturedAnimal = null;

        // Если силки из бечевки, они могли порваться при извлечении
        if (material.equals("бечевка") && Math.random() < 0.3) {
            System.out.println("Силки порвались при извлечении добычи!");
            isSet = false;
            durability = 0;
        }

        return animal;
    }

    public void repair() {
        if (isCrafted && durability < 100) {
            durability = 100;
            System.out.println("Силки отремонтированы! Прочность восстановлена.");
        }
    }

    public void resetTrap() {
        if (isCrafted && !isBroken()) {
            capturedAnimal = null;
            isSet = true;
            System.out.println("Силки переустановлены!");
        }
    }

    public boolean isBroken() {
        return durability <= 0;
    }

    // Геттеры
    public boolean isSet() {
        return isSet;
    }

    public boolean isOccupied() {
        return capturedAnimal != null;
    }

    public LivingBeing getCapturedAnimal() {
        return capturedAnimal;
    }

    public double getCatchChance() {
        return catchChance;
    }

    public String getMaterial() {
        return material;
    }

    public int getDurability() {
        return durability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Snare))
            return false;
        Snare snare = (Snare) o;
        return isCrafted == snare.isCrafted &&
                isSet == snare.isSet &&
                Objects.equals(material, snare.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isCrafted, isSet, material);
    }

    @Override
    public String toString() {
        return String.format("Snare{material='%s', crafted=%s, set=%s, durability=%d, captured=%s}",
                material, isCrafted, isSet, durability,
                capturedAnimal != null ? capturedAnimal.getName() : "none");
    }
}