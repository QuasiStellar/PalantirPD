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
package com.quasistellar.otiluke.items.scrolls;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Invisibility;
import com.quasistellar.otiluke.actors.buffs.Weakness;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.effects.Flare;
import com.quasistellar.otiluke.effects.particles.ShadowParticle;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.bags.Bag;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.noosa.audio.Sample;

public class ScrollOfRemoveCurse extends Scroll {

	private static final String TXT_PROCCED = "Your pack glows with a cleansing light, and a malevolent energy disperses.";
	private static final String TXT_NOT_PROCCED = "Your pack glows with a cleansing light, but nothing happens.";

	{
		name = "Scroll of Remove Curse";
		consumedValue = 15;
		MP_COST = 5;
	}

	@Override
	protected void doRead() {

		new Flare(6, 32).show(curUser.sprite, 2f);
		Sample.INSTANCE.play(Assets.SND_READ);
		Invisibility.dispel();

		boolean procced = uncurse(curUser,
				curUser.belongings.backpack.items.toArray(new Item[0]));
		procced = uncurse(curUser, curUser.belongings.weapon,
				curUser.belongings.armor, curUser.belongings.misc1,
				curUser.belongings.misc2)
				|| procced;

		Buff.detach(curUser, Weakness.class);

		if (procced) {
			GLog.p(TXT_PROCCED);
		} else {
			GLog.i(TXT_NOT_PROCCED);
		}

		setKnown();

		curUser.spendAndNext(TIME_TO_READ);
	}

	@Override
	public String desc() {
		return "The incantation on this scroll will instantly strip from "
				+ "the reader's weapon, armor, rings and carried items any evil "
				+ "enchantments that might prevent the wearer from removing them."
				+ "The powerful magic in this scroll also upgrades cursed items.";
	}

	public static boolean uncurse(Hero hero, Item... items) {

		boolean procced = false;
		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (item != null && item.cursed) {
				item.uncurse();
				if(item.level<0){item.upgrade((-item.level)*2);} //upgrade to reverse of negatives
         	   if (item.cursed==false) {procced = true;}
			}
			if (item instanceof Bag) {
				for (Item bagItem: ((Bag)item).items){
                   if (bagItem != null && bagItem.cursed) {
                	   bagItem.uncurse();
                	   if(bagItem.level<0){bagItem.upgrade((-bagItem.level)*2);}
                	   if (bagItem.cursed==false) {procced = true;}
                     }
				}
			}
		}

		if (procced) {
			hero.sprite.emitter().start(ShadowParticle.UP, 0.05f, 10);
		}

		return procced;
	}

	@Override
	public int price() {
		return isKnown() ? 30 * quantity : super.price();
	}
}
