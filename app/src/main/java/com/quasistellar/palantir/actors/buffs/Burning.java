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
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.Blob;
import com.quasistellar.palantir.actors.blobs.Fire;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.mobs.Thief;
import com.quasistellar.palantir.effects.particles.ElmoParticle;
import com.quasistellar.palantir.items.Heap;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.food.ChargrilledMeat;
import com.quasistellar.palantir.items.food.MysteryMeat;
import com.quasistellar.palantir.items.rings.RingOfElements.Resistance;
import com.quasistellar.palantir.items.scrolls.Scroll;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.ui.BuffIndicator;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Burning extends Buff implements Hero.Doom {

	private static final String TXT_BURNS_UP = "%s burns up!";
	private static final String TXT_BURNED_TO_DEATH = "You burned to death...";

	private static final float DURATION = 8f;

	private float left;

	private static final String LEFT = "left";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LEFT, left);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		left = bundle.getFloat(LEFT);
	}

	@Override
	public boolean act() {

		if (target.isAlive()) {

			if (target instanceof Hero) {
				Buff.prolong(target, Light.class, TICK * 1.01f);
			}

			target.damage(Random.Int(1, 5), this);

			if (target instanceof Hero) {

				Hero hero = (Hero) target;
				Item item = hero.belongings.randomUnequipped();
				if (item instanceof Scroll) {

					item = item.detach(hero.belongings.backpack);
					GLog.w(TXT_BURNS_UP, item.toString());

					Heap.burnFX(hero.pos);

				} else if (item instanceof MysteryMeat) {

					item = item.detach(hero.belongings.backpack);
					ChargrilledMeat steak = new ChargrilledMeat();
					if (!steak.collect(hero.belongings.backpack)) {
						Dungeon.level.drop(steak, hero.pos).sprite.drop();
					}
					GLog.w(TXT_BURNS_UP, item.toString());

					Heap.burnFX(hero.pos);

				}

			} else if (target instanceof Thief
					&& ((Thief) target).item instanceof Scroll) {

				((Thief) target).item = null;
				target.sprite.emitter().burst(ElmoParticle.FACTORY, 6);
			}

		} else {
			detach();
		}

		if (Level.flamable[target.pos]) {
			GameScene.add(Blob.seed(target.pos, 4, Fire.class));
		}

		spend(TICK);
		left -= TICK;

		if (left <= 0
				|| Random.Float() > (2 + (float) target.HP / target.HT) / 3
				|| (Level.water[target.pos] && !target.flying)) {

			detach();
		}

		return true;
	}

	public void reignite(Char ch) {
		left = duration(ch);
	}

	@Override
	public int icon() {
		return BuffIndicator.FIRE;
	}

	@Override
	public String toString() {
		return "Burning";
	}

	public static float duration(Char ch) {
		Resistance r = ch.buff(Resistance.class);
		return r != null ? r.durationFactor() * DURATION : DURATION;
	}

	@Override
	public void onDeath() {


		Dungeon.fail(ResultDescriptions.BURNING);
		GLog.n(TXT_BURNED_TO_DEATH);
	}
}
