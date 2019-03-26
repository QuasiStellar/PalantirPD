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
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.actors.buffs.Amok;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Burning;
import com.quasistellar.otiluke.actors.buffs.Charm;
import com.quasistellar.otiluke.actors.buffs.Poison;
import com.quasistellar.otiluke.actors.buffs.Sleep;
import com.quasistellar.otiluke.actors.buffs.Terror;
import com.quasistellar.otiluke.actors.buffs.Vertigo;
import com.quasistellar.otiluke.actors.buffs.Weakness;
import com.quasistellar.otiluke.actors.mobs.npcs.Imp;
import com.quasistellar.otiluke.items.Generator;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.food.Meat;
import com.quasistellar.otiluke.items.potions.PotionOfHealing;
import com.quasistellar.otiluke.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.otiluke.items.weapon.Weapon;
import com.quasistellar.otiluke.items.weapon.enchantments.Death;
import com.quasistellar.otiluke.items.weapon.enchantments.Leech;
import com.quasistellar.otiluke.items.weapon.melee.relic.RelicMeleeWeapon;
import com.quasistellar.otiluke.items.weapon.missiles.JupitersWraith;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.mechanics.Ballistica;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.CharSprite;
import com.quasistellar.otiluke.sprites.OtilukeSprite;
import com.quasistellar.otiluke.sprites.WarlockSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.quasistellar.otiluke.windows.WndDescend;
import com.quasistellar.otiluke.windows.WndOtilukeMessage;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Otiluke extends Mob implements Callback {

	private static final float TIME_TO_ZAP = 1f;

	private static final String TXT_SHADOWBOLT_KILLED = "%s's shadow bolt killed you...";

	{
		name = "stone golem Otiluke";
		spriteClass = OtilukeSprite.class;

		HP = HT = 7000+(adj(0)*Random.NormalIntRange(5, 7));
		defenseSkill = 50+adj(0);
		
		state=PASSIVE;

		EXP = 101;
		
		loot = Generator.Category.POTION;
		lootChance = 0.83f;
		
	}	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(250, 250+adj(0));
	}

	@Override
	public int attackSkill(Char target) {
		return 250+adj(0);
	}

	@Override
	public int dr() {
		return 8+adj(1);
	}
	
	@Override
	public void notice() {
		super.notice();
		yell("Arise my protectors!");
	}
	
	@Override
	public void damage(int dmg, Object src) {

		if (state == PASSIVE) {
			state = HUNTING;
		}
		
		if(state==HUNTING){
			
			for (Mob mob : Dungeon.level.mobs) {
				if (mob != null && mob instanceof MineSentinel &&  Random.Int(20)<2) {
					if (mob.state==PASSIVE){
						mob.damage(1, this);
						mob.state = HUNTING;
					}
					break;
			}			
		  }
		}
		
		if(!(src instanceof RelicMeleeWeapon || src instanceof JupitersWraith)){
			int max = Math.round(dmg*.25f);
			dmg = Random.Int(1,max);
		}

		super.damage(dmg, src);
	}
	
	

	
	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.adjacent(pos, enemy.pos)) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((OtilukeSprite) sprite).zap(enemy.pos);
			} else {
				zap();
			}

			return !visible;
		}
	}

	private void zap() {
		spend(TIME_TO_ZAP);

		if (hit(this, enemy, true)) {
			if (enemy == Dungeon.hero && Random.Int(2) == 0) {
				Buff.prolong(enemy, Weakness.class, Weakness.duration(enemy));
			}

			int dmg = Random.Int(100, 160+adj(0));
			enemy.damage(dmg, this);

			if (!enemy.isAlive() && enemy == Dungeon.hero) {
				Dungeon.fail(Utils.format(ResultDescriptions.MOB,
						Utils.indefinite(name)));
				GLog.n(TXT_SHADOWBOLT_KILLED, name);
			}
		} else {
			enemy.sprite.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
		}
	}

	public void onZapComplete() {
		zap();
		next();
	}

	@Override
	public void call() {
		next();
	}
	

	@Override
	public void die(Object cause) {
		Dungeon.level.locked=false;
		super.die(cause);
	}

	@Override
	public String description() {
		return "powerful magic has turned Otiluke into a guardian of this level";
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(Death.class);
		IMMUNITIES.add(Leech.class);
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
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}}
