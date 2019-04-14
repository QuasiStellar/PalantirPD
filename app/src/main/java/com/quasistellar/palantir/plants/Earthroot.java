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
package com.quasistellar.palantir.plants;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.effects.CellEmitter;
import com.quasistellar.palantir.effects.particles.EarthParticle;
import com.quasistellar.palantir.items.potions.PotionOfParalyticGas;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.quasistellar.palantir.ui.BuffIndicator;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.utils.Bundle;

public class Earthroot extends Plant {

	private static final String TXT_DESC = "When a creature touches an Earthroot, its roots "
			+ "create a kind of immobile natural armor around it.";

	{
		image = 5;
		plantName = "Earthroot";
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		if (ch != null) {
			Buff.affect(ch, Armor.class).level = ch.HT;
		}

		if (Dungeon.visible[pos]) {
			CellEmitter.bottom(pos).start(EarthParticle.FACTORY, 0.05f, 8);
			Camera.main.shake(1, 0.4f);
		}
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	public static class Seed extends Plant.Seed {
		{
			plantName = "Earthroot";

			name = "seed of " + plantName;
			image = ItemSpriteSheet.SEED_EARTHROOT;

			plantClass = Earthroot.class;
			alchemyClass = PotionOfParalyticGas.class;

			bones = true;
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}
	}

	public static class Armor extends Buff {

		private static final float STEP = 1f;

		private int pos;
		private int level;
		
		@Override
		public boolean attachTo(Char target) {
			pos = target.pos;
			return super.attachTo(target);
		}

		@Override
		public boolean act() {
			if (target.pos != pos) {
				detach();
			}
			spend(STEP);
			return true;
		}

		public int absorb(int damage) {
			if (level <= damage - damage / 2) {
				detach();
				return damage - level;
			} else {
				level -= damage - damage / 2;
				return damage / 2;
			}
		}

		public void level(int value) {
			if (level < value) {
				level = value;
			}
		}

		@Override
		public int icon() {
			return BuffIndicator.ARMOR;
		}

		@Override
		public String toString() {
			return Utils.format("Herbal armor (%d)", level);
		}

		private static final String POS = "pos";
		private static final String LEVEL = "level";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(POS, pos);
			bundle.put(LEVEL, level);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			pos = bundle.getInt(POS);
			level = bundle.getInt(LEVEL);
		}
	}
	
	public static class NutArmor extends Buff {

		private int pos;
		private int level;
		public static final float DURATION = 10f;
		
		@Override
		public boolean attachTo(Char target) {
			pos = target.pos;
			return super.attachTo(target);
		}

		@Override
		public boolean act() {
			detach();
			return true;
		}

		public int absorb(int damage) {
			if (level <= damage - damage / 2) {
				detach();
				return damage - level;
			} else {
				level -= damage - damage / 2;
				return damage / 2;
			}
		}

		public void level(int value) {
			if (level < value) {
				level = value;
			}
		}

		@Override
		public int icon() {
			return BuffIndicator.ARMOR;
		}

		@Override
		public String toString() {
			return Utils.format("Herbal armor (%d)", level);
		}

		private static final String POS = "pos";
		private static final String LEVEL = "level";

		@Override
		public void storeInBundle(Bundle bundle) {
			super.storeInBundle(bundle);
			bundle.put(POS, pos);
			bundle.put(LEVEL, level);
		}

		@Override
		public void restoreFromBundle(Bundle bundle) {
			super.restoreFromBundle(bundle);
			pos = bundle.getInt(POS);
			level = bundle.getInt(LEVEL);
		}
	}
}
