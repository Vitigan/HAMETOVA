package items.containers;

import entities.LivingBeing;
import entities.human.Robinson;
import exceptions.InteractionException;

public class Basket extends Container {
    private String style;

    public Basket() {
        this("простая", 15.0);
    }

    public Basket(String style, double maxWeight) {
        super("Корзина", 1.0, enums.Size.MEDIUM, maxWeight, enums.Size.SMALL);
        this.style = style;
    }

    public Basket(int maxWeight, String style) {
        this(style, (double) maxWeight);
    }

    @Override
    public void interactWithItem(LivingBeing interactor) {
        System.out.println(interactor.getName() + " взаимодействует с " + style + " корзиной");

        // Просто показываем содержимое через InventoryStorage
        storage.printContents();

        if (interactor instanceof Robinson) {
            Robinson robinson = (Robinson) interactor;
            if (storage.isEmpty()) {
                throw new InteractionException("Корзина пуста - нечего забирать!");
            }
            robinson.talk("Заберу предметы в свой инвентарь!");
            transferAllTo(robinson.getStorage());
        }
    }

    @Override
    public String getDescription() {
        return style + " корзина (" + storage.getCurrentWeight() + "/" + storage.getMaxWeight() + " кг)";
    }

    public void carry() {
        System.out.println("Несу корзину " + style);
    }

    public String getStyle() {
        return style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Basket))
            return false;
        if (!super.equals(o))
            return false;
        Basket basket = (Basket) o;
        return java.util.Objects.equals(style, basket.style);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), style);
    }

    @Override
    public String toString() {
        return "Basket{" +
                "style='" + style + '\'' +
                ", " + super.toString() +
                '}';
    }
}
