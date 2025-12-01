package items.tools.traps;

import items.Item;
import entities.LivingBeing;
import entities.animals.Goat;
import entities.human.Robinson;
import enums.Emotion;
import items.ItemInteractable;
import java.util.Objects;

public class Snare extends Item implements ItemInteractable {
    private boolean isSet;
    private LivingBeing capturedAnimal;
    private double catchChance;
    private String material;
    private int durability;

    public Snare(String material) {
        super("Силки");
        this.isSet = false;
        this.capturedAnimal = null;
        this.material = material;
        this.durability = 100;

        // Шанс поимки зависит от материала
        if (material.equals("проволока")) {
            this.catchChance = 0.8;
        } else if (material.equals("бечевка")) {
            this.catchChance = 0.4;
        } else {
            this.catchChance = 0.6;
        }
    }

    public void setTrap() {
        isSet = true;
        System.out.println("Силки установлены в месте кормежки коз!");
    }

    @Override
    public void interactWithItem(LivingBeing interactor) {
        if (interactor instanceof Robinson) {
            // Проверка ловушки
            if (capturedAnimal != null) {
                // Проверяем, не сбежало ли животное
                if (capturedAnimal instanceof Goat && !((Goat) capturedAnimal).isTrapped()) {
                    System.out.println("Похоже, добыча вырвалась и убежала! Силки пусты.");
                    capturedAnimal = null;
                    isSet = false;
                } else {
                    System.out.println("В силках поймана: " + capturedAnimal.getName());
                    interactor.setEmotion(Emotion.HAPPY);
                }
            } else {
                System.out.println("Силки пусты");
            }

            if (!isSet) {
                System.out.println("Силки не установлены");
            }
        } else {
            // Срабатывание ловушки
            if (isSet && capturedAnimal == null) {
                System.out.println(interactor.getName() + " приближается к силкам...");

                if (Math.random() < catchChance) {
                    capturedAnimal = interactor;
                    System.out.println(interactor.getName() + " попал в силки!");
                    interactor.setEmotion(Emotion.SCARED);

                    if (interactor instanceof Goat) {
                        ((Goat) interactor).getTrapped();
                    }

                    // Износ при поимке
                    durability -= 20;
                } else {
                    System.out.println(interactor.getName() + " избежал силок!");

                    // Проверка на разрыв
                    if (material.equals("бечевка") && Math.random() < 0.6) {
                        System.out.println("Силки из бечевки порвались!");
                        isSet = false;
                        durability = 0;
                    }
                }

                if (durability <= 0) {
                    System.out.println("Силки полностью износились!");
                    isSet = false;
                }
            } else if (capturedAnimal != null) {
                System.out.println("В силках уже поймана " + capturedAnimal.getName());
            }
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
        return true;
    }

    public LivingBeing collectAnimal() {
        if (capturedAnimal == null) {
            System.out.println("В силках ничего нет!");
            return null;
        }

        LivingBeing animal = capturedAnimal;
        capturedAnimal = null;

        // Риск разрыва бечевки
        if (material.equals("бечевка") && Math.random() < 0.3) {
            System.out.println("Силки порвались при извлечении добычи!");
            isSet = false;
            durability = 0;
        }

        return animal;
    }

    public void repair() {
        if (durability < 100) {
            durability = 100;
            System.out.println("Силки отремонтированы! Прочность восстановлена.");
        }
    }

    public void resetTrap() {
        if (!isBroken()) {
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
        return isSet == snare.isSet &&
                Objects.equals(material, snare.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSet, material);
    }

    @Override
    public String toString() {
        return String.format("Snare{material='%s', set=%s, durability=%d, captured=%s}",
                material, isSet, durability,
                capturedAnimal != null ? capturedAnimal.getName() : "none");
    }
}
