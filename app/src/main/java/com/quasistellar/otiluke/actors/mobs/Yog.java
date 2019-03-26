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

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.blobs.Blob;
import com.quasistellar.otiluke.actors.blobs.Fire;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.actors.buffs.Amok;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Burning;
import com.quasistellar.otiluke.actors.buffs.Charm;
import com.quasistellar.otiluke.actors.buffs.Ooze;
import com.quasistellar.otiluke.actors.buffs.Poison;
import com.quasistellar.otiluke.actors.buffs.Roots;
import com.quasistellar.otiluke.actors.buffs.Sleep;
import com.quasistellar.otiluke.actors.buffs.Terror;
import com.quasistellar.otiluke.actors.buffs.Vertigo;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.Pushing;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.effects.particles.ShadowParticle;
import com.quasistellar.otiluke.items.Gold;
import com.quasistellar.otiluke.items.OtilukesJournal;
import com.quasistellar.otiluke.items.keys.SkeletonKey;
import com.quasistellar.otiluke.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.otiluke.items.weapon.enchantments.Death;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.mechanics.Ballistica;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.BurningFistSprite;
import com.quasistellar.otiluke.sprites.CharSprite;
import com.quasistellar.otiluke.sprites.InfectingFistSprite;
import com.quasistellar.otiluke.sprites.LarvaSprite;
import com.quasistellar.otiluke.sprites.PinningFistSprite;
import com.quasistellar.otiluke.sprites.RottingFistSprite;
import com.quasistellar.otiluke.sprites.YogSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.utils.Random;

public class Yog extends Mob {

	{
		name = "Yog-Dzewa";
		spriteClass = YogSprite.class;

		HP = HT = 2000;

		EXP = 50;

		state = PASSIVE;
	}
	
	private static final int REGENERATION = 200;

	private static final String TXT_DESC = "Yog-Dzewa is an Old God, a powerful entity from the realms of chaos. A century ago, the ancient dwarves "
			+ "barely won the war against its army of demons, but were unable to kill the god itself. Instead, they then "
			+ "imprisoned it in the halls below their city, believing it to be too weak to rise ever again.";

	private static int fistsCount = 0;

	public Yog() {
		super();
	}

	public void spawnFists() {
		RottingFist fist1 = new RottingFist();
		BurningFist fist2 = new BurningFist();
		PinningFist fist3 = new PinningFist();
		InfectingFist fist4 = new InfectingFist();

		
			fist1.pos = Dungeon.level.randomRespawnCellMob();
			fist2.pos = Dungeon.level.randomRespawnCellMob();
			fist3.pos = Dungeon.level.randomRespawnCellMob();
			fist4.pos = Dungeon.level.randomRespawnCellMob();
	
		GameScene.add(fist1);
		GameScene.add(fist2);
		GameScene.add(fist3);
		GameScene.add(fist4);
	}

