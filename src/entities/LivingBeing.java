package entities;

import enums.Emotion;
import enums.Size;
import java.util.Objects;

public abstract class LivingBeing {
    protected String name;
    protected int age;
    protected Emotion emotion;
    protected boolean isAlive;
    protected Size size;

    public LivingBeing(String name, int age, Size size) {
        this.name = name;
        this.age = age;
        this.size = size;
        this.emotion = Emotion.SAD;
        this.isAlive = true;
    }

    public abstract void move();

    public abstract void makeSound();

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
    }

    public String getEmotionEffect() {
        switch (emotion) {
            case HAPPY:
                return " (радостно)";
            case SAD:
                return " (грустно)";
            case SCARED:
                return " (испуганно)";
            case EXCITED:
                return " (возбужденно)";
            case CALM:
                return " (спокойно)";
            default:
                return "";
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public Size getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof LivingBeing that))
            return false;
        return age == that.age &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return String.format("%s{name='%s', age=%d, emotion=%s}",
                getClass().getSimpleName(), name, age, emotion);
    }
}
