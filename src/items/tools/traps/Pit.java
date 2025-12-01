package items.tools.traps;

import items.Item;
import entities.LivingBeing;
import entities.animals.Goat;
import entities.human.Robinson;
import enums.Emotion;
import items.ItemInteractable;
import java.util.Objects;

public class Pit extends Item implements ItemInteractable {
    private boolean isSet;
    private boolean isCovered;
    private LivingBeing capturedAnimal;
    private double catchChance;

    public Pit() {
        super("Волчья яма");
        this.isSet = false;
        this.isCovered = false;
        this.capturedAnimal = null;
        this.catchChance = 0.7;
    }

    public void setTrap() {
        isSet = true;
        isCovered = true;
        System.out.println("Волчья яма замаскирована и готова к работе!");
    }

    @Override
    public void interactWithItem(LivingBeing interactor) {
        if (interactor instanceof Robinson) {
            // Проверка ямы
            if (capturedAnimal != null) {
                // Проверяем, не сбежало ли животное
                if (capturedAnimal instanceof Goat && !((Goat) capturedAnimal).isTrapped()) {
                    System.out.println("Яма сработала, но животное выбралось! Яма пуста.");
                    capturedAnimal = null;
                    isCovered = false;
                } else {
                    System.out.println("В яме поймана: " + capturedAnimal.getName());
                    interactor.setEmotion(Emotion.HAPPY);
                }
            } else {
                System.out.println("Яма пуста");
            }

            if (!isSet) {
                System.out.println("Яма не установлена - нужно замаскировать");
            }
        } else {
            // Срабатывание ловушки
            if (isSet && isCovered && capturedAnimal == null) {
                System.out.println(interactor.getName() + " приближается к замаскированной яме...");

                if (Math.random() < catchChance) {
                    capturedAnimal = interactor;
                    isCovered = false;
                    System.out.println(interactor.getName() + " провалился в волчью яму!");
                    interactor.setEmotion(Emotion.SCARED);

                    if (interactor instanceof Goat) {
                        ((Goat) interactor).getTrapped();
                    }
                } else {
                    System.out.println(interactor.getName() + " обошел яму!");
                }
            } else if (capturedAnimal != null) {
                System.out.println("В яме уже находится " + capturedAnimal.getName());
            }
        }
    }

    @Override
    public String getDescription() {
        String status = isSet ? "установлена" : "не установлена";
        String content = capturedAnimal != null ? " с " + capturedAnimal.getName() : " пустая";
        return "Волчья яма (" + status + ")" + content;
    }

    @Override
    public boolean canInteract() {
        return true;
    }

    public LivingBeing collectAnimal() {
        if (capturedAnimal == null) {
            System.out.println("В яме ничего нет!");
            return null;
        }

        LivingBeing animal = capturedAnimal;
        capturedAnimal = null;
        isCovered = true;
        System.out.println("Забрали " + animal.getName() + " из ямы");
        return animal;
    }

    public void resetTrap() {
        capturedAnimal = null;
        isCovered = true;
        isSet = true;
        System.out.println("Яма перезаряжена и готова к работе!");
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

    public boolean isCovered() {
        return isCovered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Pit))
            return false;
        Pit pit = (Pit) o;
        return isSet == pit.isSet &&
                isCovered == pit.isCovered &&
                Objects.equals(capturedAnimal, pit.capturedAnimal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isSet, isCovered, capturedAnimal);
    }

    @Override
    public String toString() {
        return String.format("Pit{set=%s, covered=%s, captured=%s}",
                isSet, isCovered,
                capturedAnimal != null ? capturedAnimal.getName() : "none");
    }
}
