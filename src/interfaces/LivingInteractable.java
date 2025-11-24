package interfaces;

import entities.LivingBeing;

public interface LivingInteractable {
    void interactWithLiving(LivingBeing interactor);
    String getDescription();
}