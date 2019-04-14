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
package com.quasistellar.palantir.items.weapon.enchantments;

import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.effects.particles.ShadowParticle;
import com.quasistellar.palantir.items.weapon.Weapon;
import com.quasistellar.palantir.items.weapon.melee.relic.RelicMeleeWeapon;
import com.quasistellar.palantir.sprites.ItemSprite;
import com.quasistellar.palantir.sprites.ItemSprite.Glowing;
import com.watabou.utils.Random;

public class Death extends Weapon.Enchantment {

	private static final String TXT_GRIM = "Grim %s";

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing(0x000000);
	
	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}
	
	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		// lvl 0 - 8%
		// lvl 1 ~ 9%
		// lvl 2 ~ 10%
		int level = Math.max(0, weapon.level);

		if (Random.Int(level + 100) >= 92) {

			defender.damage(defender.HP, this);
			defender.sprite.emitter().burst(ShadowParticle.UP, 5);

			if (!defender.isAlive() && attacker instanceof Hero) {
			}

			return true;

		} else {

			return false;

		}
	}

	@Override
	public Glowing glowing() {
		return BLACK;
	}

	@Override
	public String name(String weaponName) {
		return String.format(TXT_GRIM, weaponName);
	}

}
