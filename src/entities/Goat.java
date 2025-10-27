package entities;

import enums.GoatType;

public class Goat extends LivingBeing {
    private GoatType type;
    private boolean isTrapped;

    public Goat(String name, int age, GoatType type) {
        super(name, age);
        this.type = type;
        this.isTrapped = false;
    }

    @Override
    public void move() {
        if (!isTrapped) {
            System.out.println(name + " бежит по полю");
        } else {
            System.out.println(name + " пытается вырваться из ловушки");
        }
    }

    @Override
    public void makeSound() {
        System.out.println(name + " говорит: 'Беееее!'");
    }

    public void eat() {
        System.out.println(name + " ест траву");
    }

    public void getTrapped() {
        isTrapped = true;
        System.out.println(name + " попала в ловушку!");
    }

    public void escape() {
        if (isTrapped) {
            if (Math.random() < 0.3) {
                isTrapped = false;
                System.out.println(name + " сбежала из ловушки!");
            } else {
                System.out.println(name + " не смогла сбежать");
            }
        }
    }

    public GoatType getType() { return type; }
    public boolean isTrapped() { return isTrapped; }
}