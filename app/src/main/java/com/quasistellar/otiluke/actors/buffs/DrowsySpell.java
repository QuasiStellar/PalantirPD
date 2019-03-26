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
package com.quasistellar.otiluke.actors.buffs;

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.ui.BuffIndicator;
import com.watabou.utils.Random;

public class DrowsySpell extends Buff {

	@Override
	public int icon() {
		return BuffIndicator.DROWSY;
	}

	@Override
	public boolean attachTo(Char target) {

		int level= Dungeon.hero.magicLevel/5;

		if (!target.immunities().contains(Sleep.class)
				&& super.attachTo(target)) {
			if (cooldown() == 0)
				spend(Random.Int(3+level, 6+level*2));
			return true;
		}
		return false;
	}

	@Override
	public boolean act() {
		Buff.affect(target, MagicalSleep.class);

		detach();
		return true;
	}

	@Override
	public String toString() {
		return "Drowsy";
	}
}
