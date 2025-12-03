package items.tools;

import enums.WeaponType;
import exceptions.ItemBrokenException;

public class Bow extends Weapon {

    public Bow() {
        super(WeaponType.BOW);
    }

    @Override
    public boolean use() throws ItemBrokenException {
        System.out.print("*Вжиух* "); // Звук выстрела из лука
        return super.use();
    }
}
