package entities.animals;

import entities.LivingBeing;
import entities.human.Robinson;
import enums.GoatAge;
import entities.Soundable;
import entities.LivingInteractable;
import enums.Emotion;
import entities.HuntTarget;

public class Goat extends LivingBeing implements Soundable, LivingInteractable, HuntTarget {
    private final GoatAge ageType;
    private boolean isTrapped;
    private boolean isTamed; // приручен

    public Goat(String name, int age) {
        super(name, age, determineSize(age));
        this.ageType = determineAgeType(age);
        this.isTrapped = false;
        this.isTamed = false;
        this.setEmotion(Emotion.CALM);
    }

    private static GoatAge determineAgeType(int age) {
        if (age <= 1)
            return GoatAge.YOUNG;
        if (age <= 7)
            return GoatAge.ADULT;
        return GoatAge.OLD;
    }

    private static enums.Size determineSize(int age) {
        return age <= 1 ? enums.Size.SMALL : enums.Size.MEDIUM;
    }

    @Override
    public void move() {
        if (isTrapped) {
            throw new IllegalStateException(name + " не может двигаться, так как находится в ловушке!");
        }
        System.out.println(name + " бежит по полю");
    }

    @Override
    public void makeSound() {
        System.out.println(name + " мекает" + getEmotionEffect());
    }

    @Override
    public void interactWithLiving(LivingBeing interactor) {
        if (interactor instanceof Robinson) {
            Robinson robinson = (Robinson) interactor;
            if (isTrapped) {
                this.setEmotion(Emotion.SCARED);
                System.out.println(name + getEmotionEffect() + " смотрит на " + robinson.getName());
            } else if (isTamed) {
                this.setEmotion(Emotion.HAPPY);
                System.out.println(name + getEmotionEffect() + " бежит к " + robinson.getName());
                makeSound();
            } else {
                this.setEmotion(Emotion.SCARED);
                System.out.println(name + getEmotionEffect() + " убегает от " + robinson.getName());
            }
        }
    }

    @Override
    public boolean beHunted() {
        if (!isAlive()) {
            System.out.println(name + " уже мертва!");
            return false;
        }

        if (isTrapped) {
            throw new IllegalStateException(name + " уже поймана! Нельзя охотиться на пойманное животное.");
        }

        // Шанс поймать зависит от возраста козы
        double catchChance = 0.0;
        switch (ageType) {
            case YOUNG:
                catchChance = 0.8; // Молодые легче ловятся
                break;
            case ADULT:
                catchChance = 0.5;
                break;
            case OLD:
                catchChance = 0.3; // Старые осторожнее
                break;
        }

        if (Math.random() < catchChance) {
            isAlive = false;
            System.out.println("Успешно поймали (и убили) " + name + "!");
            return true;
        } else {
            System.out.println(name + " убежала!");
            return false;
        }
    }

    public void escape() {
        if (isTrapped) {
            // Шанс побега зависит от возраста
            double escapeChance = 0.0;
            switch (ageType) {
                case YOUNG:
                    escapeChance = 0.4; // Молодые сильнее
                    break;
                case ADULT:
                    escapeChance = 0.3;
                    break;
                case OLD:
                    escapeChance = 0.1; // Старые слабее
                    break;
            }

            if (Math.random() < escapeChance) {
                isTrapped = false;
                System.out.println(name + " сбежала из ловушки!");
                this.setEmotion(Emotion.HAPPY);
            } else {
                System.out.println(name + " не смогла сбежать");
            }
        }
    }

    public void getTrapped() {
        isTrapped = true;
        this.setEmotion(Emotion.SCARED);
        System.out.println(name + " попала в ловушку!");
    }

    @Override
    public String getDescription() {
        String desc = "";
        switch (ageType) {
            case YOUNG:
                desc = "молодая козочка";
                break;
            case ADULT:
                desc = "взрослая коза";
                break;
            case OLD:
                desc = "старая коза";
                break;
        }
        return desc;
    }

    public boolean isTrapped() {
        return isTrapped;
    }

    public boolean isTamed() {
        return isTamed;
    }

    public void eat() {
        System.out.println(name + " щиплет травку.");
    }

    public GoatAge getType() {
        return ageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Goat goat))
            return false;
        if (!super.equals(o))
            return false;
        return isTrapped == goat.isTrapped &&
                isTamed == goat.isTamed &&
                ageType == goat.ageType;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), ageType, isTrapped, isTamed);
    }
}
