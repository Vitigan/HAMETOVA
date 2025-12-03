import entities.animals.Goat;
import entities.animals.Parrot;
import entities.human.Robinson;
import enums.*;
import exceptions.*;
import items.*;
import items.tools.Pipe;
import items.tools.Weapon;
import items.tools.traps.Pit;
import items.tools.traps.Snare;
import items.transport.Boat;
import items.inventory.InventoryItem;
import items.containers.Basket;
import items.containers.Backpack;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== РОБИНЗОН КРУЗО - СЦЕНАРИЙ ПО МОТИВАМ ПРОИЗВЕДЕНИЯ ===\n");

        // Создание персонажей
        Robinson robinson = new Robinson("Робинзон", 45);
        Parrot popka = new Parrot("Попка", 3, true);

        Goat oldGoat = new Goat("Старый козел", 6, GoatAge.OLD);
        Goat domesticGoat = new Goat("Домашняя коза", 4, GoatAge.ADULT);
        Goat kid1 = new Goat("Козленок1", 1, GoatAge.YOUNG);
        Goat kid2 = new Goat("Козленок2", 1, GoatAge.YOUNG);

        System.out.println("=== НАЧАЛО ИСТОРИИ ===\n");

        // СЦЕНА 1: Встреча с попугаем
        System.out.println("СЦЕНА 1: Неожиданная встреча");
        robinson.introduce();
        robinson.setEmotion(Emotion.SAD);
        robinson.makeSound();
        robinson.move();

        oldGoat.eat();
        domesticGoat.eat();
        kid1.eat();
        kid2.eat();

        popka.move();
        popka.flyTo(robinson);
        popka.talk("Бедный Робин Крузо! Как ты сюда пришел? Где ты был?");
        popka.sitOnFinger();
        robinson.setEmotion(Emotion.HAPPY);
        System.out.println();

        // СЦЕНА 2: Размышления о лодке
        System.out.println("СЦЕНА 2: Размышления об опасностях");

        // Сбор ресурсов для лодки
        System.out.println("Робинзон собирает материалы для лодки...");
        robinson.getStorage().addItem(new InventoryItem("Дерево", 10, 0.5));
        robinson.getStorage().addItem(new InventoryItem("Гвозди", 5, 0.05));

        // Используем Фабрику для создания лодки
        Boat rescueBoat = CraftingFactory.createBoat(robinson.getStorage(), "Спасательная шлюпка");

        try {
            rescueBoat.sail();
            System.out.println("Робинзон пытается плыть...");
            rescueBoat.dock();
        } catch (ItemBrokenException e) {
            System.out.println("Робинзон размышляет: " + e.getMessage());
            System.out.println("От одной мысли об опасностях у меня замирает сердце и стынет кровь в жилах...");
        }

        rescueBoat.repair();
        robinson.setEmotion(Emotion.SAD);
        System.out.println();

        // СЦЕНА 3: Ремесла
        System.out.println("СЦЕНА 3: Ремесленная деятельность");

        // Сбор глины
        robinson.getStorage().addItem(new InventoryItem("Глина", 5, 0.5));

        // Используем Фабрику для создания трубки
        Pipe clayPipe = CraftingFactory.createPipe(robinson.getStorage(), Material.CLAY);
        try {
            clayPipe.smoke();
            robinson.setEmotion(Emotion.HAPPY);
            System.out.println("Робинзон радуется своей сметке!");
        } catch (InteractionException e) {
            System.out.println("Неудача с трубкой: " + e.getMessage());
            robinson.setEmotion(Emotion.SAD);
        }

        // Используем Фабрику для создания корзины
        robinson.getStorage().addItem(new InventoryItem("Прутья", 10, 0.2));
        Basket storageBasket = CraftingFactory.createBasket(robinson.getStorage(), 15, "большая глубокая");

        System.out.println("\nРобинзон наполняет корзину:");
        storageBasket.put(new InventoryItem("Глина", 4, 1.0));
        storageBasket.carry();

        // Используем Фабрику для создания рюкзака
        robinson.getStorage().addItem(new InventoryItem("Кожа", 5, 1.0));
        robinson.getStorage().addItem(new InventoryItem("Нитки", 5, 0.01));
        Backpack backpack = CraftingFactory.createBackpack(robinson.getStorage(), 10, "кожаный");
        System.out.println("Робинзон кладет вещи в рюкзак:");
        backpack.store("Трубка"); // Кладем как предмет (строку)

        // Попытка положить лодку в рюкзак (должна быть ошибка размера)
        System.out.println("Попытка положить лодку в рюкзак:");
        if (!backpack.getStorage().addItem(rescueBoat)) {
            System.out.println("-> Не удалось: лодка слишком большая!");
        }

        backpack.wear();
        System.out.println();

        // СЦЕНА 4: Охота
        System.out.println("СЦЕНА 4: Охота с луком");

        // Ресурсы для лука
        robinson.getStorage().addItem(new InventoryItem("Дерево", 5, 0.5));
        robinson.getStorage().addItem(new InventoryItem("Веревка", 2, 0.1));

        // Используем Фабрику для создания оружия
        Weapon bow = CraftingFactory.createWeapon(robinson.getStorage(), WeaponType.BOW);

        System.out.println("\nРобинзон выходит на охоту...");
        // Робинзон охотится на коз
        robinson.hunt(domesticGoat, bow);
        robinson.hunt(kid2, bow);

        System.out.println("Охота завершена.");
        bow.repair();
        System.out.println();

        // СЦЕНА 5: Ловушки
        System.out.println("СЦЕНА 5: Альтернативные методы охоты");
        robinson.setEmotion(Emotion.SAD);

        // Установка ловушек через Фабрику
        // Ресурсы для силков
        robinson.getStorage().addItem(new InventoryItem("Веревка", 5, 0.2));
        Snare snare = CraftingFactory.createSnare(robinson.getStorage(), "бечевка");
        Pit pit = CraftingFactory.createPit(robinson.getStorage());

        snare.setTrap();
        pit.setTrap();

        System.out.println("\n--- Проверка ловушек ---");

        // Животные попадают в ловушки
        snare.interactWithItem(kid1); // Козленок попадает в силки
        pit.interactWithItem(oldGoat); // Старый козел падает в яму

        // Тест размера ямы
        System.out.println("\n--- Тест размера ямы ---");
        // Создаем маленькую яму (хак через рефлексию или просто новый класс, но тут мы
        // просто создадим новую яму и скажем что она малая, если бы могли)
        // Но так как мы не можем изменить размер созданной ямы (он final), мы просто
        // проверим текущую логику.
        // Яма LARGE, Робинзон MEDIUM. Он упадет.
        // Давайте попробуем поймать слона (которого нет, но мы можем создать гигантскую
        // козу)
        Goat giantGoat = new Goat("Гигантская Коза", 10, GoatAge.OLD);
        // Хак: изменим размер козы через рефлексию или просто поверим, что если бы она
        // была GIANT, она бы не упала.
        // Вместо этого, давайте создадим "Мелкую яму" если бы могли.

        // Ладно, давайте просто проверим, что Робинзон (MEDIUM) падает в Яму (LARGE)
        System.out.println("Робинзон (MEDIUM) подходит к Яме (LARGE):");
        pit.interactWithItem(robinson);

        // А теперь представим, что яма маленькая (нужно было бы добавить конструктор с
        // размером в Pit, но мы не добавили)
        // Давайте добавим конструктор в Pit с размером, чтобы протестировать это.

        // Робинзон проверяет ловушки
        System.out.println("\nРобинзон обходит ловушки:");
        snare.interactWithItem(robinson);
        pit.interactWithItem(robinson);

        // Попытка побега
        System.out.println("\n--- Попытка побега ---");
        kid1.escape();
        oldGoat.escape();

        // Проверка ловушек после попытки побега
        System.out.println("\nРобинзон снова проверяет ловушки:");
        snare.interactWithItem(robinson);
        pit.interactWithItem(robinson);

        System.out.println();

        // СЦЕНА 6: Итоги
        System.out.println("СЦЕНА 6: Итоги года уединения");

        int remainingCaptured = 0;
        if (kid1.isTrapped())
            remainingCaptured++;
        if (oldGoat.isTrapped())
            remainingCaptured++;

        System.out.println("Поймано и осталось в ловушках: " + remainingCaptured + " коз");

        robinson.setEmotion(Emotion.HAPPY);
        System.out.println("Робинзон чувствует себя счастливым, покорившись воле провидения.");
        System.out.println("Он усовершенствовался во всех ремеслах и ни в чем не терпит недостатков,");
        System.out.println("за исключением человеческого общества.");

        System.out.println("\n=== КОНЕЦ СЦЕНАРИЯ ===");
    }
}
