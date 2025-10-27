package exceptions;

public class CraftingException extends Exception {
    public CraftingException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Ошибка создания: " + super.getMessage();
    }
}