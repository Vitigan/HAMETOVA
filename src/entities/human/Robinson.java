package entities.human;

import entities.LivingBeing;
import enums.Emotion;
import entities.LivingInteractable;
import entities.Talkable;
import items.Item;
import exceptions.InteractionException;
import items.inventory.HoldsItems;
import items.inventory.InventoryStorage;
import entities.HuntTarget;
import items.tools.Weapon;
import exceptions.ItemBrokenException;

public class Robinson extends LivingBeing implements LivingInteractable, Talkable, HoldsItems {
    private final InventoryStorage inventory;
    private int powderAmount;

    private static final double DEFAULT_INVENTORY_SIZE = 30.0;
    private static final int INITIAL_POWDER = 20;

    public Robinson(String name, int age) {
        super(name, age, enums.Size.MEDIUM); // Робинзон среднего размера
        this.inventory = new InventoryStorage(DEFAULT_INVENTORY_SIZE);
        this.powderAmount = INITIAL_POWDER;
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

    public void hunt(HuntTarget target, Weapon weapon) {
        String targetName = (target instanceof LivingBeing) ? ((LivingBeing) target).getName() : "цель";
        System.out.println(name + " охотится на " + targetName + " с помощью " + weapon.getName());

        try {
            if (weapon.use()) {
                if (target.beHunted()) {
                    System.out.println("Охота удалась! " + targetName + " добыта.");
                    setEmotion(Emotion.HAPPY);
                } else {
                    System.out.println(targetName + " убежала!");
                    setEmotion(Emotion.SAD);
                }
            } else {
                System.out.println("Промах!");
                setEmotion(Emotion.SAD);
            }
        } catch (ItemBrokenException e) {
            System.out.println("Охота сорвалась: " + e.getMessage());
            setEmotion(Emotion.SAD);
        }
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
