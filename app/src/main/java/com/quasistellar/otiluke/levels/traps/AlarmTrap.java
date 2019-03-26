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
package com.quasistellar.otiluke.levels.traps;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.mobs.Mob;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class AlarmTrap {

	// 0xDD3333

	public static void trigger(int pos, Char ch) {

		for (Mob mob : Dungeon.level.mobs) {
			if (mob != ch) {
				mob.beckon(pos);
			}
		}

		if (Dungeon.visible[pos]) {
			GLog.w("The trap emits a piercing sound that echoes throughout the dungeon!");
			CellEmitter.center(pos).start(Speck.factory(Speck.SCREAM), 0.3f, 3);
		}

		Sample.INSTANCE.play(Assets.SND_ALERT);
	}
}
