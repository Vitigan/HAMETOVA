package entities.animals;

import entities.LivingBeing;
import entities.human.Robinson;
import enums.GoatAge;
import interfaces.Soundable;
import interfaces.LivingInteractable;
import enums.Emotion;
import interfaces.HuntTarget;

public class Goat extends LivingBeing implements Soundable, LivingInteractable, HuntTarget {
    private final GoatAge ageType;
    private boolean isTrapped;
    private boolean isTamed; // приручен

    public Goat(String name, int age, GoatAge ageType) {
        super(name, age);
        this.ageType = ageType;
        this.isTrapped = false;
        this.isTamed = false;
        this.setEmotion(Emotion.CALM);
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
        String sound = "";
        switch (ageType) {
            case YOUNG:
                sound = "тонко кричит: 'Мееее!'";
                break;
            case ADULT:
                sound = "громко говорит: 'Бееее!'";
                break;
            case OLD:
                sound = "хрипло мычит: 'Ммууу!'";
                break;
        }

        String emotionEffect = "";
        switch (getEmotion()) {
            case HAPPY:
                emotionEffect = " (радостно)";
                break;
            case SAD:
                emotionEffect = " (грустно)";
                break;
            case SCARED:
                emotionEffect = " (испуганно)";
                break;
            case EXCITED:
                emotionEffect = " (возбужденно)";
                break;
            case CALM:
                emotionEffect = " (спокойно)";
                break;
            default:
                emotionEffect = "";
                break;
        }

        System.out.println(name + " " + sound + emotionEffect);
    }

    @Override
    public void interactWithLiving(LivingBeing interactor) {
        if (interactor instanceof Robinson) {
            Robinson robinson = (Robinson) interactor;
            if (isTrapped) {
                System.out.println(name + " беспомощно смотрит на " + robinson.getName());
                this.setEmotion(Emotion.SCARED);
            } else if (isTamed) {
                System.out.println(name + " радостно бежит к " + robinson.getName());
                this.setEmotion(Emotion.HAPPY);
                makeSound();
            } else {
                System.out.println(name + " убегает от " + robinson.getName());
                this.setEmotion(Emotion.SCARED);
            }
        }
    }

    @Override
    public boolean beHunted() {
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
            isTrapped = true;
            System.out.println("Успешно поймали " + name + "!");
            this.setEmotion(Emotion.SCARED);
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