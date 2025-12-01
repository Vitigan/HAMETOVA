package entities;

public interface Talkable extends Soundable {
    void talk(String message);

    void introduce(); // Добавим этот метод для представления
}
