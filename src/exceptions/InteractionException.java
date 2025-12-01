package exceptions;

public class InteractionException extends RuntimeException {
    public InteractionException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "Ошибка взаимодействия: " + super.getMessage();
    }
}
