package entities;

import interfaces.Interactable;
import interfaces.Talkable;

public class Parrot extends LivingBeing implements Interactable, Talkable {
    private boolean isTame; //ручной

    public Parrot(String name, int age, boolean isTame) {
        super(name, age);
        this.isTame = isTame;
    }

    @Override
    public void move() {
        System.out.println(name + " летит");
    }

    @Override
    public void makeSound() {
        System.out.println(name + " щебечет");
    }

    @Override
    public void interact() {
        System.out.println(name + " взаимодействует");
    }

    @Override
    public String getDescription() {
        return "Попугай " + name + (isTame ? " (ручной)" : " (дикий)");  //условие ? значение_если_истина : значение_если_ложь
    }

    public void talk(String message) {
        System.out.println(name + " говорит: \"" + message + "\"");
    }

    public void flyTo(Human human) {
        System.out.println(name + " подлетает к " + human.getName());
    }

    public void sitOnFinger() {
        if (isTame) {
            System.out.println(name + " садится на палец!");
        } else {
            System.out.println(name + " дикий и не садится на палец!");
        }
    }

    public boolean isTame() { return isTame; }
}