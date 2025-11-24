package items.containers;

import entities.LivingBeing;
import entities.human.Robinson;
import interfaces.Craftable;
import interfaces.ItemInteractable;
import interfaces.HoldsItems;
import items.inventory.InventoryItem;
import items.inventory.InventoryStorage;
import java.util.ArrayList;
import java.util.List;

public class Basket implements Craftable, ItemInteractable, HoldsItems {
    private boolean isCrafted;
    private final InventoryStorage storage;
    private String style;

    public Basket() {
        this("простая", 15.0);
    }

    public Basket(String style, double maxWeight) {
        this.isCrafted = false;
        this.storage = new InventoryStorage(maxWeight);
        this.style = style;
    }

    // Constructor for Main.java compatibility
    public Basket(int maxWeight, String style) {
        this(style, (double) maxWeight);
    }

    @Override
    public void craft() {
        if (isCrafted) {
            throw new exceptions.InteractionException("Корзина уже сплетена!");
        }
        isCrafted = true;
        System.out.println("Сплетена " + style + " корзина!");
    }

    @Override
    public boolean isFinished() {
        return isCrafted;
    }

    @Override
    public InventoryStorage getStorage() {
        return storage;
    }

    @Override
    public void interactWithItem(LivingBeing interactor) {
        System.out.println(interactor.getName() + " взаимодействует с " + style + " корзиной");

        if (!isCrafted) {
            throw new exceptions.InteractionException("Корзина еще не сплетена!");
        }

        // Просто показываем содержимое через InventoryStorage
        storage.printContents();

        if (interactor instanceof Robinson) {
            Robinson robinson = (Robinson) interactor;
            if (storage.isEmpty()) {
                throw new exceptions.InteractionException("Корзина пуста - нечего забирать!");
            }
            robinson.talk("Заберу предметы в свой инвентарь!");
            transferAllTo(robinson.getStorage());
        }
    }

    private void transferAllTo(InventoryStorage target) {
        // Создаем копию списка, чтобы избежать ConcurrentModificationException
        List<InventoryItem> itemsToTransfer = new ArrayList<>(storage.getItems());

        for (InventoryItem item : itemsToTransfer) {
            if (target.addItem(item)) {
                storage.takeItem(item.name(), item.quantity());
            } else {
                System.out.println("Не удалось перенести " + item.name() + " (нет места).");
            }
        }
    }

    @Override
    public String getDescription() {
        return style + " корзина (" + storage.getCurrentWeight() + "/" + storage.getMaxWeight() + " кг)";
    }

    @Override
    public boolean canInteract() {
        return isCrafted;
    }

    // Wrapper methods for Main.java compatibility
    public void store(String itemName) {
        // Simplified storage for string items (mocking weight/quantity)
        storage.addItem(new InventoryItem(itemName, 1, 1.0));
        System.out.println("Положил " + itemName + " в корзину.");
    }

    public String take() {
        if (storage.isEmpty())
            return "ничего";
        InventoryItem item = storage.getItems().get(0);
        storage.takeItem(item.name(), item.quantity());
        return item.name();
    }

    public String getContents() {
        return storage.getItems().toString();
    }

    public void carry() {
        System.out.println("Несу корзину " + style);
    }

    public String getStyle() {
        return style;
    }
}