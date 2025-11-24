package entities.human;

import entities.LivingBeing;
import enums.Emotion;
import interfaces.LivingInteractable;
import interfaces.Talkable;
import interfaces.HoldsItems;
import items.inventory.InventoryStorage;

public class Robinson extends LivingBeing implements LivingInteractable, Talkable, HoldsItems {
    private final InventoryStorage inventory;
    private int powderAmount;

    public Robinson(String name, int age) {
        super(name, age);
        this.inventory = new InventoryStorage(30.0);
        this.powderAmount = 20;
        this.setEmotion(Emotion.CALM);
    }

    @Override
    public void move() {
        System.out.println(name + " идет пешком");
    }

    @Override
    public void makeSound() {
        String sound = "";
        switch (getEmotion()) {
            case HAPPY:
                sound = "напевает веселую мелодию";
                break;
            case SAD:
                sound = "тяжело вздыхает";
                break;
            case SCARED:
                sound = "дрожащим голосом шепчет молитву";
                break;
            case EXCITED:
                sound = "восклицает от удивления";
                break;
            case CALM:
                sound = "спокойно насвистывает";
                break;
            default:
                sound = "молчит";
                break;
        }
        System.out.println(name + " " + sound);
    }

    @Override
    public void talk(String message) {
        System.out.println(name + " говорит: \"" + message + "\"");
    }

    @Override
    public void introduce() {
        talk("Я Робинзон, нахожусь на этом острове уже " + age + " лет");
    }

    @Override
    public void interactWithLiving(LivingBeing interactor) {
        System.out.println(name + " взаимодействует с " + interactor.getName());
    }

    @Override
    public String getDescription() {
        return "Человек по имени " + name;
    }

    @Override
    public InventoryStorage getStorage() {
        return inventory;
    }

    public void checkInventory() {
        System.out.println(name + " проверяет инвентарь:");
        inventory.printContents();
    }

    public int getPowderAmount() {
        return powderAmount;
    }

    public void usePowder() {
        if (powderAmount <= 0) {
            throw new IllegalStateException("Невозможно использовать порох: запасы исчерпаны!");
        }
        powderAmount--;
        System.out.println("Использован порох. Осталось: " + powderAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Robinson robinson))
            return false;
        if (!super.equals(o))
            return false;
        return powderAmount == robinson.powderAmount &&
                inventory.equals(robinson.inventory);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), inventory, powderAmount);
    }
}