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

import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.Dungeon;

public class ManaRegen extends Buff {

	private static final float REGENERATION_DELAY = 100;

	@Override
	public boolean act() {
		if (target.isAlive()) {

			if (target.MP < target.MT && !((Hero) target).isStarving()) {
				target.MP += 1;
			}

			int mLevel = Dungeon.hero.magicLevel+1;
			spend(REGENERATION_DELAY/mLevel);

		} else {

			diactivate();

		}

		return true;
	}
}
