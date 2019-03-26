package com.quasistellar.otiluke.items.Ammo;

import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.rings.RingOfSharpshooting;
import com.watabou.utils.Random;

/**
 * Created by matthewporritt on 8/12/17.
 */

public class Ammo extends Item {

    public int MIN = 0;
    public int MAX = 1;

    public int DIE = 1;
    public int SIDES = 4;

    public int speedFactor = 1;

    public imbueFamily imbue = imbueFamily.GENERAL;


    @Override
    public boolean isIdentified() {
        return true;
    }

    public enum imbueFamily {
        GENERAL,
        LIT,
        FIRE,
        COLD,
        STATUS,
        DEFENSE,
        ENERGY
    }

    public imbueFamily checkimbue(){
        return imbue;
    }



    protected void miss(int cell) {
        int bonus = 0;
        for (Buff buff : curUser.buffs(RingOfSharpshooting.Aim.class)) {
            bonus += ((RingOfSharpshooting.Aim) buff).level;
        }

        // degraded ring of sharpshooting will even make missed shots break.
        if (Random.Float() < Math.pow(0.6, -bonus))
            super.onThrow(cell);
    }


}
