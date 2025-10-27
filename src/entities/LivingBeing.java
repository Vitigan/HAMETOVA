package entities;

import enums.Emotion;
import interfaces.Soundable;
import java.util.Objects;

public abstract class LivingBeing implements Soundable {
    protected String name;
    protected int age;
    protected Emotion emotion;
    protected boolean isAlive;

    public LivingBeing(String name, int age) {
        this.name = name;
        this.age = age;
        this.emotion = Emotion.SAD;
        this.isAlive = true;
    }

    public abstract void move();

    // Getters and setters
    public String getName() { return name; }
    public int getAge() { return age; }
    public Emotion getEmotion() { return emotion; }
    public void setEmotion(Emotion emotion) { this.emotion = emotion; }
    public boolean isAlive() { return isAlive; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LivingBeing that)) return false;
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