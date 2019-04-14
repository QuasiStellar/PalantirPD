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
package com.quasistellar.palantir.actors.buffs;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.items.rings.RingOfElements.Resistance;
import com.quasistellar.palantir.ui.BuffIndicator;

public class Weakness extends FlavourBuff {

	private static final float DURATION = 40f;

	@Override
	public int icon() {
		return BuffIndicator.WEAKNESS;
	}

	@Override
	public String toString() {
		return "Weakened";
	}

	@Override
	public boolean attachTo(Char target) {
		if (super.attachTo(target) && target==Dungeon.hero) {
			Hero hero = (Hero) target;
			hero.weakened = true;
			hero.belongings.discharge();

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void detach() {
		super.detach();
		if  (target==Dungeon.hero){
		((Hero) target).weakened = false;
		}
	}

	public static float duration(Char ch) {
		Resistance r = ch.buff(Resistance.class);
		return r != null ? r.durationFactor() * DURATION : DURATION;
	}
}
