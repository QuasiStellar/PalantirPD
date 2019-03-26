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

import java.util.HashSet;

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.Statistics;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.actors.buffs.Amok;
import com.quasistellar.otiluke.actors.buffs.Burning;
import com.quasistellar.otiluke.actors.buffs.Charm;
import com.quasistellar.otiluke.actors.buffs.Sleep;
import com.quasistellar.otiluke.actors.buffs.Terror;
import com.quasistellar.otiluke.actors.buffs.Vertigo;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.items.OrbOfZot;
import com.quasistellar.otiluke.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.otiluke.items.weapon.enchantments.Death;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.ShadowYogSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.utils.Random;

public class ShadowYog extends Mob  {
	
	{
		name = "Shadow Yog-Dzewa";
		spriteClass = ShadowYogSprite.class;

		HP = HT = 50*Dungeon.hero.lvl;
		
		baseSpeed = 2f;
		defenseSkill = 32;

		EXP = 100;

		state = PASSIVE;
	}
	
	private int yogsAlive = 0;
	
	private static final String TXT_DESC =  "Yog has retreated to his den in Shadow form."
			                               +"The legion of Yog is being fed strength from the mobs in the den, ";

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(45, 125);
	}

	@Override
	public int attackSkill(Char target) {
		return 50;
	}

	public ShadowYog() {
		super();
	}

	@Override
	public int dr() {
		return (Dungeon.level.mobs.size());
	}
	
	@Override
	public void damage(int dmg, Object src) {

			//for (Mob mob : Dungeon.level.mobs) {
			 //	mob.beckon(pos);
			//	}
			
			for (int i = 0; i < 4; i++) {
				int trapPos;
				do {
					trapPos = Random.Int(Level.getLength());
				} while (!Level.fieldOfView[trapPos] || !Level.passable[trapPos]);

				if (Dungeon.level.map[trapPos] == Terrain.INACTIVE_TRAP) {
					Level.set(trapPos, Terrain.SECRET_SUMMONING_TRAP);
					GameScene.updateMap(trapPos);
				}
			}
			
			if (HP<(HT/8) && Random.Float() < 0.5f){
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
					GLog.n("Shadow Yog vanishes!");
				}		
				if (Dungeon.level.mobs.size()<Dungeon.hero.lvl*2){
				SpectralRat.spawnAroundChance(newPos);
				}
			}
			
			super.damage(dmg, src);
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
                return super.defenseProc(enemy, damage);
	}
	

	
	@Override
	public void beckon(int cell) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void die(Object cause) {
		super.die(cause);
		
		Statistics.shadowYogsKilled++;

      for (Mob mob : Dungeon.level.mobs) {
			
			if (mob instanceof ShadowYog){
				   yogsAlive++;
				 }
			}
			
		 if(yogsAlive==0){
			GameScene.bossSlain();
			Dungeon.shadowyogkilled=true;
			
			Dungeon.level.drop(new OrbOfZot(), pos).sprite.drop();
			
			for (Mob mob : (Iterable<Mob>) Dungeon.level.mobs.clone()) {
				if (mob instanceof Rat || mob instanceof GreyOni || mob instanceof SpectralRat || mob instanceof Eye) {
					mob.die(cause);
				}
			}
			
			yell("...");
		 }
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

	}
