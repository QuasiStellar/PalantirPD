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

import com.quasistellar.otiluke.actors.blobs.ConfusionGas;
import com.quasistellar.otiluke.actors.blobs.ParalyticGas;
import com.quasistellar.otiluke.actors.blobs.StenchGas;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.ui.BuffIndicator;

public class GasesImmunity extends FlavourBuff {

	public static final float DURATION = 10f;

	@Override
	public int icon() {
		return BuffIndicator.IMMUNITY;
	}

	@Override
	public String toString() {
		return "Immune to gases";
	}

	{
		immunities.add(ParalyticGas.class);
		immunities.add(ToxicGas.class);
		immunities.add(ConfusionGas.class);
		immunities.add(StenchGas.class);
	}
}