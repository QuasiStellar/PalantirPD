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
package com.quasistellar.otiluke.items.misc;

import java.util.ArrayList;

import com.quasistellar.otiluke.Badges;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.hero.HeroClass;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.ItemStatusHandler;
import com.quasistellar.otiluke.items.KindofMisc;
import com.quasistellar.otiluke.items.rings.Ring.RingBuff;
import com.quasistellar.otiluke.items.rings.RingOfAccuracy.Accuracy;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class AutoPotion extends MiscEquippable {

	{
		name = "auto potion";
		image = ItemSpriteSheet.ARTIFACT;

		unique = true;
	}
	
	@Override
	protected MiscBuff buff() {
		return new AutoHealPotion();
	}

	public class AutoHealPotion extends MiscBuff {
	}
	
    @Override
	public String cursedDesc(){
		return "your " + this  + " is cursed";		
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public String desc() {
		return "Wearing this charm will automatically use a potion when your life gets low.";
	}


}
