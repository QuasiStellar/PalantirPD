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
package com.quasistellar.palantir.items.journalpages;

import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.quasistellar.palantir.utils.GLog;

public class DragonCave extends JournalPage {

	private static final String TXT_VALUE = "Dragon Cave";

	{
		name = "journal page dragon cave";
		image = ItemSpriteSheet.JOURNAL_PAGE;
		room=7;

		stackable = false;
	}

	@Override
	public boolean doPickUp(Hero hero) {
         
		GLog.p("You found a page to Otiluke's Journal!", TXT_VALUE);
		return super.doPickUp(hero);
	
	}

	@Override
	public String info() {
		return "A loose journal page";
	}
}
