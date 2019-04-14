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
package com.quasistellar.palantir.items.weapon.ranged;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.Fire;
import com.quasistellar.palantir.actors.blobs.Freezing;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.hero.HeroClass;
import com.quasistellar.palantir.items.Ammo.Ammo;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.bags.Bag;
import com.quasistellar.palantir.items.bags.Quiver;
import com.quasistellar.palantir.items.weapon.Weapon;
import com.quasistellar.palantir.mechanics.Ballistica;
import com.quasistellar.palantir.sprites.MissileSprite;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class RangedWeapon extends Weapon {

	private static final String TXT_MISSILES = "Missile weapon";
	private static final String TXT_YES = "Yes, I know what I'm doing";
	private static final String TXT_NO = "No, I changed my mind";
	private static final String TXT_R_U_SURE = "Do you really want to equip it as a melee weapon?";
	private static final String AC_SHOOT = "Shoot";
	private static final String TXT_NOAMMO = "You need to find some ammo for your ranged weapon!";

	private int tier;
	private int requiredStr;

	public RangedWeapon (int tier, int requiredStr) {
		super();

		this.tier = tier;
		this.requiredStr = requiredStr;
	}

	@Override
	public int damageRoll(Hero owner) {
		Ammo ammo = getAmmo(Dungeon.hero,Dungeon.hero.belongings.backpack.items.toArray(new Item[0]));

		if(!checkQuiver(Dungeon.hero,Dungeon.hero.belongings.backpack.items.toArray(new Item[0]))){
			return 0;
		}

		int damage = Random.Roll(ammo.DIE, ammo.SIDES);

		if (owner.heroClass == HeroClass.ROGUE) {
			int exStr = (TierBonus(level) + 1) * Math.max(Math.round((owner.STR() - STR) / 3), 0);
			if (exStr > 0) {
				damage += Random.IntRange(0, exStr);
			}
		}
		return damage;
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {

		Ammo ammo = getAmmo(Dungeon.hero,Dungeon.hero.belongings.backpack.items.toArray(new Item[0]));

		if (damage>0 && ammo!=null){
			procSpecial(attacker,defender,damage,ammo);
		}
        if(ammo!=null) {
			shoot(attacker, defender.pos, defender.pos, ammo);
		}
		super.proc(attacker, defender, damage);
	}

	public void shoot(Char user, int pos, int dst, Ammo ammo) {

		final int cell = Ballistica.cast(user.pos, dst, false, true);

		((MissileSprite) user.sprite.parent.recycle(MissileSprite.class))
				.reset(user.pos, cell, ammo, new Callback() {
					@Override
					public void call() {
					}
				});
	}

	public void procSpecial(Char attacker, Char defender, int damage, Ammo ammo){
	   	if (ammo.checkimbue() == Ammo.imbueFamily.COLD){
			Fire fire = (Fire) Dungeon.level.blobs.get(Fire.class);
			Freezing.affect(defender.pos, fire);

		}
	}

	@Override
	public boolean isUpgradable() {
		return true;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public String info() {

		StringBuilder info = new StringBuilder(desc());

		info.append("\n\nAverage damage of this weapon equals to "
				+ (MIN + (MAX - MIN) / 2) + " points per hit. ");

		if (Dungeon.hero.belongings.backpack.items.contains(this)) {
			if (STR > Dungeon.hero.STR()) {
				info.append("\n\nBecause of your inadequate strength the accuracy and speed "
						+ "of your attack with this " + name + " is decreased.");
			}
			if (STR < Dungeon.hero.STR()
					&& Dungeon.hero.heroClass == HeroClass.HUNTRESS) {
				info.append("\n\nBecause of your excess strength the damage "
						+ "of your attack with this " + name + " is increased.");
			}
		}

		info.append("\n\nAs this weapon is designed to be used at a distance, it is much less accurate if used at melee range.");

		if (isEquipped(Dungeon.hero)) {
			info.append("\n\nYou hold the " + name + " at the ready.");
		}

		return info.toString();
	}

	public static boolean checkAmmo(Hero hero, Item... items) {

		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (item instanceof Quiver) {
				for (Item bagItem: ((Bag)item).items){
					if (bagItem != null) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static Ammo getAmmo(Hero hero, Item... items) {

		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (item instanceof Quiver) {
				for (Item bagItem: ((Bag)item).items){
					if (bagItem != null && bagItem instanceof Ammo) {
						return ((Ammo)bagItem);
					}
				}
			}
		}
		return null;
	}

	public static Boolean checkQuiver(Hero hero, Item... items) {

		for (int i = 0; i < items.length; i++) {
			Item item = items[i];
			if (item instanceof Quiver) {
				for (Item bagItem: ((Bag)item).items){
					if (bagItem != null && bagItem instanceof Ammo) {
						return true;
					}
				}
			}
		}
		GLog.n(TXT_NOAMMO);
		return false;
	}
}
