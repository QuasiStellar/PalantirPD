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

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.ResultDescriptions;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.items.Generator;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.weapon.enchantments.Death;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.sprites.FossilSkeletonSprite;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class FossilSkeleton extends Mob {

	private static final String TXT_HERO_KILLED = "You were killed by the explosion of bones...";

	{
		name = "fossil skeleton";
		spriteClass = FossilSkeletonSprite.class;

		HP = HT = 25+(adj(0)*Random.NormalIntRange(3, 7));
		defenseSkill = 9+adj(1);

		EXP = 5;
		maxLvl = 10;

		loot = Generator.Category.WEAPON;
		lootChance = 0.2f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(12+adj(0), 20+adj(3));
	}

	@Override
	protected float attackDelay() {
		return 1.2f;
	}
	
	@Override
	public void die(Object cause) {

		super.die(cause);

		boolean heroKilled = false;
		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			Char ch = findChar(pos + Level.NEIGHBOURS8[i]);
			if (ch != null && ch.isAlive()) {
				int damage = Math.max(0,
						Random.NormalIntRange(3, 8) - Random.IntRange(0, ch.dr() / 2));
				ch.damage(damage, this);
				if (ch == Dungeon.hero && !ch.isAlive()) {
					heroKilled = true;
				}
			}
		}

		if (Dungeon.visible[pos]) {
			Sample.INSTANCE.play(Assets.SND_BONES);
		}

		if (heroKilled) {
			Dungeon.fail(Utils.format(ResultDescriptions.MOB,
					Utils.indefinite(name)));
			GLog.n(TXT_HERO_KILLED);
		}
	}

	@Override
	protected Item createLoot() {
		Item loot = Generator.random(Generator.Category.WEAPON);
		for (int i = 0; i < 2; i++) {
			Item l = Generator.random(Generator.Category.WEAPON);
			if (l.level < loot.level) {
				loot = l;
			}
		}
		return loot;
	}

	@Override
	public int attackSkill(Char target) {
		return 12+adj(0);
	}

	@Override
	public int dr() {
		return 5+adj(0);
	}

	@Override
	public String defenseVerb() {
		return "blocked";
	}

	@Override
	public String description() {
		return "Skeletons are composed of corpses bones from unlucky adventurers and inhabitants of the dungeon, "
				+ "animated by emanations of evil magic from the depths below. After they have been "
				+ "damaged enough, they disintegrate in an explosion of bones. "
				+ "This skeleton appears to be made from fossilized bone. ";
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Death.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
