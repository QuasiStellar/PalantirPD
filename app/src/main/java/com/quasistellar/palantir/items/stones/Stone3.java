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
package com.quasistellar.palantir.items.stones;

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Badges;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.noosa.audio.Sample;

import java.util.ArrayList;

public class Stone3 extends Item {

	public int charge;
	public int chargeCap;
	
	{
		name = "blue stone";
		image = ItemSpriteSheet.NORNBLUE;
		bones = false;
		stackable=true;

		charge = 0;
		chargeCap = 30;
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

	public static class BlueStone extends Item {

		{
			name = "blue stone";
			stackable = true;
			image = ItemSpriteSheet.NORNBLUE;
		}

		@Override
		public boolean doPickUp(Hero hero) {
			Stone3 stone = hero.belongings.getItem(Stone3.class);

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
					GLog.p("The stone is completed!");
					Badges.validateStone2();
				} else
					GLog.i("You collect the stone.");

				Sample.INSTANCE.play(Assets.SND_DEWDROP);
				hero.spendAndNext(TIME_TO_PICK_UP);
				return true;

			}
		}

		@Override
		public String info() {
			return "A colorful stone, lying here, in the castle.";
		}

	}

	@Override
	public int price() {
		return 1000 * quantity;
	}
}
