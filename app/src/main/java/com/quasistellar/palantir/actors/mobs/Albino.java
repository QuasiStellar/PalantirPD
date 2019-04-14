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
package com.quasistellar.palantir.actors.mobs;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Bleeding;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.items.food.Meat;
import com.quasistellar.palantir.sprites.AlbinoSprite;
import com.watabou.utils.Random;

public class Albino extends Rat {

	{
		name = "albino rat";
		spriteClass = AlbinoSprite.class;

		HP = HT = 10+(Dungeon.depth*Random.NormalIntRange(1, 3));

		loot = new Meat();
		lootChance = 1f;
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			Buff.affect(enemy, Bleeding.class).set(damage);
		}

		return damage;
	}

	@Override
	public String description() {
		return "This is a rare breed of marsupial rat, with pure white fur and jagged teeth.";
	}
}
