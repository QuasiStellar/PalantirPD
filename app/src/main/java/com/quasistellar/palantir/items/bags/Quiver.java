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
package com.quasistellar.palantir.items.bags;

import com.quasistellar.palantir.items.Ammo.Arrow;
import com.quasistellar.palantir.items.Ammo.SilverArrow;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;

public class Quiver extends Bag {

	{
		name = "quiver";
		image = ItemSpriteSheet.BATTLE_AXE;

		size = 10;
	}

	@Override
	public boolean grab(Item item) {
		return item instanceof Arrow || item instanceof SilverArrow;
	}

	@Override
	public int price() {
		return 50;
	}

	@Override
	public String info() {
		return "A sturdy open-ended pack that holds many arrows at the ready.";
	}
}
