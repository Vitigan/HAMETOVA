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
        Boat rescueBoat = new Boat("Спасательная шлюпка");
        rescueBoat.craft();

        try {
            rescueBoat.sail();
            System.out.println("Робинзон пытается плыть...");
            rescueBoat.dock(); // Логическое завершение плавания
        } catch (ItemBrokenException e) {
            System.out.println("Робинзон размышляет: " + e.getMessage());
            System.out.println("От одной мысли об опасностях у меня замирает сердце и стынет кровь в жилах...");
        }

        rescueBoat.repair();
        robinson.setEmotion(Emotion.SAD);
        System.out.println();

        // СЦЕНА 3: Ремесла
        System.out.println("СЦЕНА 3: Ремесленная деятельность");
        Pipe clayPipe = new Pipe(Material.CLAY);
        clayPipe.craft();
        clayPipe.smoke();
        robinson.setEmotion(Emotion.HAPPY);
        System.out.println("Робинзон радуется своей сметке!");

        Basket storageBasket = new Basket(15, "большая глубокая");
        storageBasket.craft();

        System.out.println("\nРобинзон наполняет корзину:");
        storageBasket.put(new InventoryItem("Глина", 4, 1.0));
        storageBasket.carry();
        System.out.println();

        // СЦЕНА 4: Создание инструментов
        System.out.println("СЦЕНА 4: Создание инструментов");
        Tool woodHammer = new Tool("Молоток", Material.WOOD);
        Tool metalAxe = new Tool("Топор", Material.METAL);

        woodHammer.craft();
        metalAxe.craft();

        try {
            woodHammer.use();
            metalAxe.use();

            woodHammer.repair();
            metalAxe.repair();
        } catch (ItemBrokenException e) {
            System.out.println("Ошибка с инструментом: " + e.getMessage());
        }
        System.out.println();

        // СЦЕНА 5: Охота
        System.out.println("СЦЕНА 5: Проблемы с охотой");
        Weapon gun = new Weapon(WeaponType.GUN);
        gun.craft();

        int successfulHunts = 0;
        for (int i = 0; i < 6; i++) {
            try {
                if (gun.use()) {
                    successfulHunts++;
                }
            } catch (ItemBrokenException e) {
                System.out.println("Проблема с охотой: " + e.getMessage());
                break;
            }
        }

        System.out.println("Успешных охот: " + successfulHunts);
        System.out.println("Порох на исходе... Нужно искать другие способы добычи пищи");
        gun.repair();
        System.out.println();

        // СЦЕНА 6: Ловушки
        System.out.println("СЦЕНА 6: Альтернативные методы охоты");
        robinson.setEmotion(Emotion.SAD);

        // Используем новые классы ловушек
        Snare snare = new Snare("бечевка");
        Pit pit = new Pit();

        snare.craft();
        pit.craft();

        snare.setTrap();
        pit.setTrap();

        System.out.println("\n--- Проверка ловушек ---");

        // Симуляция попадания животных
        snare.interactWithItem(kid1); // Козленок попадает в силки
        pit.interactWithItem(oldGoat); // Старый козел падает в яму

        // Робинзон проверяет ловушки
        System.out.println("\nРобинзон обходит ловушки:");
        snare.interactWithItem(robinson);
        pit.interactWithItem(robinson);

        // Попытка побега
        System.out.println("\n--- Попытка побега ---");
        kid1.escape();
        oldGoat.escape();

        // Повторная проверка (должна показать, что животные сбежали или остались)
        System.out.println("\nРобинзон снова проверяет ловушки:");
        snare.interactWithItem(robinson);
        pit.interactWithItem(robinson);

        System.out.println();

        // СЦЕНА 7: Итоги
        System.out.println("СЦЕНА 7: Итоги года уединения");

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