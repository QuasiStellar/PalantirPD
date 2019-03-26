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
package com.quasistellar.otiluke.items.journalpages;

import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;

public class JournalPage extends Item {

	private static final String TXT_VALUE = "Journal Page";
	public int room;

	{
		name = "journal page";
		image = ItemSpriteSheet.JOURNAL_PAGE;

		stackable = false;
		unique = true;
	}
		
	@Override
	public boolean doPickUp(Hero hero) {
         
		GLog.p("You found a page to Otiluke's Journal! It reads, %s.", TXT_VALUE);
		return super.doPickUp(hero);
	
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
	public int price() {
		return 10 * quantity;
	}
	
	@Override
	public String info() {
		return "A loose journal page labled Journal Page.";
	}
}
