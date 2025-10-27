package items;

import interfaces.Craftable;
import exceptions.ItemBrokenException;


public class Boat implements Craftable {

    private boolean isBuilt;
    private boolean isOnWater;
    private int durability;
    private String name;

    public Boat(String name){
        this.name = name;
        this.isBuilt = false;
        this.isOnWater = false;
        this.durability = 100;
    }

    @Override
    public void craft(){
        if (!isBuilt){
            isBuilt = true;
            System.out.println(name + " построена!");
        }
    }

    @Override
    public boolean isFinished(){
        return isBuilt;
    }

    public void sail() throws ItemBrokenException{
        if (!isBuilt){
            throw new ItemBrokenException("Сначала построй лодку!");
        }
        if (durability <=0){
            throw new ItemBrokenException("Лодка сломана!");
        }
        isOnWater = true;
        durability -= 10; // лодка изнашивается
        System.out.println(name + " плывёт. Прочность: " + durability + "%");

        if(Math.random() <0.2){
            durability = 0;
            throw new ItemBrokenException("Лодка сломалась в море!");
        }
    }
    public void repair(){
        if (isBuilt && durability <100){
            durability = 100;
            System.out.println(name + " отремонтирована!");
        }
    }

    // геттеры получить информацию о лодке
    public boolean isBuilt() {
        return isBuilt;
    }

    public boolean isOnWater() {
        return isOnWater;
    }

    public int getDurability() {
        return durability;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (!(obj instanceof Boat)) return false;
        Boat other = (Boat) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode(){
        return name.hashCode(); //хэш на основе имени
    }

    @Override
    public String toString(){
        return "Лодка '" + name + "' (построена: " + isBuilt + ", на воде: " + isOnWater + ", прочность: " + durability + "%)";
    }

}