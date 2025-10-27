import entities.*;
import enums.*;
import exceptions.*;
import interfaces.*;
import items.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== РОБИНЗОН КРУЗО - СЦЕНАРИЙ ПО МОТИВАМ ПРОИЗВЕДЕНИЯ ===\n");

        try {
            // Создание мира и персонажей с использованием полиморфизма
            List<LivingBeing> inhabitants = new ArrayList<>();

            Human robinson = new Human("Робинзон", 45);
            Parrot popka = new Parrot("Попка", 3, true);

            Goat oldGoat = new Goat("Старый козел", 6, GoatType.WILD);
            Goat domesticGoat = new Goat("Домашняя коза", 4, GoatType.DOMESTIC);
            Goat kid1 = new Goat("Козленок1", 1, GoatType.KID);
            Goat kid2 = new Goat("Козленок2", 1, GoatType.KID);

            inhabitants.add(robinson);
            inhabitants.add(popka);
            inhabitants.add(oldGoat);
            inhabitants.add(domesticGoat);
            inhabitants.add(kid1);
            inhabitants.add(kid2);

            // Использование record для инвентаря
            List<InventoryItem> inventory = Arrays.asList(
                    new InventoryItem("Порох", 50, 2.5),
                    new InventoryItem("Зерно", 100, 5.0),
                    new InventoryItem("Табак", 10, 0.5)
            );

            System.out.println("=== НАЧАЛО ИСТОРИИ ===\n");

            // Сцена 1: Встреча с попугаем
            System.out.println("СЦЕНА 1: Неожиданная встреча");

            // Робинзон представляется
            robinson.introduce();

            robinson.setEmotion(Emotion.SAD);
            robinson.makeSound();
            robinson.move();

            // Козы едят траву
            System.out.println("\nКозы пасутся на лугу:");
            oldGoat.eat();
            domesticGoat.eat();
            kid1.eat();
            kid2.eat();

            popka.move();
            popka.flyTo(robinson);
            popka.talk("Бедный Робин Крузо! Как ты сюда пришел? Где ты был?");
            popka.sitOnFinger();
            robinson.setEmotion(Emotion.HAPPY);
            robinson.feelHappy();

            System.out.println();

            // Сцена 2: Размышления о лодке и опасностях
            System.out.println("СЦЕНА 2: Размышления об опасностях");
            Boat rescueBoat = new Boat("Спасательная шлюпка");
            rescueBoat.craft();

            try {
                rescueBoat.sail();
                System.out.println("Робинзон пытается плыть...");
            } catch (ItemBrokenException e) {
                System.out.println("Робинзон размышляет: " + e.getMessage());
                System.out.println("От одной мысли об опасностях у меня замирает сердце и стынет кровь в жилах...");
            }

            // Используем repair из Boat
            System.out.println("\nРобинзон ремонтирует лодку:");
            rescueBoat.repair();
            System.out.println("Прочность лодки после ремонта: " + rescueBoat.getDurability() + "%");

            robinson.setEmotion(Emotion.SAD);
            System.out.println();

            // Сцена 3: Ремесла и изобретения
            System.out.println("СЦЕНА 3: Ремесленная деятельность");

            // Создание трубки
            Pipe clayPipe = new Pipe(Material.CLAY);
            clayPipe.craft();

            try {
                clayPipe.smoke();
                robinson.setEmotion(Emotion.HAPPY);
                System.out.println("Робинзон радуется своей сметке!");
            } catch (CraftingException e) {
                System.out.println("Робинзон огорчен: " + e.getMessage());
            }

            // Создание корзин
            Basket storageBasket = new Basket(15, "большая глубокая");
            storageBasket.craft();

            // Используем store и take из Basket
            System.out.println("\nРобинзон наполняет корзину:");
            storageBasket.store("зерно");
            storageBasket.store("табак");
            storageBasket.store("яблоки");
            storageBasket.store("орехи");

            System.out.println("Содержимое корзины: " + storageBasket.getContents());

            System.out.println("\nРобинзон достает предметы из корзины:");
            String item1 = storageBasket.take();
            String item2 = storageBasket.take();
            System.out.println("Достал: " + item1 + " и " + item2);
            System.out.println("Осталось в корзине: " + storageBasket.getContents());

            storageBasket.carry();

            System.out.println();

            // Сцена 4: Инструменты и материалы
            System.out.println("СЦЕНА 4: Создание инструментов");

            // Используем все материалы
            Tool woodHammer = new Tool("Молоток", Material.WOOD);
            Tool metalAxe = new Tool("Топор", Material.METAL);
            Basket vineBasket = new Basket(10, "из лозы");
            Tool powderContainer = new Tool("Пороховница", Material.POWDER);

            woodHammer.craft();
            metalAxe.craft();
            vineBasket.craft();
            powderContainer.craft();

            // Используем инструменты
            System.out.println("\nРобинзон использует инструменты:");
            try {
                boolean hammerSuccess = woodHammer.use();
                System.out.println("Молоток сработал: " + (hammerSuccess ? "УСПЕХ" : "НЕУДАЧА"));

                boolean axeSuccess = metalAxe.use();
                System.out.println("Топор сработал: " + (axeSuccess ? "УСПЕХ" : "НЕУДАЧА"));

                // Демонстрация износа
                System.out.println("Прочность молотка: " + woodHammer.getDurability() + "%");
                System.out.println("Прочность топора: " + metalAxe.getDurability() + "%");

                // Ремонт инструментов
                System.out.println("\nРобинзон ремонтирует инструменты:");
                woodHammer.repair();
                metalAxe.repair();
                System.out.println("Прочность после ремонта - молоток: " + woodHammer.getDurability() + "%, топор: " + metalAxe.getDurability() + "%");

            } catch (ItemBrokenException e) {
                System.out.println("Ошибка с инструментом: " + e.getMessage());
            }

            System.out.println();

            // Сцена 5: Проблемы с порохом и охота
            System.out.println("СЦЕНА 5: Проблемы с охотой");
            System.out.println("Запас пороха: " + inventory.get(0).quantity());

            Weapon gun = new Weapon(WeaponType.GUN);
            Weapon trap = new Weapon(WeaponType.TRAP);
            Weapon snare = new Weapon(WeaponType.SNARE);
            Weapon pitfall = new Weapon(WeaponType.PITFALL);

            gun.craft();
            trap.craft();
            snare.craft();
            pitfall.craft();

            // Попытка охоты с огнестрельным оружием
            int successfulHunts = 0;
            for (int i = 0; i < 6; i++) {
                try {
                    if (gun.use()) {
                        successfulHunts++;
                        System.out.println("Успешная охота! Добыта коза.");
                    }
                } catch (ItemBrokenException e) {
                    System.out.println("Проблема с охотой: " + e.getMessage());
                    break;
                }
            }

            System.out.println("Успешных охот: " + successfulHunts);
            System.out.println("Порох на исходе... Нужно искать другие способы добычи пищи");

            // Используем repair из Weapon
            System.out.println("\nРобинзон ремонтирует оружие:");
            gun.repair();
            trap.repair();
            System.out.println("Прочность оружия после ремонта - ружье: " + gun.getDurability() + "%, ловушка: " + trap.getDurability() + "%");

            System.out.println();

            // Сцена 6: Строительство ловушек
            System.out.println("СЦЕНА 6: Альтернативные методы охоты");
            robinson.setEmotion(Emotion.SAD);

            List<Goat> goats = Arrays.asList(oldGoat, domesticGoat, kid1, kid2);
            List<Goat> capturedGoats = new ArrayList<>();

            System.out.println("Робинзон строит ловушки и силки...");

            for (int i = 0; i < goats.size(); i++) {
                Goat goat = goats.get(i);
                try {
                    if (trap.use() || snare.use() || pitfall.use()) {
                        goat.getTrapped();
                        capturedGoats.add(goat);
                        System.out.println("В ловушку попался: " + goat.getName() + " (тип: " + goat.getType() + ")");
                    }
                } catch (ItemBrokenException e) {
                    System.out.println("Ловушка сломалась: " + e.getMessage());
                }
            }

            // Попытка побега некоторых коз
            for (int i = 0; i < capturedGoats.size(); i++) {
                Goat goat = capturedGoats.get(i);
                goat.escape();
            }

            System.out.println();

            // Сцена 7: Итоги и размышления
            System.out.println("СЦЕНА 7: Итоги года уединения");

            int remainingCaptured = 0;
            for (int i = 0; i < capturedGoats.size(); i++) {
                Goat goat = capturedGoats.get(i);
                if (goat.isTrapped()) {
                    remainingCaptured++;
                }
            }
            System.out.println("Поймано и осталось в ловушках: " + remainingCaptured + " коз");

            // Демонстрация всех типов коз
            System.out.println("\nТипы коз на острове:");
            for (int i = 0; i < goats.size(); i++) {
                Goat goat = goats.get(i);
                System.out.println("- " + goat.getName() + ": " + goat.getType());
            }

            robinson.setEmotion(Emotion.HAPPY);
            System.out.println("Робинзон чувствует себя счастливым, покорившись воле провидения.");
            System.out.println("Он усовершенствовался во всех ремеслах и ни в чем не терпит недостатков,");
            System.out.println("за исключением человеческого общества.");

            System.out.println();

            // Демонстрация полиморфизма
            System.out.println("=== ДЕМОНСТРАЦИЯ ПОЛИМОРФИЗМА ===");
            for (int i = 0; i < inhabitants.size(); i++) {
                LivingBeing being = inhabitants.get(i);
                System.out.println(being);
                being.makeSound();
                being.move();
                System.out.println("---");
            }

            // Демонстрация работы с record
            System.out.println("=== ИНВЕНТАРЬ РОБИНЗОНА ===");
            for (int i = 0; i < inventory.size(); i++) {
                InventoryItem item = inventory.get(i);
                System.out.println(item);
            }

            // Подсчет веса
            double totalWeight = 0;
            for (int i = 0; i < inventory.size(); i++) {
                totalWeight += inventory.get(i).weight();
            }
            System.out.printf("Общий вес инвентаря: %.1f кг\n", totalWeight);

            // Демонстрация всех использованных материалов
            System.out.println("\n=== ИСПОЛЬЗОВАННЫЕ МАТЕРИАЛЫ ===");
            System.out.println("- " + Material.CLAY + ": для трубки");
            System.out.println("- " + Material.WOOD + ": для молотка");
            System.out.println("- " + Material.METAL + ": для топора");
            System.out.println("- " + Material.VINE + ": для корзины");
            System.out.println("- " + Material.POWDER + ": для пороховницы");

            // Демонстрация исключений
            System.out.println("\n=== ПРОВЕРКА ИСКЛЮЧЕНИЙ ===");
            try {
                Pipe brokenPipe = new Pipe(Material.CLAY);
                brokenPipe.smoke(); // Должно выбросить исключение
            } catch (CraftingException e) {
                System.out.println("Поймано проверяемое исключение: " + e.getMessage());
            }

            try {
                Boat brokenBoat = new Boat("Старая лодка");
                brokenBoat.sail(); // Должно выбросить исключение
            } catch (ItemBrokenException e) {
                System.out.println("Поймано непроверяемое исключение: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Неожиданная ошибка в сценарии: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=== КОНЕЦ СЦЕНАРИЯ ===");
    }
}