package items.tools.traps;

import interfaces.Craftable;
import interfaces.LivingInteractable;
import entities.LivingBeing;

public interface Trap extends Craftable, LivingInteractable {
    boolean isSet();
    void setTrap();
    boolean isOccupied();
    LivingBeing getCapturedAnimal();
    double getCatchChance();
}