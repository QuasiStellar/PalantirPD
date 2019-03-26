/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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

import com.quasistellar.otiluke.sprites.ItemSpriteSheet;

public class InactiveMrDestructo2 extends Item {

	{
		name = "inactive mr destructo 2.0";
		image = ItemSpriteSheet.INACTIVEMRD2;
		stackable = true;
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
	public String info() {
		return "Mr Destructo has gone inactive. Maybe there is a way to recharge him.";
	}

	
		
	
}
