package items;

import items.tools.Pipe;
import items.tools.Weapon;
import items.tools.traps.Pit;
import items.tools.traps.Snare;
import items.transport.Boat;
import items.containers.Basket;
import items.containers.Backpack;
import enums.Material;
import enums.WeaponType;

public class CraftingFactory {

    public static Boat createBoat(String name) {
        Boat boat = new Boat(name);
        System.out.println(name + " построена!");
        return boat;
    }

    public static Weapon createWeapon(WeaponType type) {
        Weapon weapon = new Weapon(type);
        System.out.println(type + " создано! Эффективность: " + weapon.getEffectiveness() + "%");
        return weapon;
    }

    public static Pipe createPipe(Material material) {
        Pipe pipe = new Pipe(material);
        System.out.println("Создана трубка из " + material + " (качество: " + pipe.getQuality() + ")");
        if (pipe.isSmokable()) {
            System.out.println("Ура! Трубка пригодна для курения!");
        } else {
            System.out.println("Увы, трубка не пригодна для курения...");
        }
        return pipe;
    }

    public static Snare createSnare(String material) {
        Snare snare = new Snare(material);
        System.out.println("Созданы силки из " + material + "!");
        return snare;
    }

    public static Pit createPit() {
        Pit pit = new Pit();
        System.out.println("Выкопана волчья яма!");
        return pit;
    }

    public static Basket createBasket(int maxWeight, String style) {
        Basket basket = new Basket(maxWeight, style);
        System.out.println("Сплетена " + style + " корзина!");
        return basket;
    }

    public static Backpack createBackpack(int capacity, String material) {
        Backpack backpack = new Backpack(capacity, material);
        System.out.println("Создан " + material + " рюкзак!");
        return backpack;
    }
}
