package items;

import entities.LivingBeing;

public interface ItemInteractable {
    void interactWithItem(LivingBeing interactor);

    String getDescription();

    boolean canInteract();
}
