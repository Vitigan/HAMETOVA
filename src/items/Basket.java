package items;

import interfaces.Craftable;
import java.util.ArrayList;
import java.util.List;

public class Basket implements Craftable {

    private boolean isCrafted;
    private int capacity;
    private List<String> contents;
    private String style;

    public Basket() {
        this.isCrafted = false;  // новая корзина еще не создана
        this.capacity = 10;      // по умолчанию вмещает 10 предметов
        this.contents = new ArrayList<>(); // создаем пустой список для предметов
        this.style = "простая";  // простой стиль по умолчанию
    }

    public Basket(int capacity, String style) {
        this();                  // вызываем первый конструктор
        this.capacity = capacity; // меняем вместимость
        this.style = style;      // меняем стиль
    }

    @Override
    public void craft() {
        if (!isCrafted) {
            isCrafted = true;
            System.out.println("Сплетена " + style + " корзина!");
        }
    }

    @Override
    public boolean isFinished() {
        return isCrafted;
    }

    public boolean store(String item) {  //метод
        if (!isCrafted) {
            System.out.println("Сначала сплети корзину!");
            return false;
        }
        if (contents.size() >= capacity) {
            System.out.println("Корзина полна! Нельзя положить: " + item);
            return false;
        }
        contents.add(item);
        System.out.println("В корзину положен: " + item);
        return true;
    }

    public String take() {
        if (contents.isEmpty()) {
            System.out.println("В корзине ничего нет!");
            return null;
        }
        // Берем последний положенный предмет
        String item = contents.remove(contents.size() - 1);
        System.out.println("Из корзины взят: " + item);
        return item;
    }

    public void carry() {
        if (isCrafted) {
            System.out.println("Несу корзину. Внутри: " + contents);
        } else {
            System.out.println("Не могу нести - корзина не сплетена!");
        }
    }

    public boolean isCrafted() { return isCrafted; }
    public int getCapacity() { return capacity; }
    public List<String> getContents() { return new ArrayList<>(contents); } //возвращаем копию
    public String getStyle() { return style; }
}