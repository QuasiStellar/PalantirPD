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
package com.quasistellar.palantir.items.wands;

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.ResultDescriptions;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Paralysis;
import com.quasistellar.palantir.actors.buffs.Strength;
import com.quasistellar.palantir.effects.CellEmitter;
import com.quasistellar.palantir.effects.MagicMissile;
import com.quasistellar.palantir.effects.Speck;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.mechanics.Ballistica;
import com.quasistellar.palantir.utils.BArray;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class WandOfAvalanche extends Wand {

	{
		name = "Wand of Avalanche";
		hitChars = false;
	}

	@Override
	protected void onZap(int cell) {

		Sample.INSTANCE.play(Assets.SND_ROCKS);

		int level = level();

		Ballistica.distance = Math.min(Ballistica.distance, 8 + level);

		int size = 1 + level / 3;
		PathFinder.buildDistanceMap(cell, BArray.not(Level.solid, null), size);

		for (int i = 0; i < Level.getLength(); i++) {

			int d = PathFinder.distance[i];

			if (d < Integer.MAX_VALUE) {

				Char ch = Actor.findChar(i);
				if (ch != null) {

					ch.sprite.flash();
					
					 int damage= Random.Int(2, 6 + (size - d) * 2);
			         if (Dungeon.hero.buff(Strength.class) != null){ damage *= (int) 4f; Buff.detach(Dungeon.hero, Strength.class);}
					 ch.damage(damage, this);
	

					if (ch.isAlive() && Random.Int(2 + d) == 0) {
						Buff.prolong(ch, Paralysis.class, Random.IntRange(2, 6));
					}
				}

				CellEmitter.get(i).start(Speck.factory(Speck.ROCK), 0.07f,
						3 + (size - d));
				Camera.main.shake(3, 0.07f * (3 + (size - d)));
			}
		}

		if (!curUser.isAlive()) {
			Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
			GLog.n("You killed yourself with your own Wand of Avalanche...");
		}
	}

	@Override
	protected void fx(int cell, Callback callback) {
		MagicMissile.earth(curUser.sprite.parent, curUser.pos, cell, callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
	}

	@Override
	public String desc() {
		return "When a discharge of this wand hits a wall (or any other solid obstacle) it causes "
				+ "an avalanche of stones, damaging and stunning all creatures in the affected area.";
	}
}
