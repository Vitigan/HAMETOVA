package entities;

import enums.Emotion;
import interfaces.Interactable;

public class Human extends LivingBeing implements Interactable {
    public Human(String name, int age) {
        super(name, age);
    }

    @Override
    public void move() {
        System.out.println(name + " идет пешком");
    }

    @Override
    public void makeSound() {
        System.out.println(name + " говорит что-то");
    }

    @Override
    public void interact() {
        System.out.println(name + " взаимодействует с окружающим миром");
    }

    @Override
    public String getDescription() {
        return "Человек по имени " + name;
    }

    public void feelHappy() {
        this.setEmotion(Emotion.HAPPY);
        System.out.println(name + " теперь счастлив!");
    }

    public void introduce() {
        System.out.println("Привет, меня зовут " + name);
    }
}