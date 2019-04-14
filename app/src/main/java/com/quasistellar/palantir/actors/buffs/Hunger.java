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
package com.quasistellar.palantir.actors.buffs;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.ResultDescriptions;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.hero.HeroClass;
import com.quasistellar.palantir.items.artifacts.Artifact;
import com.quasistellar.palantir.items.artifacts.HornOfPlenty;
import com.quasistellar.palantir.ui.BuffIndicator;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Hunger extends Buff implements Hero.Doom {

	private static final float STEP = 10f;

	public static final float HUNGRY = 600f; //260
	public static final float STARVING = 700f; //360

	private static final String TXT_HUNGRY = "You are hungry.";
	private static final String TXT_STARVING = "You are starving!";
	private static final String TXT_DEATH = "You starved to death...";

	private float level;

	private static final String LEVEL = "level";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEVEL, level);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		level = bundle.getFloat(LEVEL);
	}

	@Override
	public boolean act() {
		if (target.isAlive()) {

			Hero hero = (Hero) target;

			if (isStarving()) {

				if (Random.Float() < 0.3f
						&& (target.HP > 1 || !target.paralysed)) {

					hero.damage(1, this);

				}
			} else {

				float newLevel = level + STEP;
				boolean statusUpdated = false;
				if (newLevel >= STARVING) {

					GLog.n(TXT_STARVING);
					hero.damage(1, this);
					statusUpdated = true;

					hero.interrupt();

				} else if (newLevel >= HUNGRY && level < HUNGRY) {

					GLog.w(TXT_HUNGRY);
					statusUpdated = true;

				}
				level = newLevel;

				if (statusUpdated) {
					BuffIndicator.refreshHero();
				}

			}

			float step = ((Hero) target).heroClass == HeroClass.ROGUE ? STEP * 1.2f
					: STEP;
			spend(target.buff(Shadows.class) == null ? step : step * 1.5f);

		} else {

			diactivate();

		}

		return true;
	}

	public void satisfy(float energy) {
		Artifact.ArtifactBuff buff = target
				.buff(HornOfPlenty.hornRecharge.class);
		if (buff != null && buff.isCursed()) {
			energy = Math.round(energy * 0.67f);
			GLog.n("The cursed horn steals some of the food energy as you eat.");
		}
		level -= energy;
		if (level < 0) {
			level = 0;
		} else if (level > STARVING) {
			level = STARVING;
		}

		BuffIndicator.refreshHero();
	}

	public boolean isStarving() {
		return level >= STARVING;
	}
	
	public int hungerLevel() {
		return (int) level;
	}

	@Override
	public int icon() {
		if (level < HUNGRY) {
			return BuffIndicator.NONE;
		} else if (level < STARVING) {
			return BuffIndicator.HUNGER;
		} else {
			return BuffIndicator.STARVATION;
		}
	}

	@Override
	public String toString() {
		if (level < STARVING) {
			return "Hungry";
		} else {
			return "Starving";
		}
	}

	@Override
	public void onDeath() {


		Dungeon.fail(ResultDescriptions.HUNGER);
		GLog.n(TXT_DEATH);
	}
}
