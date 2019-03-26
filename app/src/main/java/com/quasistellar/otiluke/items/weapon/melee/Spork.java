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
package com.quasistellar.otiluke.items.weapon.melee;

import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.mobs.Gullin;
import com.quasistellar.otiluke.actors.mobs.Kupua;
import com.quasistellar.otiluke.actors.mobs.MineSentinel;
import com.quasistellar.otiluke.actors.mobs.Otiluke;
import com.quasistellar.otiluke.actors.mobs.Zot;
import com.quasistellar.otiluke.actors.mobs.ZotPhase;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Spork extends MeleeWeapon {

	{
		name = "spork";
		image = ItemSpriteSheet.SPORK;
		reinforced=true;
	}

	public Spork() {
		super(3, 10, 1.2f, 0.25f, 2, 20);
	}


	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
		if (defender instanceof Gullin 
        		|| defender instanceof Kupua
        		|| defender instanceof MineSentinel
        		|| defender instanceof Otiluke
        		|| defender instanceof Zot
        		|| defender instanceof ZotPhase){
        	
        	//damage*=2;
			
			defender.damage(Random.Int(damage,damage*4), this);
		}
        
		
		if (enchantment != null) {
			enchantment.proc(this, attacker, defender, damage);		
		}
	}
	
	@Override
	public String desc() {
		return "Is it a spoon? Is it a fork? It is neither and it is both. "
				+"Mobs better respect!";
	}
}
