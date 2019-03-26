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
package com.quasistellar.otiluke.items.food;

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.buffs.BerryRegeneration;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Haste;
import com.quasistellar.otiluke.actors.buffs.Hunger;
import com.quasistellar.otiluke.actors.buffs.Slow;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.mobs.Mob;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.utils.Random;

public class BlueMilk extends Food {

	{
		name = "blue milk mushroom";
		image = ItemSpriteSheet.MUSHROOM_BLUEMILK;
		energy = (Hunger.STARVING - Hunger.HUNGRY)/10;
		message = "Munch munch";
		hornValue = 1;
		bones = false;
	}

	private static final String TXT_PREVENTING = "Something tells you that wouldn't be a good idea here! ";
	private static final String TXT_EFFECT = "You are slipping through the ether! ";

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals(AC_EAT)) {
			
			if (Dungeon.bossLevel()){
				GLog.w(TXT_PREVENTING);
				return;
			}

		}
		
	   if (action.equals(AC_EAT)) {
		   
		   
		   GLog.w(TXT_EFFECT);
			
		   switch (Random.Int(10)) {
			case 1:
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					Buff.affect(mob, Slow.class, Slow.duration(mob));
				}
				Buff.affect(hero, Haste.class, 20f);
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT);
				break;
			case 0: case 2: case 3: case 4: case 5: 
			case 6: case 7: case 8: case 9: case 10:
				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
					Buff.affect(mob, Slow.class, Slow.duration(mob));
					Buff.affect(mob, BerryRegeneration.class).level(mob.HT);
				}
				Buff.affect(hero, Haste.class, 20f);
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT);
				break;
			}
		}
	   
	   super.execute(hero, action);
	}	
	
	@Override
	public String info() {
		return "This mushroom oozes a milky blue substance. "
				+"You feel your senses slipping. ";
	}

	@Override
	public int price() {
		return 20 * quantity;
	}
	
	public BlueMilk() {
		this(1);
	}

	public BlueMilk(int value) {
		this.quantity = value;
	}
}
