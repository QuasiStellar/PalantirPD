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
package com.quasistellar.otiluke.items.scrolls;

import com.quasistellar.otiluke.Badges;
import com.quasistellar.otiluke.effects.Identification;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.windows.WndBag;

public class ScrollOfIdentify extends InventoryScroll {

	{
		name = "Scroll of Identify";
		inventoryTitle = "Select an item to identify";
		mode = WndBag.Mode.UNIDENTIFED;
		consumedValue = 10;
		MP_COST = 5;
		bones = true;
	}

	@Override
	protected void onItemSelected(Item item) {

		curUser.sprite.parent.add(new Identification(curUser.sprite.center()
				.offset(0, -16)));

		item.identify();
		GLog.i("It is " + item);

	}

	@Override
	public String desc() {
		return "Permanently reveals all of the secrets of a single item.";
	}

	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}