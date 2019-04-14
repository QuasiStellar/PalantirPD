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

import java.util.HashSet;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.ToxicGas;
import com.quasistellar.palantir.actors.buffs.Poison;
import com.quasistellar.palantir.items.Generator;
import com.quasistellar.palantir.items.food.Meat;
import com.quasistellar.palantir.items.weapon.Weapon;
import com.quasistellar.palantir.items.weapon.enchantments.Death;
import com.quasistellar.palantir.items.weapon.enchantments.Leech;
import com.quasistellar.palantir.items.weapon.melee.MeleeWeapon;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.RatSprite;
import com.quasistellar.palantir.sprites.SokobanSentinelSprite;
import com.quasistellar.palantir.sprites.StatueSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class SokobanSentinel extends Mob {


	private static final float SPAWN_DELAY = 2f;

	{
		name = "animated statue";
		spriteClass = StatueSprite.class;

		HP = HT = 99999;
		defenseSkill = 99999;

		baseSpeed = 0.5f;

		loot = new Meat();
		lootChance = 0.5f;

	}



	@Override
	public int damageRoll() {
		return 99999;
	}

	@Override
	public int attackSkill(Char target) {
		return 99999;
	}

	@Override
	public int dr() {
		return 99999;
	}

	@Override
	public String description() {
		return "You would think that it's just another one of this dungeon's ugly statues, but its red glowing eyes give it away."
				+ "\n\nWhile the statue itself is made of stone, the _"
				+ "epic sword" + "_, it's wielding, looks real.";
	}

	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
			}
		}
	}

	public static SokobanSentinel spawnAt(int pos) {

		SokobanSentinel b = new SokobanSentinel();

		b.pos = pos;
		b.state = b.HUNTING;
		GameScene.add(b, SPAWN_DELAY);

		return b;

	}

}
