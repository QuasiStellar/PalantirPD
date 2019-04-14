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
package com.quasistellar.palantir.levels.features;

import com.quasistellar.palantir.Challenges;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Barkskin;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.hero.HeroSubClass;
import com.quasistellar.palantir.effects.CellEmitter;
import com.quasistellar.palantir.effects.particles.LeafParticle;
import com.quasistellar.palantir.items.Dewdrop;
import com.quasistellar.palantir.items.Generator;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.RedDewdrop;
import com.quasistellar.palantir.items.VioletDewdrop;
import com.quasistellar.palantir.items.YellowDewdrop;
import com.quasistellar.palantir.items.artifacts.SandalsOfNature;
import com.quasistellar.palantir.items.food.Blackberry;
import com.quasistellar.palantir.items.food.Blueberry;
import com.quasistellar.palantir.items.food.Cloudberry;
import com.quasistellar.palantir.items.food.Moonberry;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.levels.Terrain;
import com.quasistellar.palantir.plants.BlandfruitBush;
import com.quasistellar.palantir.plants.Flytrap;
import com.quasistellar.palantir.scenes.GameScene;
import com.watabou.utils.Random;

public class HighGrass {

	public static void trample(Level level, int pos, Char ch) {

		Level.set(pos, Terrain.GRASS);
		GameScene.updateMap(pos);

		if (!Dungeon.isChallenged(Challenges.NO_HERBALISM)) {
			int naturalismLevel = 0;

			if (ch != null) {
				SandalsOfNature.Naturalism naturalism = ch
						.buff(SandalsOfNature.Naturalism.class);
				if (naturalism != null) {
					if (!naturalism.isCursed()) {
						naturalismLevel = naturalism.level() + 1;
						naturalism.charge();
					} else {
						naturalismLevel = -1;
					}
				}
			}

			if (naturalismLevel >= 0) {
				// Seed
				if (Random.Int(18 - ((int) (naturalismLevel * 3.34))) == 0) {
					Item seed = Generator.random(Generator.Category.SEED);

					if (seed instanceof BlandfruitBush.Seed) {
						if (Random.Int(15)
								- Dungeon.limitedDrops.blandfruitSeed.count >= 0) {
							level.drop(seed, pos).sprite.drop();
							Dungeon.limitedDrops.blandfruitSeed.count++;
						}
						
					  }	else if (seed instanceof Flytrap.Seed) {
						if (Random.Int(15)
								- Dungeon.limitedDrops.upgradeEaterSeed.count >= 0) {
							level.drop(seed, pos).sprite.drop();
							Dungeon.limitedDrops.upgradeEaterSeed.count++;
						}
						
					  }else if (seed instanceof Blackberry
								|| seed instanceof Cloudberry
								|| seed instanceof Blueberry
								|| seed instanceof Moonberry								
								) {
							if (Random.Int(40)- Dungeon.limitedDrops.berries.count >= 0) {
								level.drop(seed, pos).sprite.drop();
								Dungeon.limitedDrops.berries.count++;
							}
					} else
						level.drop(seed, pos).sprite.drop();
				}
				
				// Mushroom
				if (Dungeon.growLevel(Dungeon.depth) && Random.Int(40 - ((int) (naturalismLevel * 3.34))) == 0) {
					Item mushroom = Generator.random(Generator.Category.MUSHROOM);
					level.drop(mushroom, pos).sprite.drop();
				}
				
				// Dew
				if (Random.Int(3 - naturalismLevel) == 0) {
					if (Random.Int(30 - naturalismLevel) == 0 && naturalismLevel>0) {
						level.drop(new YellowDewdrop(), pos).sprite.drop();
					} else if (Random.Int(50 - naturalismLevel) == 0 && naturalismLevel>2) {
						level.drop(new RedDewdrop(), pos).sprite.drop();
					} else if (Random.Int(100 - naturalismLevel) == 0 && naturalismLevel>4){
						level.drop(new VioletDewdrop(), pos).sprite.drop();
					} else {
					   level.drop(new Dewdrop(), pos).sprite.drop();
					}
				}
			}
		}

		int leaves = 4;

		// Barkskin
		if (ch instanceof Hero && ((Hero) ch).subClass == HeroSubClass.WARDEN) {
			Buff.affect(ch, Barkskin.class).level(ch.HT / 3);
			leaves = 8;
		}

		CellEmitter.get(pos).burst(LeafParticle.LEVEL_SPECIFIC, leaves);
		Dungeon.observe();
	}
}
