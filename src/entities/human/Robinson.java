package entities.human;

import entities.LivingBeing;
import entities.animals.Goat;
import entities.animals.Parrot;
import entities.animals.Turtle;
import enums.Emotion;
import entities.LivingInteractable;
import entities.Talkable;
import items.Item;
import exceptions.InteractionException;
import items.inventory.Inventory;
import entities.HuntTarget;
import items.tools.Weapon;
import exceptions.ItemBrokenException;

public class Robinson extends LivingBeing implements LivingInteractable, Talkable {
    private final Inventory inventory;
    private int powderAmount;

    private static final double DEFAULT_INVENTORY_SIZE = 30.0;
    private static final int INITIAL_POWDER = 20;

    public Robinson(String name, int age) {
        super(name, age, enums.Size.MEDIUM); // Робинзон среднего размера
        this.inventory = new Inventory(DEFAULT_INVENTORY_SIZE);
        this.powderAmount = INITIAL_POWDER;
        this.setEmotion(Emotion.CALM);
    }

    @Override
    public void move() {
        System.out.println(name + " идет пешком");
    }

    @Override
    public void makeSound() {
        System.out.println(name + " говорит" + getEmotionEffect());
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
    public void interactWithLiving(LivingBeing target) {
        System.out.println(name + " подходит к " + target.getName());

        if (target instanceof Turtle) {
            Turtle turtle = (Turtle) target;
            if (turtle.isCaught()) {
                if (turtle.getEggCount() > 0) {
                    System.out.println(name + " проверяет черепаху на наличие яиц.");
                    int eggs = turtle.collectEggs();
                    talk("Ого! Нашел " + eggs + " яиц!");
                    this.setEmotion(Emotion.HAPPY);
                } else {
                    System.out.println(name + " видит, что у черепахи нет яиц.");
                }
            } else {
                System.out.println(name + " пытается поймать черепаху.");
                if (Math.random() < 0.6) {
                    turtle.getCaught();
                    talk("Попалась, красавица!");
                    this.setEmotion(Emotion.HAPPY);
                } else {
                    System.out.println("Черепаха оказалась быстрее.");
                    this.setEmotion(Emotion.SAD);
                }
            }
            // Вызываем реакцию черепахи на взаимодействие
            turtle.interactWithLiving(this);
        } else if (target instanceof Goat) {
            Goat goat = (Goat) target;
            if (goat.isTamed()) {
                System.out.println(name + " ласково гладит козу.");
                this.setEmotion(Emotion.HAPPY);
            } else if (goat.isTrapped()) {
                System.out.println(name + " осматривает пойманную козу.");
                this.setEmotion(Emotion.CALM);
            } else {
                System.out.println(name + " пытается подозвать козу.");
                talk("Коза-коза, иди сюда!");
            }
            goat.interactWithLiving(this);
        } else if (target instanceof Parrot) {
            Parrot parrot = (Parrot) target;
            if (parrot.isTame()) {
                System.out.println(name + " играет с попугаем.");
                this.setEmotion(Emotion.HAPPY);
            } else {
                System.out.println(name + " пытается приманить попугая.");
                talk("Попка-дурак, хочешь сухарик?");
            }
            parrot.interactWithLiving(this);
        } else {
            System.out.println(name + " взаимодействует с " + target.getName());
            if (target instanceof LivingInteractable) {
                ((LivingInteractable) target).interactWithLiving(this);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Человек по имени " + name;
    }

    public Inventory getStorage() {
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
