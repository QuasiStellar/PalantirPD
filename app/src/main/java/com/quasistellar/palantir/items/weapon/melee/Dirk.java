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

public class Dirk extends MeleeWeapon {

	{
		name = "dirk";
		image = ItemSpriteSheet.DIRK;
	}

	public Dirk() {
		super(2, 11, 1f, 1.5f, 2, 6);
	}

	@Override
	public String desc() {
		return "When a little bit more blade than a dagger is just right.";
	}
}
