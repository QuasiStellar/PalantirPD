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
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.Blob;
import com.quasistellar.palantir.actors.blobs.ToxicGas;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Paralysis;
import com.quasistellar.palantir.actors.buffs.Terror;
import com.quasistellar.palantir.effects.CellEmitter;
import com.quasistellar.palantir.effects.Speck;
import com.quasistellar.palantir.effects.particles.ElmoParticle;
import com.quasistellar.palantir.effects.particles.SparkParticle;
import com.quasistellar.palantir.items.OtilukesJournal;
import com.quasistellar.palantir.items.artifacts.CapeOfThorns;
import com.quasistellar.palantir.items.journalpages.Sokoban3;
import com.quasistellar.palantir.items.keys.SkeletonKey;
import com.quasistellar.palantir.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.palantir.items.weapon.enchantments.Death;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.levels.Terrain;
import com.quasistellar.palantir.levels.traps.LightningTrap;
import com.quasistellar.palantir.mechanics.Ballistica;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.CharSprite;
import com.quasistellar.palantir.sprites.DM300Sprite;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class DM300 extends Mob implements Callback {
	
	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		name = "DM-300";
		spriteClass = DM300Sprite.class;

		HP = HT = 500;
		EXP = 30;
		defenseSkill = 40;

		loot = new CapeOfThorns().identify();
		lootChance = 0.333f;
	}

	private int bossAlive = 0;
	private int towerAlive = 1;
	
	@Override
	public int damageRoll() {
		
		return Random.NormalIntRange(18, 24)*towerAlive;
	}

	@Override
	public int attackSkill(Char target) {
		return 28;
	}

	@Override
	public int dr() {
		return 10+(4*towerAlive);
	}

	@Override
	public boolean act() {
		towerAlive = 1;
        for (Mob mob : Dungeon.level.mobs) {
			
			if (mob instanceof Tower){
				   towerAlive++;
				 }
			}
        
		GameScene.add(Blob.seed(pos, 30, ToxicGas.class));

		return super.act();
	}
	
	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}

	
	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.distance(pos, enemy.pos) <= 1) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((DM300Sprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(10, 22);
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, LightningTrap.LIGHTNING);

				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail(Utils.format(ResultDescriptions.MOB,
								Utils.indefinite(name)));
						GLog.n(TXT_LIGHTNING_KILLED, name);
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}

			return !visible;
		}
	}

	
	@Override
	public void move(int step) {
		super.move(step);

		if (Dungeon.level.map[step] == Terrain.INACTIVE_TRAP && HP < HT) {

			HP += Random.Int(1, HT - HP);
			sprite.emitter().burst(ElmoParticle.FACTORY, 5);

			if (Dungeon.visible[step] && Dungeon.hero.isAlive()) {
				GLog.n("DM-300 repairs itself!");
			}
		}

		int[] cells = { step - 1, step + 1, step - Level.getWidth(),
				step + Level.getWidth(), step - 1 - Level.getWidth(),
				step - 1 + Level.getWidth(), step + 1 - Level.getWidth(),
				step + 1 + Level.getWidth() };
		int cell = cells[Random.Int(cells.length)];

		if (Dungeon.visible[cell]) {
			CellEmitter.get(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
			Camera.main.shake(3, 0.7f);
			Sample.INSTANCE.play(Assets.SND_ROCKS);

			if (Level.water[cell]) {
				GameScene.ripple(cell);
			} else if (Dungeon.level.map[cell] == Terrain.EMPTY) {
				Level.set(cell, Terrain.EMPTY_DECO);
				GameScene.updateMap(cell);
			}
		}

		Char ch = Actor.findChar(cell);
		if (ch != null && ch != this) {
			Buff.prolong(ch, Paralysis.class, 2);
		}
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

           for (Mob mob : Dungeon.level.mobs) {
			
			  if (mob instanceof Tower){
				   bossAlive++;
				   }
			
			}
			
			 if(bossAlive==0){
				 
					GameScene.bossSlain();
					Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
			 }
			 
			 if (!Dungeon.limitedDrops.journal.dropped()){ 
				  Dungeon.level.drop(new OtilukesJournal(), pos).sprite.drop();
				  Dungeon.limitedDrops.journal.drop();
				}
				
				Dungeon.level.drop(new Sokoban3(), pos).sprite.drop();
		       

		yell("Mission failed. Shutting down.");
	}

	@Override
	public void notice() {
		super.notice();
		yell("Unauthorised personnel detected.");
	}
	
	@Override
	public void call() {
		next();
	}
		
	@Override
	public String description() {
		return "This machine was created by the Dwarves several centuries ago. Later, Dwarves started to replace machines with "
				+ "golems, elementals and even demons. Eventually it led their civilization to the decline. The DM-300 and similar "
				+ "machines were typically used for construction and mining, and in some cases, for city defense.";
	}

	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(Terror.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	
}
