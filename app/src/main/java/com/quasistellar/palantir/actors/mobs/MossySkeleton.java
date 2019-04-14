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
import com.quasistellar.palantir.Statistics;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.items.Bone;
import com.quasistellar.palantir.items.PrisonKey;
import com.quasistellar.palantir.items.RedDewdrop;
import com.quasistellar.palantir.items.YellowDewdrop;
import com.quasistellar.palantir.items.weapon.enchantments.Death;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.sprites.MossySkeletonSprite;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class MossySkeleton extends Mob {

	private static final String TXT_HERO_KILLED = "You were killed by the explosion of bones...";
	private static final String TXT_KILLCOUNT = "Mossy Skeleton Kill Count: %s";

	{
		name = "moss covered skeleton";
		spriteClass = MossySkeletonSprite.class;

		HP = HT = 35+(10*Random.NormalIntRange(7, 10));
		defenseSkill = 15;

		EXP = 1;
		maxLvl = 10;
		
		baseSpeed = 0.5f+(Math.min(2f, Statistics.skeletonsKilled/50));

		loot = new YellowDewdrop();
		lootChance = 0.5f; // by default, see die()
			
		lootThird= new RedDewdrop();
		lootChanceThird = 0.1f; // by default, see die()
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20+Math.round(Statistics.skeletonsKilled/10), 45+Math.round(Statistics.skeletonsKilled/5));
		
	}

	@Override
	protected float attackDelay() {
		return 2f-(Math.min(1.75f, Statistics.skeletonsKilled/50));
	}
	
	@Override
	public void die(Object cause) {

		super.die(cause);
		
		Statistics.skeletonsKilled++;
		GLog.w(TXT_KILLCOUNT, Statistics.skeletonsKilled);
		
		if (!Dungeon.limitedDrops.prisonkey.dropped() && Dungeon.depth<27) {
			Dungeon.limitedDrops.prisonkey.drop();
			Dungeon.level.drop(new PrisonKey(), pos).sprite.drop();
			explodeDew(pos);				
		} else {
			explodeDew(pos);
		}
		
		if(!Dungeon.limitedDrops.bone.dropped() && Statistics.skeletonsKilled > 50 && Random.Int(10)==0) {
			Dungeon.limitedDrops.bone.drop();
			Dungeon.level.drop(new Bone(), pos).sprite.drop();
		}
		
		if(!Dungeon.limitedDrops.bone.dropped() && Statistics.skeletonsKilled > 100) {
			Dungeon.limitedDrops.bone.drop();
			Dungeon.level.drop(new Bone(), pos).sprite.drop();
		}

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
	public int attackSkill(Char target) {
		return 28;
	}

	@Override
	public int dr() {
		return 27;
	}

	@Override
	public String defenseVerb() {
		return "blocked";
	}

	@Override
	public String description() {
		return "Animated remains from an epic battle on this level. This skeleton rose from the ground covered in moss. "
				+ "It is angry you have disturbed this place. ";
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
