/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.quasistellar.otiluke.items.stones;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Badges;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class Diamond2 extends Item {

	public int charge;
	public int chargeCap;
	
	{
		name = "blue crystal";
		image = ItemSpriteSheet.DBLUE;
		bones = false;
		stackable=true;

		charge = 0;
		chargeCap = 1;
	}

	private static final String CHARGE = "charge";

	@Override
	public void storeInBundle(com.watabou.utils.Bundle bundle) {
		super.storeInBundle(bundle);

		bundle.put(CHARGE, charge);
	}

	@Override
	public void restoreFromBundle(com.watabou.utils.Bundle bundle) {
		super.restoreFromBundle(bundle);

		charge = bundle.getInt(CHARGE);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		return new ArrayList<>(); //yup, no dropping this one
	}

    @Override
    public String status() {
        return Utils.format("%d/%d", charge, chargeCap);
    }

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	public static class BlueDiamond extends Item {

		{
			name = "blue crystal";
			stackable = true;
			image = ItemSpriteSheet.DBLUE;
		}

		@Override
		public boolean doPickUp(Hero hero) {
			Diamond2 stone = hero.belongings.getItem(Diamond2.class);

			if (stone == null) {
				GLog.w("How is it possible???");
				return false;
			}
			if (stone.charge >= stone.chargeCap) {
				GLog.i("How is it possible???");
				return true;
			} else {

				stone.charge++;
				if (stone.charge == stone.chargeCap) {
					GLog.p("You find the crystal!");
				} else
					GLog.i("You find the crystal!");
				Badges.validateCrystal2();
				Sample.INSTANCE.play(Assets.SND_DEWDROP);
				hero.spendAndNext(TIME_TO_PICK_UP);
				return true;

			}
		}

		@Override
		public String info() {
			return "It shines like a newborn star!";
		}

	}

	@Override
	public int price() {
		return 1000 * quantity;
	}
}
