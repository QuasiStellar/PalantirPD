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

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.Statistics;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.ToxicGas;
import com.quasistellar.palantir.actors.buffs.Burning;
import com.quasistellar.palantir.actors.buffs.Frost;
import com.quasistellar.palantir.actors.buffs.Invisibility;
import com.quasistellar.palantir.actors.buffs.Paralysis;
import com.quasistellar.palantir.actors.buffs.Roots;
import com.quasistellar.palantir.items.ConchShell;
import com.quasistellar.palantir.items.Generator;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.food.Meat;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.sprites.AlbinoPiranhaSprite;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.utils.Random;

public class AlbinoPiranha extends Mob {
	
	private static final String TXT_KILLCOUNT = "Albino Piranha Kill Count: %s";

	{
		name = "albino giant piranha";
		spriteClass = AlbinoPiranhaSprite.class;

		baseSpeed = 2f;

		EXP = 0;
		
		loot = new Meat();
		lootChance = 0.1f;
		
	}

	public AlbinoPiranha() {
		super();

		HP = HT = 10 + Dungeon.depth * 5;
		defenseSkill = 10 + Dungeon.depth * 2;
	}

	protected boolean checkwater(int cell){
		return Level.water[cell];		
	}
	
		
	@Override
	protected boolean act() {
		
		if (!Level.water[pos]) {
			damage(HT, this);
			//die(null);
			return true;
					
				
		} else {
			// this causes pirahna to move away when a door is closed on them.
			Dungeon.level.updateFieldOfView(this);
			enemy = chooseEnemy();
			if (state == this.HUNTING
					&& !(enemy.isAlive() 
					&& Level.fieldOfView[enemy.pos] 
					&& Level.water[enemy.pos])) {
				state = this.WANDERING;
				int oldPos = pos;
				int i = 0;
				do {
					i++;
					target = Dungeon.level.randomDestination();
					if (i == 100)
						return true;
				} while (!getCloser(target));
				moveSprite(oldPos, pos);
				return true;
			}
			
			if (enemy.invisible>1){
				enemy.remove(Invisibility.class);
				GLog.w("No point being invisible when all the fish are blind!");
			}
			
			if (!Level.water[enemy.pos] || enemy.flying){
				enemy.invisible = 1;
			}	else {enemy.invisible = 0;}	
						
			return super.act();
		}
	}

	
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(Dungeon.depth, 4 + Dungeon.depth * 2);
	}

	@Override
	public int attackSkill(Char target) {
		return 20 + Dungeon.depth * 2;
	}

	@Override
	public int dr() {
		return Dungeon.depth;
	}

	@Override
	public void die(Object cause) {
		explodeDew(pos);
		if(Random.Int(105-Math.min(Statistics.albinoPiranhasKilled,100))==0){
		  Item mushroom = Generator.random(Generator.Category.MUSHROOM);
		  Dungeon.level.drop(mushroom, pos).sprite.drop();	
		}
		
		if(!Dungeon.limitedDrops.conchshell.dropped() && Statistics.albinoPiranhasKilled > 50 && Random.Int(10)==0) {
			Dungeon.limitedDrops.conchshell.drop();
			Dungeon.level.drop(new ConchShell(), pos).sprite.drop();
		}
		
		if(!Dungeon.limitedDrops.conchshell.dropped() && Statistics.albinoPiranhasKilled > 100) {
			Dungeon.limitedDrops.conchshell.drop();
			Dungeon.level.drop(new ConchShell(), pos).sprite.drop();
		}
		
		super.die(cause);

		Statistics.albinoPiranhasKilled++;
		GLog.w(TXT_KILLCOUNT, Statistics.albinoPiranhasKilled);
		//Badges.validatePiranhasKilled();
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	protected boolean getCloser(int target) {

		if (rooted) {
			return false;
		}

		int step = Dungeon.findPath(this, pos, target, Level.water,
				Level.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean getFurther(int target) {
		int step = Dungeon.flee(this, pos, target, Level.water,
				Level.fieldOfView);
		if (step != -1) {
			move(step);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String description() {
		return "These huge blind cave fish inhabitant pools of underground water. "
				+"Vibrations in the water alert them to prey. "
				+ "They look ferocious and hungry.";
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(Paralysis.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(Roots.class);
		IMMUNITIES.add(Frost.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
