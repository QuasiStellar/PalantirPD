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

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.hero.HeroClass;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.sprites.CharSprite;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class PuddingCup extends Item {


	{
		name = "pudding cup";
		image = ItemSpriteSheet.PUDDING_CUP;

		stackable = true;
	}

	@Override
	public boolean doPickUp(Hero hero) {
					
		GLog.p("Oh boy! A pudding cup!");
		
		return super.doPickUp(hero);
	}

	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}
	@Override
	public String info() {
		return "A chocolate pudding cup.";
	}
}
