package items.tools.traps;

import entities.LivingBeing;
import entities.animals.Goat;
import entities.human.Robinson;
import enums.Emotion;
import interfaces.Craftable;
import interfaces.ItemInteractable;
import java.util.Objects;

public class Pit implements Craftable, ItemInteractable {
    private boolean isCrafted;
    private boolean isSet;
    private boolean isCovered;
    private LivingBeing capturedAnimal;
    private double catchChance;

    public Pit() {
        this.isCrafted = false;
        this.isSet = false;
        this.isCovered = false;
        this.capturedAnimal = null;
        this.catchChance = 0.7;
    }

    @Override
    public void craft() {
        if (isCrafted) {
            throw new exceptions.InteractionException("Волчья яма уже выкопана!");
        }
        isCrafted = true;
        System.out.println("Выкопана волчья яма!");
    }

    @Override
    public boolean isFinished() {
        return isCrafted;
    }

    public void setTrap() {
        if (!isCrafted) {
            throw new exceptions.InteractionException("Сначала нужно выкопать яму!");
        }
        isSet = true;
        isCovered = true;
        System.out.println("Волчья яма замаскирована и готова к работе!");
    }

    // ItemInteractable implementation
    @Override
    public void interactWithItem(LivingBeing interactor) {
        // Животное проходит мимо ямы
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

    @Override
    public String getDescription() {
        String status = isSet ? "установлена" : "не установлена";
        String content = capturedAnimal != null ? " с " + capturedAnimal.getName() : " пустая";
        return "Волчья яма (" + status + ")" + content;
    }

    @Override
    public boolean canInteract() {
        return isCrafted;
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
        if (isCrafted) {
            capturedAnimal = null;
            isCovered = true;
            isSet = true;
            System.out.println("Яма перезаряжена и готова к работе!");
        }
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
        return isCrafted == pit.isCrafted &&
                isSet == pit.isSet &&
                isCovered == pit.isCovered &&
                Objects.equals(capturedAnimal, pit.capturedAnimal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isCrafted, isSet, isCovered, capturedAnimal);
    }

    @Override
    public String toString() {
        return String.format("Pit{crafted=%s, set=%s, covered=%s, captured=%s}",
                isCrafted, isSet, isCovered,
                capturedAnimal != null ? capturedAnimal.getName() : "none");
    }
}