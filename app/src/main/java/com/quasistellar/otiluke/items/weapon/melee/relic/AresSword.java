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
package com.quasistellar.otiluke.items.weapon.melee.relic;

import java.util.ArrayList;
import java.util.HashSet;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.actors.buffs.BerryRegeneration;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Burning;
import com.quasistellar.otiluke.actors.buffs.GasesImmunity;
import com.quasistellar.otiluke.actors.buffs.MagicImmunity;
import com.quasistellar.otiluke.actors.buffs.Poison;
import com.quasistellar.otiluke.actors.buffs.Slow;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.mobs.Eye;
import com.quasistellar.otiluke.actors.mobs.Warlock;
import com.quasistellar.otiluke.actors.mobs.Yog;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.artifacts.Artifact.ArtifactBuff;
import com.quasistellar.otiluke.items.artifacts.CloakOfShadows.cloakStealth;
import com.quasistellar.otiluke.items.rings.Ring.RingBuff;
import com.quasistellar.otiluke.items.weapon.Weapon;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.levels.traps.LightningTrap;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.ui.BuffIndicator;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

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


