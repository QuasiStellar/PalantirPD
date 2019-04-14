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
package com.quasistellar.palantir.items;

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.effects.particles.ShadowParticle;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.noosa.audio.Sample;

public abstract class EquipableItem extends Item {

	private static final String TXT_UNEQUIP_CURSED = "You can't remove cursed %s!";

	public static final String AC_EQUIP = "EQUIP";
	public static final String AC_UNEQUIP = "UNEQUIP";

	{
		bones = true;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_EQUIP)) {
			// In addition to equipping itself, item reassigns itself to the
			// quickslot
			// This is a special case as the item is being removed from
			// inventory, but is staying with the hero.
			int slot = Dungeon.quickslot.getSlot(this);
			doEquip(hero);
			if (slot != -1) {
				Dungeon.quickslot.setSlot(slot, this);
				updateQuickslot();
			}
		} else if (action.equals(AC_UNEQUIP)) {
			doUnequip(hero, true);
		} else {
			super.execute(hero, action);
		}
	}

	@Override
	public void doDrop(Hero hero) {
		if (!isEquipped(hero) || doUnequip(hero, false, false)) {
			super.doDrop(hero);
		}
	}

	@Override
	public void cast(final Hero user, int dst) {

		if (isEquipped(user)) {
			if (quantity == 1 && !this.doUnequip(user, false, false)) {
				return;
			}
		}

		super.cast(user, dst);
	}

	protected static void equipCursed(Hero hero) {
		hero.sprite.emitter().burst(ShadowParticle.CURSE, 6);
		Sample.INSTANCE.play(Assets.SND_CURSED);
	}

	protected float time2equip(Hero hero) {
		return 1;
	}

	public abstract boolean doEquip(Hero hero);

	public boolean doUnequip(Hero hero, boolean collect, boolean single) {

		if (cursed) {
			GLog.w(TXT_UNEQUIP_CURSED, name());
			return false;
		}

		if (single) {
			hero.spendAndNext(time2equip(hero));
		} else {
			hero.spend(time2equip(hero));
		}

		if (collect && !collect(hero.belongings.backpack)) {
			Dungeon.level.drop(this, hero.pos);
		}

		return true;
	}


	public String TierCheck(int level){
		String TIER="Normal";

		if (level>0) {
			TIER = "Enforced";
		}
		if (level>4) {
			TIER = "Strong";
		}
		if (level>9) {
			TIER = "Powerful";
		}
		if (level>15) {
			TIER = "Heroic";
		}
		if (level>30) {
			TIER = "Legendary";
		}
		if (level>50) {
			TIER = "Godly";
		}
		if (level>100) {
			TIER = "Celestial";
		}

		return TIER;
	}
	public int TierBonus(int level){
		int bonus=0;

		if (level>0) {
			bonus = 1;
		}
		if (level>4) {
			bonus = 2;
		}
		if (level>9) {
			bonus = 3;
		}
		if (level>15) {
			bonus = 4;
		}
		if (level>30) {
			bonus = 5;
		}
		if (level>50) {
			bonus = 6;
		}
		if (level>100) {
			bonus = 7;
		}

		return bonus;
	}
	final public boolean doUnequip(Hero hero, boolean collect) {
		return doUnequip(hero, collect, true);
	}
}
