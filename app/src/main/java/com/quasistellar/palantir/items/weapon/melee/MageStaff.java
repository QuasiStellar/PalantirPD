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
package com.quasistellar.palantir.items.weapon.melee;

import com.quasistellar.palantir.sprites.ItemSpriteSheet;

public class MageStaff extends MeleeWeapon {

	{
		name = "magic staff";
		image = ItemSpriteSheet.MAGICSTAFF;
	}

	public MageStaff() {
		super(2, 10, 1f, 1.5f, 1, 6);
	}

	@Override
	public String desc() {
		return "A staff carved from special wood that can be enchanted.";
	}
}
