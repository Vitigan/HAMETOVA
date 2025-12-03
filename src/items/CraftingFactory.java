package items;

import items.tools.Pipe;
import items.tools.Weapon;
import items.tools.Bow;
import items.tools.traps.Pit;
import items.tools.traps.Snare;
import items.transport.Boat;
import items.containers.Basket;
import items.containers.Backpack;
import enums.Material;
import enums.WeaponType;
import items.inventory.InventoryStorage;
import items.inventory.InventoryItem;

public class CraftingFactory {

    public static Boat createBoat(InventoryStorage storage, String name) {
        checkAndConsume(storage, "Дерево", 5);
        checkAndConsume(storage, "Гвозди", 2);
        Boat boat = new Boat(name);
        System.out.println(name + " построена! (Потрачено: 5 Дерева, 2 Гвоздя)");
        return boat;
    }

    public static Weapon createWeapon(InventoryStorage storage, WeaponType type) {
        if (type == WeaponType.BOW) {
            checkAndConsume(storage, "Дерево", 2);
            checkAndConsume(storage, "Веревка", 1);
        } else if (type == WeaponType.GUN) {
            checkAndConsume(storage, "Железо", 3);
            checkAndConsume(storage, "Порох", 1);
        }

        Weapon weapon;
        if (type == WeaponType.BOW) {
            weapon = new Bow();
        } else {
            weapon = new Weapon(type);
        }
        System.out.println(type + " создано! Эффективность: " + weapon.getEffectiveness() + "%");
        return weapon;
    }

    public static Pipe createPipe(InventoryStorage storage, Material material) {
        checkAndConsume(storage, "Глина", 1);
        Pipe pipe = new Pipe(material);
        System.out.println("Создана трубка из " + material + " (качество: " + pipe.getQuality() + ")");
        if (pipe.isSmokable()) {
            System.out.println("Ура! Трубка пригодна для курения!");
        } else {
            System.out.println("Увы, трубка не пригодна для курения...");
        }
        return pipe;
    }

    public static Snare createSnare(InventoryStorage storage, String material) {
        checkAndConsume(storage, "Веревка", 2);
        Snare snare = new Snare(material);
        System.out.println("Созданы силки из " + material + "!");
        return snare;
    }

    public static Pit createPit(InventoryStorage storage) {
        // Яма не требует ресурсов, только сил
        Pit pit = new Pit();
        System.out.println("Выкопана волчья яма!");
        return pit;
    }

    public static Basket createBasket(InventoryStorage storage, int maxWeight, String style) {
        checkAndConsume(storage, "Прутья", 5);
        Basket basket = new Basket(maxWeight, style);
        System.out.println("Сплетена " + style + " корзина!");
        return basket;
    }

    public static Backpack createBackpack(InventoryStorage storage, int capacity, String material) {
        checkAndConsume(storage, "Кожа", 3);
        checkAndConsume(storage, "Нитки", 2);
        Backpack backpack = new Backpack(capacity, material);
        System.out.println("Создан " + material + " рюкзак!");
        return backpack;
    }

    private static void checkAndConsume(InventoryStorage storage, String resourceName, int amount) {
        InventoryItem item = storage.takeItem(resourceName, amount);
        if (item == null) {
            throw new IllegalStateException("Недостаточно ресурсов: " + resourceName + " (нужно " + amount + ")");
        }
    }
}
