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
package com.quasistellar.palantir.items.armor.glyphs;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Invisibility;
import com.quasistellar.palantir.items.armor.Armor;
import com.quasistellar.palantir.items.armor.Armor.Glyph;
import com.quasistellar.palantir.items.wands.WandOfBlink;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.sprites.ItemSprite;
import com.quasistellar.palantir.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Displacement extends Glyph {

	private static final String TXT_DISPLACEMENT = "%s of displacement";

	private static ItemSprite.Glowing BLUE = new ItemSprite.Glowing(0x66AAFF);

	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		if (Dungeon.bossLevel()) {
			return damage;
		}
		
	    float LEVEL = 0.333f;
		if (defender.HP > defender.HT * LEVEL) {
			return damage;
		}

		int nTries = (armor.level < 0 ? 1 : armor.level + 1) * 5;
		for (int i = 0; i < nTries; i++) {
			int pos = Random.Int(Level.getLength());
			if (Dungeon.visible[pos] && Level.passable[pos]
					&& Actor.findChar(pos) == null) {

				WandOfBlink.appear(defender, pos);
				Dungeon.level.press(pos, defender);
				Buff.affect(defender, Invisibility.class, Invisibility.DURATION);
				Dungeon.observe();

				break;
			}
		}

		return damage;
	}

	@Override
	public String name(String weaponName) {
		return String.format(TXT_DISPLACEMENT, weaponName);
	}

	@Override
	public Glowing glowing() {
		return BLUE;
	}
}
