package items.transport;

import items.Item;
import exceptions.ItemBrokenException;

public class Boat extends Item {

    private boolean isOnWater;
    private int durability;

    public Boat(String name) {
        super(name, 50.0, enums.Size.LARGE); // Лодка крупная
        this.isOnWater = false;
        this.durability = 100;
    }

    public void sail() throws ItemBrokenException {
        if (durability <= 0) {
            throw new ItemBrokenException("Лодка сломана!");
        }
        isOnWater = true;
        durability -= 10; // лодка изнашивается
        System.out.println(name + " плывёт. Прочность: " + durability + "%");

        if (Math.random() < 0.2) {
            durability = 0;
            throw new ItemBrokenException("Лодка сломалась в море!");
        }
    }

    public void dock() {
        if (isOnWater) {
            isOnWater = false;
            System.out.println(name + " причалила к берегу.");
        } else {
            System.out.println(name + " и так на суше.");
        }
    }

    public void repair() {
        if (durability < 100) {
            durability = 100;
            System.out.println(name + " отремонтирована!");
        }
    }

    // Геттеры
    public boolean isOnWater() {
        return isOnWater;
    }

    public int getDurability() {
        return durability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Boat boat))
            return false;
        return isOnWater == boat.isOnWater &&
                durability == boat.durability &&
                name.equals(boat.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, isOnWater, durability);
    }

    @Override
    public String toString() {
        return "Лодка '" + name + "' (на воде: " + isOnWater + ", прочность: " + durability
                + "%)";
    }

}
