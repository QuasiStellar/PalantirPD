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
package com.quasistellar.otiluke.actors.buffs;

import java.util.HashSet;

import com.quasistellar.otiluke.actors.blobs.ConfusionGas;
import com.quasistellar.otiluke.actors.blobs.CorruptGas;
import com.quasistellar.otiluke.actors.blobs.ParalyticGas;
import com.quasistellar.otiluke.actors.blobs.StenchGas;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.actors.mobs.BrokenRobot;
import com.quasistellar.otiluke.actors.mobs.Eye;
import com.quasistellar.otiluke.actors.mobs.Warlock;
import com.quasistellar.otiluke.actors.mobs.Yog;
import com.quasistellar.otiluke.levels.traps.LightningTrap;
import com.quasistellar.otiluke.ui.BuffIndicator;

public class MagicImmunity extends FlavourBuff {

	public static final float DURATION = 10f;

	@Override
	public int icon() {
		return BuffIndicator.DISPEL;
	}

	@Override
	public String toString() {
		return "Dispel Magic";
	}

	{
		immunities.add(ParalyticGas.class);
		immunities.add(ToxicGas.class);
		immunities.add(ConfusionGas.class);
		immunities.add(StenchGas.class);
		immunities.add(Burning.class);
		immunities.add(ToxicGas.class);
		immunities.add(Poison.class);
		immunities.add(LightningTrap.Electricity.class);
		immunities.add(Warlock.class);
		immunities.add(Eye.class);
		immunities.add(Yog.BurningFist.class);
		immunities.add(BrokenRobot.class);
		immunities.add(CorruptGas.class);
		
		
	}
}

