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

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Invisibility;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.quasistellar.palantir.ui.BuffIndicator;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class LokisFlail extends RelicMeleeWeapon {

	public LokisFlail() {
		super(6, 1f, 1f);
		// TODO Auto-generated constructor stub
	}

	
	{
		name = "Loki's Flail";
		image = ItemSpriteSheet.LOKISFLAIL;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = 0;
		chargeCap = 1000;

		cooldown = 0;
		bones = false;
		
  }
	
	private static final String TXT_PREVENTING = "Enemies on this level are all blind. No point using invisibility.";
		
	public static final String AC_STEALTH = "STEALTH";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= chargeCap)
			actions.add(AC_STEALTH);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_STEALTH)) {
			
			  if (Dungeon.depth==29) {
					GLog.w(TXT_PREVENTING);
					return;
			
			   } 
			GLog.w("Your sword fills you with restoring energy!");
			charge = 0;
			Buff.affect(hero, Invisibility.class, Invisibility.DURATION);
			GLog.i("You see your hands turn invisible!");
			Sample.INSTANCE.play(Assets.SND_MELD);

		} else
			super.execute(hero, action);
	}

	
	public class StealthCounter extends WeaponBuff {

		@Override
		public boolean act() {
			if (charge < chargeCap) {
				charge+=level;
				if (charge >= chargeCap) {
					GLog.w("Your flail is fully charged.");					
				}
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}
		
		@Override
		public String toString() {
			return "StealthCounter";
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
		return new StealthCounter();
	}
	
}


