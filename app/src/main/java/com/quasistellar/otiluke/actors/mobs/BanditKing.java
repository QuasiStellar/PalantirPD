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

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.buffs.Blindness;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.CountDown;
import com.quasistellar.otiluke.actors.buffs.Cripple;
import com.quasistellar.otiluke.actors.buffs.Poison;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.weapon.melee.Spork;
import com.quasistellar.otiluke.sprites.BanditKingSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.utils.Random;

public class BanditKing extends Thief {

	public Item item;

	{
		name = "shadow bandit";
		spriteClass = BanditKingSprite.class;
		HP = HT = 200; //200

		EXP = 10;
		maxLvl = 25;
		flying = true;
		
		// 1 in 30 chance to be a crazy bandit, equates to overall 1/90 chance.
		lootChance = 0.333f;
		defenseSkill = 20; //20
		if (Dungeon.depth<25){Dungeon.sporkAvail = false;}
		
	}
	
	@Override
	public int dr() {
		return 20; //20
	}

	@Override
	public float speed() {
		return 2f;
   	}
	
	@Override
	public int attackProc(Char enemy, int damage) {
		if(enemy.buff(CountDown.class) == null){
			Buff.affect(enemy, CountDown.class);	
			state = FLEEING;
		}

		return damage;
	}
	
	@Override
	protected boolean steal(Hero hero) {
		if (super.steal(hero)) {

			
			if (Dungeon.depth<25){
			Buff.prolong(hero, Blindness.class, Random.Int(5, 12));
			Buff.affect(hero, Poison.class).set(
					Random.Int(5, 7) * Poison.durationFactor(enemy));
			Buff.prolong(hero, Cripple.class, Cripple.DURATION);
			Dungeon.observe();
			} else if(hero.buff(CountDown.class) == null){
				Buff.affect(enemy, CountDown.class);			
		    }	

			return true;
		} else {
			return false;
		}
	}

	@Override
	public void die(Object cause) {
		super.die(cause);
		if (Dungeon.depth<25){
		yell("Fine! Take it back!");
		GLog.n("Shadow Bandit dissolves away.");
		if (!Dungeon.limitedDrops.spork.dropped()) {
			Dungeon.level.drop(new Spork(), pos).sprite.drop();
			Dungeon.limitedDrops.spork.drop();
			Dungeon.sporkAvail = false;
		yell("Doh! Dropped my spork!");	
		}
	  }
	}
}
