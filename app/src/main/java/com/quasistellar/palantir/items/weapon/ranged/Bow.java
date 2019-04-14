package com.quasistellar.palantir.items.weapon.ranged;

import com.quasistellar.palantir.sprites.ItemSpriteSheet;

/**
 * Created by matthewporritt on 11/14/17.
 */

public class Bow extends RangedWeapon {

    {
        name = "bow";
        image = ItemSpriteSheet.GOLDEN_KEY;
    }

    public Bow() {
        super(2, 20);
    }

    @Override
    public String desc() {
        return "Shooty shoot.";
    }
}
