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
package com.quasistellar.palantir.items.weapon.melee.relic;

import java.util.ArrayList;

import com.quasistellar.palantir.actors.buffs.BerryRegeneration;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.quasistellar.palantir.ui.BuffIndicator;
import com.quasistellar.palantir.utils.GLog;

public class AresSword extends RelicMeleeWeapon {

	public AresSword() {
		super(6, 1f, 1f);
		// TODO Auto-generated constructor stub
	}

	
	{
		name = "Ares Sword";
		image = ItemSpriteSheet.ARESSWORD;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = 0;
		chargeCap = 1000;

		cooldown = 0;
		bones = false;
		
  }
		
	public static final String AC_REGEN = "REGEN";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= chargeCap)
			actions.add(AC_REGEN);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_REGEN)) {
			GLog.w("Your sword fills you with restoring energy!");
			charge = 0;
			Buff.affect(hero, BerryRegeneration.class).level(level*2);
		} else
			super.execute(hero, action);
	}

	
	public class RegenCounter extends WeaponBuff {

		@Override
		public boolean act() {
			if (charge < chargeCap) {
				charge+=level;
				if (charge >= chargeCap) {
					GLog.w("Your sword glows with life-giving power.");					
				}
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}
		
		@Override
		public String toString() {
			return "Regen";
		}

		@Override
		public int icon() {
			if (cooldown == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.NONE;
		}

		@Override
		public void detach() {
			cooldown = 0;
			charge = 0;
			super.detach();
		}

	}
	
	
	
	
	@Override
	protected WeaponBuff passiveBuff() {
		return new RegenCounter();
	}
	
}


