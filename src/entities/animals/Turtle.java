package entities.animals;

import entities.LivingBeing;
import entities.human.Robinson;
import enums.Emotion;
import interfaces.Soundable;
import interfaces.LivingInteractable;
import java.util.Objects;

public class Turtle extends LivingBeing implements Soundable, LivingInteractable {
    private int eggCount;
    private boolean isCaught;

    public Turtle(String name, int age) {
        super(name, age);
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
        String sound = "";
        switch (getEmotion()) {
            case SCARED:
                sound = "испуганно шипит";
                break;
            case HAPPY:
                sound = "тихо урчит";
                break;
            case SAD:
                sound = "издает грустный свист";
                break;
            default:
                sound = "медленно дышит";
                break;
        }
        System.out.println(name + " " + sound);
    }

    @Override
    public void interactWithLiving(LivingBeing interactor) {
        if (interactor instanceof Robinson) {
            Robinson robinson = (Robinson) interactor;

            if (isCaught) {
                System.out.println(name + " беспомощно смотрит на " + robinson.getName());
                this.setEmotion(Emotion.SCARED);
                makeSound();

                if (eggCount > 0) {
                    System.out.println(robinson.getName() + " забирает яйца у черепахи");
                    int collected = collectEggs();
                    robinson.talk("Собрал " + collected + " яиц черепахи!");
                }
            } else {
                System.out.println(name + " пытается уползти от " + robinson.getName());
                this.setEmotion(Emotion.SCARED);

                if (Math.random() < 0.7) {
                    System.out.println(name + " прячется в панцирь!");
                }

                if (Math.random() < 0.6) {
                    getCaught();
                    robinson.talk("Поймал черепаху!");
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
            System.out.println("Сначала нужно поймать черепаху!");
            return 0;
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