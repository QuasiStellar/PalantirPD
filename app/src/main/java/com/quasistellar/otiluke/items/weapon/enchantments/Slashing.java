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
package com.quasistellar.otiluke.items.weapon.enchantments;

import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.items.weapon.Weapon;
import com.quasistellar.otiluke.items.weapon.melee.relic.RelicMeleeWeapon;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.sprites.ItemSprite;
import com.quasistellar.otiluke.sprites.ItemSprite.Glowing;

public class Slashing extends Weapon.Enchantment {

	private static final String TXT_SLASHING = "Slashing %s";

	private static ItemSprite.Glowing GREEN = new ItemSprite.Glowing(0x00FF00);
	
	@Override
	public boolean proc(RelicMeleeWeapon weapon, Char attacker, Char defender, int damage) {
		return false;
	}

	private int[] points = new int[2];
	private int nPoints;

	
	public static final int[] NEIGHBOURS8 = { +1, 
		                                      -1, 
		                                       +Level.getWidth(),
		                                       -Level.getWidth(),
		                                       
		                                       +1 + Level.getWidth(), 
		                                       +1 - Level.getWidth(), 
		                                       -1 + Level.getWidth(), 
		                                       -1 - Level.getWidth() };
	
	/*  -W-1 -W  -W+1
	 *  -1    P  +1
	 *  W-1   W  W+1
	 * 
	 */

	@Override
	public boolean proc(Weapon weapon, Char attacker, Char defender, int damage) {
		int level = Math.max(0, weapon.level);

		if (defender.pos-attacker.pos == 1){
			
	//		points[0] = [attacker.pos-Level.W]
			
		} else if (defender.pos-attacker.pos == -1) {
			
			
		} else if (defender.pos-attacker.pos == Level.getWidth()) {
			
			
		} else if (defender.pos-attacker.pos == -Level.getWidth()) {
			
			
		} else if (defender.pos-attacker.pos == Level.getWidth()+1) {
			
			
		} else if (defender.pos-attacker.pos == Level.getWidth()-1) {
			
			
		}else if (defender.pos-attacker.pos == -Level.getWidth()-1) {
			
			
		}else if (defender.pos-attacker.pos == -Level.getWidth()+1) {
			
			
		}else  {
			
			
		}
			
		
		int dmg = damage;
		for (int i = 1; i <= level + 1; i++) {
			dmg = Math.max(dmg, attacker.damageRoll() - i);
		}

		if (dmg > damage) {
			defender.damage(dmg - damage, this);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String name(String weaponName) {
		return String.format(TXT_SLASHING, weaponName);
	}

	@Override
	public Glowing glowing() {
		return GREEN;
	}
}
