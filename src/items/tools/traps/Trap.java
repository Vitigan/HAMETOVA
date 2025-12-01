package items.tools.traps;

import entities.LivingInteractable;
import entities.LivingBeing;

public interface Trap extends LivingInteractable {
    boolean isSet();

    void setTrap();

    boolean isOccupied();

    LivingBeing getCapturedAnimal();

    double getCatchChance();
}
