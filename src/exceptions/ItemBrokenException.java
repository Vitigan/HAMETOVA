package exceptions;
    public class ItemBrokenException extends RuntimeException{
        public ItemBrokenException(String message){
            super(message);
        }

        @Override
        public String getMessage(){
            return "Поломка предмета: " + super.getMessage();
        }
    }