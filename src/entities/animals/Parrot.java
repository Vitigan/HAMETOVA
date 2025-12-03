package entities.animals;

import entities.LivingBeing;
import entities.human.Robinson;
import enums.Emotion;
import entities.Soundable;
import entities.LivingInteractable;
import entities.Talkable;

public class Parrot extends LivingBeing implements Soundable, LivingInteractable, Talkable {
    private boolean isTame;

    public Parrot(String name, int age, boolean isTame) {
        super(name, age, enums.Size.SMALL);
        this.isTame = isTame;
        this.setEmotion(isTame ? Emotion.HAPPY : Emotion.CALM);
    }

    @Override
    public void move() {
        System.out.println(name + " летит");
    }

    @Override
    public void makeSound() {
        String sound = "";
        switch (getEmotion()) {
            case HAPPY:
                sound = "весело щебечет и насвистывает";
                break;
            case SAD:
                sound = "тихо бормочет";
                break;
            case SCARED:
                sound = "пронзительно кричит";
                break;
            case EXCITED:
                sound = "громко кричит и хлопает крыльями";
                break;
            case CALM:
                sound = "негромко щебечет";
                break;
            default:
                sound = "молчит";
                break;
        }
        System.out.println(name + " " + sound);
    }

    @Override
    public void interactWithLiving(LivingBeing interactor) {
        if (interactor instanceof Robinson) {
            Robinson robinson = (Robinson) interactor;
            if (isTame) {
                System.out.printf("%s радостно садится %s на плечо%n", name, robinson.getName());

                // ДВУСТОРОННЕЕ ВЗАИМОДЕЙСТВИЕ:
                robinson.interactWithLiving(this); // ← Робинзон реагирует на попугая!
            }
        }
    }

    @Override
    public String getDescription() {
        return "Попугай " + name + (isTame ? " (ручной)" : " (дикий)");
    }

    @Override
    public void talk(String message) {
        System.out.println(name + " говорит: \"" + message + "\"");
    }

    @Override
    public void introduce() {
        talk("Я попугай " + name + "!");
    }

    public void flyTo(Robinson human) {
        System.out.println(name + " подлетает к " + human.getName());
    }

    public void sitOnFinger() {
        if (isTame) {
            System.out.println(name + " садится на палец!");
        }
    }

    public boolean isTame() {
        return isTame;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Parrot parrot))
            return false;
        if (!super.equals(o))
            return false;
        return isTame == parrot.isTame;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), isTame);
    }
}
