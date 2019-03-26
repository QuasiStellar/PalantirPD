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
package com.quasistellar.otiluke.actors.mobs;

import java.util.ArrayList;
import java.util.HashSet;

import com.quasistellar.otiluke.Badges;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.blobs.Blob;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.actors.blobs.Web;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Burning;
import com.quasistellar.otiluke.actors.buffs.Poison;
import com.quasistellar.otiluke.actors.buffs.Roots;
import com.quasistellar.otiluke.actors.buffs.Terror;
import com.quasistellar.otiluke.effects.Pushing;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.items.Gold;
import com.quasistellar.otiluke.items.keys.SkeletonKey;
import com.quasistellar.otiluke.items.potions.PotionOfMending;
import com.quasistellar.otiluke.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.otiluke.items.weapon.enchantments.Death;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.SewerBossLevel;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.levels.features.Door;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.PoisonGooSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class PoisonGoo extends Mob {
	
protected static final float SPAWN_DELAY = 2f;

private boolean gooSplit = false;

private int gooGeneration = 0;
private int goosAlive = 0;

private static final String GOOGENERATION = "gooGeneration";

	{
		name = "Goo";
		HP = HT = 50;
		EXP = 10;
		defenseSkill = 12;
		spriteClass = PoisonGooSprite.class;
		baseSpeed = 2f;

		loot = new PotionOfMending();
		lootChance = 1f;
		FLEEING = new Fleeing();
	}

	private static final float SPLIT_DELAY = 1f;	
	
	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Poison.class) == null) {
			state = HUNTING;
		}
		if (Level.water[pos] && HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP++;
		} else if(Level.water[pos] && HP == HT && HT < 100){
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HT=HT+5;
			HP=HT;
		}
		return result;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(1) == 0) {
			Buff.affect(enemy, Poison.class).set(
					Random.Int(7, 10) * Poison.durationFactor(enemy));
			state = FLEEING;
		}

		return damage;
	}

	@Override
	public void move(int step) {
		if (state == FLEEING) {
			GameScene.add(Blob.seed(pos, Random.Int(7, 10), Web.class));
		}
		super.move(step);
	}
	
	@Override
	public int damageRoll() {
			return Random.NormalIntRange(1, 10);
	}

	@Override
	public int attackSkill(Char target) {
		return 5;
	}

	@Override
	public int dr() {
		return 2;
	}

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(GOOGENERATION, gooGeneration);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		gooGeneration = bundle.getInt(GOOGENERATION);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		gooSplit = false;   
		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof Goo) {
				gooSplit = true;
			}
		}
		if (HP >= damage + 2 && gooSplit) {
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
				GLog.n("Mini Goo divides!");
				PoisonGoo clone = split();
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


	private PoisonGoo split() {
		PoisonGoo clone = new PoisonGoo();
		clone.gooGeneration = gooGeneration + 1;
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
		
		if (gooGeneration > 0){
		lootChance = 0;
		}

		super.die(cause);
		   
		for (Mob mob : Dungeon.level.mobs) {
		
		if (mob instanceof Goo || mob instanceof PoisonGoo){
			   goosAlive++;
			 }
		
		}
		
		 if(goosAlive==0){
		   ((SewerBossLevel) Dungeon.level).unseal();

			GameScene.bossSlain();
			Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();

			Dungeon.level.drop(new Gold(Random.Int(900, 2000)), pos).sprite.drop();

		} else {

		Dungeon.level.drop(new Gold(Random.Int(100, 200)), pos).sprite.drop();
		}
		
		yell("glurp... glurp...");
	}

	@Override
	public void notice() {
		super.notice();
		yell("GLURP-GLURP!");
	}

	@Override
	public String description() {
		return "Little is known about The Goo. It's quite possible that it is not even a creature, but rather a "
				+ "conglomerate of vile substances from the sewers that somehow gained basic intelligence. "
				+ "Regardless, dark magic is certainly what has allowed Goo to exist.\n\n"
				+ "Its gelatinous nature has let it absorb lots of dark energy, you feel a chill just from being near. "
				+ "If goo is able to attack with this energy you won't live for long.";
	}

	

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

	static {
		IMMUNITIES.add(Roots.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
	
	
	public static void spawnAround(int pos) {
		for (int n : Level.NEIGHBOURS4) {
			GLog.n("Goo squeezes!");
			int cell = pos + n;
			if (Level.passable[cell] && Actor.findChar(cell) == null) {
				spawnAt(cell);
				GLog.n("Goo creates mini goo!");
			}
		}
	}
	
	public static PoisonGoo spawnAt(int pos) {
		
        PoisonGoo b = new PoisonGoo();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
	
	
}
