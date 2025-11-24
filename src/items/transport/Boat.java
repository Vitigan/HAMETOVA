package items.transport;

import interfaces.Craftable;
import exceptions.ItemBrokenException;

public class Boat implements Craftable {

    private boolean isBuilt;
    private boolean isOnWater;
    private int durability;
    private String name;

    public Boat(String name) {
        this.name = name;
        this.isBuilt = false;
        this.isOnWater = false;
        this.durability = 100;
    }

    @Override
    public void craft() {
        if (isBuilt) {
            throw new exceptions.InteractionException("Лодка уже построена!");
        }
        isBuilt = true;
        System.out.println(name + " построена!");
    }

    @Override
    public boolean isFinished() {
        return isBuilt;
    }

    public void sail() throws ItemBrokenException {
        if (!isBuilt) {
            throw new ItemBrokenException("Сначала построй лодку!");
        }
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
        if (isBuilt && durability < 100) {
            durability = 100;
            System.out.println(name + " отремонтирована!");
        }
    }

    // геттеры получить информацию о лодке
    public boolean isBuilt() {
        return isBuilt;
    }

    public boolean isOnWater() {
        return isOnWater;
    }

    public int getDurability() {
        return durability;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Boat boat))
            return false;
        return isBuilt == boat.isBuilt &&
                isOnWater == boat.isOnWater &&
                durability == boat.durability &&
                name.equals(boat.name);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, isBuilt, isOnWater, durability);
    }

    @Override
    public String toString() {
        return "Лодка '" + name + "' (построена: " + isBuilt + ", на воде: " + isOnWater + ", прочность: " + durability
                + "%)";
    }

}