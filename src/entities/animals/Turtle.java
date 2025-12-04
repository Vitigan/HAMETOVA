package entities.animals;

import entities.LivingBeing;
import entities.human.Robinson;
import enums.Emotion;
import entities.Soundable;
import entities.LivingInteractable;
import enums.Size;
import java.util.Objects;

public class Turtle extends LivingBeing implements Soundable, LivingInteractable {
    private int eggCount;
    private boolean isCaught;

    public Turtle(String name, int age) {
        super(name, age, Size.SMALL);
        this.eggCount = (int) (Math.random() * 3) + 1;
        this.isCaught = false;
        this.setEmotion(Emotion.CALM);
    }

    @Override
    public void move() {
        if (isCaught) {
            throw new IllegalStateException(name + " не может двигаться, так как поймана!");
        }
        System.out.println(name + " медленно ползет по песку");
    }

    @Override
    public void makeSound() {
        System.out.println(name + " шипит" + getEmotionEffect());
    }

    @Override
    public void interactWithLiving(LivingBeing interactor) {
        if (interactor instanceof Robinson) {
            Robinson robinson = (Robinson) interactor;

            if (isCaught) {
                this.setEmotion(Emotion.SCARED);
                System.out.println(name + getEmotionEffect() + " смотрит на " + robinson.getName());
                makeSound();
            } else {
                this.setEmotion(Emotion.SCARED);
                System.out.println(name + getEmotionEffect() + " пытается уползти от " + robinson.getName());

                if (Math.random() < 0.7) {
                    System.out.println(name + " прячется в панцирь!");
                }
            }
        }
    }

    @Override
    public String getDescription() {
        String state = isCaught ? "поймана" : "свободна";
        return "Черепаха " + name + " (" + state + ", яиц: " + eggCount + ")";
    }

    public void getCaught() {
        if (!isCaught) {
            isCaught = true;
            System.out.println(name + " поймана!");
            this.setEmotion(Emotion.SCARED);
            makeSound();
        }
    }

    public int collectEggs() {
        if (!isCaught) {
            throw new IllegalStateException("Сначала нужно поймать черепаху!");
        }

        int collectedEggs = eggCount;
        if (eggCount > 0) {
            System.out.println("Собрано " + eggCount + " яиц черепахи");
            eggCount = 0;
        }
        return collectedEggs;
    }

    public void escape() {
        if (isCaught) {
            if (Math.random() < 0.2) { // 20% шанс побега
                isCaught = false;
                System.out.println(name + " сбежала!");
                this.setEmotion(Emotion.HAPPY);
            } else {
                System.out.println(name + " не смогла сбежать");
            }
        }
    }

    public boolean hasMeat() {
        return isCaught && isAlive;
    }

    public int getEggCount() {
        return eggCount;
    }

    public boolean isCaught() {
        return isCaught;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Turtle turtle))
            return false;
        if (!super.equals(o))
            return false;
        return eggCount == turtle.eggCount &&
                isCaught == turtle.isCaught;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), eggCount, isCaught);
    }

    @Override
    public String toString() {
        return String.format("Turtle{name='%s', eggs=%d, caught=%s, emotion=%s}",
                name, eggCount, isCaught, getEmotion());
    }
}
