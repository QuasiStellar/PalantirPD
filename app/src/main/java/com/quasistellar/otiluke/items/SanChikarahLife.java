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
package com.quasistellar.otiluke.items;

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;

public class SanChikarahLife extends Item {

	{
		name = "SanChikarah of Life";
		image = ItemSpriteSheet.SANCHIKARAH;

		stackable = false;
		unique = true;
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}


	@Override
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)) {

			if (Dungeon.level != null && Dungeon.depth==32) {
				Dungeon.sanchikarahlife= true;							
			}

			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int price() {
		return 10 * quantity;
	}

	@Override
	public String info() {
		return "A powerful piece of the puzzle. Three pieces will need to be forged together. ";
	}
}
