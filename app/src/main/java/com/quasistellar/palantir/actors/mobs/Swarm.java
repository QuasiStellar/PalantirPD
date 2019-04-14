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

import java.util.ArrayList;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Burning;
import com.quasistellar.palantir.actors.buffs.Poison;
import com.quasistellar.palantir.effects.Pushing;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.potions.PotionOfMending;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.levels.Terrain;
import com.quasistellar.palantir.levels.features.Door;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.SwarmSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Swarm extends Mob {

	{
		name = "swarm of flies";
		spriteClass = SwarmSprite.class;

		HP = HT = 80;
		defenseSkill = 5;

		maxLvl = 10;

		flying = true;

		loot = new PotionOfMending();
		//loot = new PotionOfMending(); potential nerf
		lootChance = 0.2f; // by default, see die()
	}

	private static final float SPLIT_DELAY = 1f;

	int generation = 0;

	private static final String GENERATION = "generation";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(GENERATION, generation);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		generation = bundle.getInt(GENERATION);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(2, 4);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		if (HP >= damage + 2) {
			ArrayList<Integer> candidates = new ArrayList<Integer>();
			boolean[] passable = Level.passable;

			int[] neighbours = { pos + 1, pos - 1, pos + Level.getWidth(),
					pos - Level.getWidth() };
			for (int n : neighbours) {
				if (passable[n] && Actor.findChar(n) == null) {
					candidates.add(n);
				}
			}

			if (candidates.size() > 0) {

				Swarm clone = split();
				clone.HP = (HP - damage) / 2;
				clone.pos = Random.element(candidates);
				clone.state = clone.HUNTING;

				if (Dungeon.level.map[clone.pos] == Terrain.DOOR) {
					Door.enter(clone.pos);
				}

				GameScene.add(clone, SPLIT_DELAY);
				Actor.addDelayed(new Pushing(clone, pos, clone.pos), -1);

				HP -= clone.HP;
			}
		}

		return damage;
	}

	@Override
	public int attackSkill(Char target) {
		return 12;
	}

	@Override
	public String defenseVerb() {
		return "evaded";
	}

	private Swarm split() {
		Swarm clone = new Swarm();
		clone.generation = generation + 1;
		if (buff(Burning.class) != null) {
			Buff.affect(clone, Burning.class).reignite(clone);
		}
		if (buff(Poison.class) != null) {
			Buff.affect(clone, Poison.class).set(2);
		}
		return clone;
	}

	@Override
	public void die(Object cause) {
		// sets drop chance
		lootChance = 0.5f / ((5 + Dungeon.limitedDrops.swarmHP.count) / (generation + 1));
		super.die(cause);
	}

	@Override
	protected Item createLoot() {
		Dungeon.limitedDrops.swarmHP.count++;
		if (Dungeon.limitedDrops.swarmHP.count>5){ Dungeon.limitedDrops.swarmHP.count=5; }
		return super.createLoot();
	}

	@Override
	public String description() {
		return "The deadly swarm of flies buzzes angrily. Every non-magical attack "
				+ "will split it into two smaller but equally dangerous swarms.";
	}
}