	@Override
	public int dr() {
		
		int checkFists = 0;
		
		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof BurningFist || mob instanceof RottingFist || mob instanceof PinningFist || mob instanceof InfectingFist) {
			checkFists++;	
			}
		}
		
		return 10+(30*checkFists);
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(54, 96);
	}
	
	@Override
	public void damage(int dmg, Object src) {

				
		if (HP<(HT/8) && Random.Float() < 0.50f && dmg<HP){
			int newPos = -1;
				for (int i = 0; i < 20; i++) {
				newPos = Dungeon.level.randomRespawnCellMob();
				if (newPos != -1) {
					break;
				}
			}
			if (newPos != -1) {
				Actor.freeCell(pos);
				CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
				pos = newPos;
				sprite.place(pos);
				sprite.visible = Dungeon.visible[pos];
				GLog.n("Yog vanishes!");
			}		
					
			if (Dungeon.level.mobs.size()<5){
			Eye.spawnAroundChance(newPos);
			}
			
			if (fistsCount==0){
				spawnFists();
				sprite.emitter().burst(ShadowParticle.UP, 2);
				HP += REGENERATION;
			}
		}

		super.damage(dmg, src);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {

		ArrayList<Integer> spawnPoints = new ArrayList<Integer>();

		for (int i = 0; i < Level.NEIGHBOURS8.length; i++) {
			int p = pos + Level.NEIGHBOURS8[i];
			if (Actor.findChar(p) == null
					&& (Level.passable[p] || Level.avoid[p])) {
				spawnPoints.add(p);
			}
		}

		if (spawnPoints.size() > 0) {
			Larva larva = new Larva();
			larva.pos = Random.element(spawnPoints);

			GameScene.add(larva);
			Actor.addDelayed(new Pushing(larva, pos, larva.pos), -1);
		}

		for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof BurningFist || mob instanceof RottingFist || mob instanceof InfectingFist || mob instanceof PinningFist
					|| mob instanceof Larva) {
				mob.aggro(enemy);
			}
		}

		return super.defenseProc(enemy, damage);
	}

	@Override
	public void beckon(int cell) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {

		for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
			if (mob instanceof BurningFist || mob instanceof RottingFist || mob instanceof Eye || mob instanceof PinningFist || mob instanceof InfectingFist) {
				mob.die(cause);
			}
		}
		
		if (!Dungeon.limitedDrops.journal.dropped()){ 
			  Dungeon.level.drop(new OtilukesJournal(), pos).sprite.drop();
			  Dungeon.limitedDrops.journal.drop();
			}

		GameScene.bossSlain();
		Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();
		//Dungeon.level.drop(new Gold(Random.Int(6000, 8000)), pos).sprite.drop();
		super.die(cause);

		yell("Back to the shadow...");
	}

	@Override
	public void notice() {
		super.notice();
		yell("Hope is an illusion...");
	}

	@Override
	public String description() {
		return TXT_DESC;

	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {

		IMMUNITIES.add(Death.class);
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Charm.class);
		IMMUNITIES.add(Sleep.class);
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(ScrollOfPsionicBlast.class);
		IMMUNITIES.add(Vertigo.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}

	public static class RottingFist extends Mob {

		private static final int REGENERATION = 50;

		{
			name = "rotting fist";
			spriteClass = RottingFistSprite.class;

			HP = HT = 2000;
			defenseSkill = 25;

			EXP = 0;

			state = WANDERING;
		}

		public RottingFist() {
			super();
			fistsCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			fistsCount--;
		}

		@Override
		public int attackSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(44, 86);
		}

		@Override
		public int dr() {
			return 35;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(3) == 0) {
				Buff.affect(enemy, Ooze.class);
				enemy.sprite.burst(0xFF000000, 5);
			}

			return damage;
		}

		@Override
		public boolean act() {

			if (Level.water[pos] && HP < HT) {
				sprite.emitter().burst(ShadowParticle.UP, 2);
				HP += REGENERATION;
			}

			return super.act();
		}

		@Override
		public String description() {
			return TXT_DESC;

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
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Poison.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ToxicGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	public static class BurningFist extends Mob {

		{
			name = "burning fist";
			spriteClass = BurningFistSprite.class;

			HP = HT = 1000;
			defenseSkill = 25;

			EXP = 0;

			state = WANDERING;
		}

		public BurningFist() {
			super();
			fistsCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			fistsCount--;
		}

		@Override
		public int attackSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(40, 52);
		}

		@Override
		public int dr() {
			return 25;
		}

		@Override
		protected boolean canAttack(Char enemy) {
			return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
		}

		@Override
		public boolean attack(Char enemy) {

			if (!Level.adjacent(pos, enemy.pos)) {
				spend(attackDelay());

				if (hit(this, enemy, true)) {

					int dmg = damageRoll();
					enemy.damage(dmg, this);

					enemy.sprite.bloodBurstA(sprite.center(), dmg);
					enemy.sprite.flash();

					if (!enemy.isAlive() && enemy == Dungeon.hero) {
						Dungeon.fail(Utils.format(ResultDescriptions.UNIQUE,
								name));
						GLog.n(TXT_KILL, name);
					}
					return true;

				} else {

					enemy.sprite.showStatus(CharSprite.NEUTRAL,
							enemy.defenseVerb());
					return false;
				}
			} else {
				return super.attack(enemy);
			}
		}

		@Override
		public boolean act() {

			for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
				GameScene.add(Blob.seed(pos + Level.NEIGHBOURS9[i], 2,
						Fire.class));
			}

			return super.act();
		}

		@Override
		public String description() {
			return TXT_DESC;

		}

		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
		static {
			RESISTANCES.add(ToxicGas.class);
			RESISTANCES.add(Death.class);

		}

		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Burning.class);
			IMMUNITIES.add(ScrollOfPsionicBlast.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ToxicGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	
	public static class InfectingFist extends Mob {

		

		{
			name = "infecting fist";
			spriteClass = InfectingFistSprite.class;

			HP = HT = 2000;
			defenseSkill = 25;

			EXP = 0;

			state = WANDERING;
		}

		public InfectingFist() {
			super();
			fistsCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			fistsCount--;
		}

		@Override
		public int attackSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(44, 86);
		}

		@Override
		public int dr() {
			return 35;
		}

		@Override
		public int attackProc(Char enemy, int damage) {
			if (Random.Int(2) == 0) {
				Buff.affect(enemy, Poison.class).set(Random.Int(7, 9) * Poison.durationFactor(enemy));
				state = FLEEING;
			}		

			return damage;
		}

		@Override
		public boolean act() {
					
			GameScene.add(Blob.seed(pos, 30, ToxicGas.class));

			return super.act();
		}

		@Override
		public String description() {
			return TXT_DESC;

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
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Poison.class);
			IMMUNITIES.add(Vertigo.class);
			IMMUNITIES.add(ToxicGas.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	
	public static class PinningFist extends Mob {

		{
			name = "pinning fist";
			spriteClass = PinningFistSprite.class;

			HP = HT = 1000;
			defenseSkill = 25;

			EXP = 0;

			state = WANDERING;
		}

		public PinningFist() {
			super();
			fistsCount++;
		}

		@Override
		public void die(Object cause) {
			super.die(cause);
			fistsCount--;
		}

		@Override
		public int attackSkill(Char target) {
			return 36;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(30, 42);
		}

		@Override
		public int dr() {
			return 25;
		}

		@Override
		protected boolean canAttack(Char enemy) {
			return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
		}
		
		@Override
		protected boolean getCloser(int target) {
			if (state == HUNTING) {
				return enemySeen && getFurther(target);
			} else {
				return super.getCloser(target);
			}
		}

		@Override
		public boolean attack(Char enemy) {

			if (!Level.adjacent(pos, enemy.pos)) {
				spend(attackDelay());

				if (hit(this, enemy, true)) {

					int dmg = damageRoll();
					enemy.damage(dmg, this);
					
					if(Random.Int(10)==0){
						Buff.prolong(enemy, Roots.class, 20);
			  		}

					enemy.sprite.bloodBurstA(sprite.center(), dmg);
					enemy.sprite.flash();

					if (!enemy.isAlive() && enemy == Dungeon.hero) {
						Dungeon.fail(Utils.format(ResultDescriptions.UNIQUE,
								name));
						GLog.n(TXT_KILL, name);
					}
					return true;

				} else {

					enemy.sprite.showStatus(CharSprite.NEUTRAL,
							enemy.defenseVerb());
					return false;
				}
			} else {
				return super.attack(enemy);
			}
		}

		@Override
		public String description() {
			return TXT_DESC;

		}

		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
		static {
			RESISTANCES.add(ToxicGas.class);
			RESISTANCES.add(Death.class);

		}

		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}

		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add(Amok.class);
			IMMUNITIES.add(Sleep.class);
			IMMUNITIES.add(Terror.class);
			IMMUNITIES.add(Burning.class);
			IMMUNITIES.add(ScrollOfPsionicBlast.class);
			IMMUNITIES.add(Vertigo.class);
		}

		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}

	public static class Larva extends Mob {

		{
			name = "god's larva";
			spriteClass = LarvaSprite.class;

			HP = HT = 25;
			defenseSkill = 20;

			EXP = 0;

			state = HUNTING;
		}

		@Override
		public int attackSkill(Char target) {
			return 30;
		}

		@Override
		public int damageRoll() {
			return Random.NormalIntRange(15, 20);
		}

		@Override
		public int dr() {
			return 8;
		}

		@Override
		public String description() {
			return TXT_DESC;

		}
	}
}
