package com.quasistellar.otiluke.items.Ammo;

import com.quasistellar.otiluke.sprites.ItemSpriteSheet;

/**
 * Created by matthewporritt on 8/12/17.
 */

public class SilverArrow extends Ammo {

    {
        name = "silver arrow";
        image = ItemSpriteSheet.DART;

        MIN = 0;
        MAX = 1;

        DIE = 2;
        SIDES = 4;

        speedFactor = 1;

        imbue = imbueFamily.COLD;
    }


    public SilverArrow() {
        this(1);
    }

    public SilverArrow(int number) {
        super();
        quantity = number;
    }

}
