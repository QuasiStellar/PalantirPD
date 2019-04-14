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
package com.quasistellar.palantir.actors.mobs;

import java.util.HashSet;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Amok;
import com.quasistellar.palantir.actors.buffs.Terror;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.mobs.npcs.Imp;
import com.quasistellar.palantir.items.KindOfWeapon;
import com.quasistellar.palantir.items.food.Food;
import com.quasistellar.palantir.items.food.Meat;
import com.quasistellar.palantir.items.weapon.melee.Knuckles;
import com.quasistellar.palantir.sprites.MonkSprite;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.utils.Random;

public class Monk extends Mob {

	public static final String TXT_DISARM = "%s has knocked the %s from your hands!";

	{
		name = "dwarf monk";
		spriteClass = MonkSprite.class;

		HP = HT = 100+(adj(0)*Random.NormalIntRange(3, 5));
		defenseSkill = 30+adj(1);

		EXP = 11;
		maxLvl = 21;

		loot = new Food();
		lootChance = 0.1f;
		
		lootOther = new Meat();
		lootChanceOther = 0.1f; // by default, see die()
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(22, 36+adj(0));
	}

	@Override
	public int attackSkill(Char target) {
		return 30+adj(01);
	}

	@Override
	protected float attackDelay() {
		return 0.5f;
	}

	@Override
	public int dr() {
		return 2+adj(0);
	}

	@Override
	public String defenseVerb() {
		return "parried";
	}

	@Override
	public void die(Object cause) {
		Imp.Quest.process(this);

		super.die(cause);
	}

	@Override
	public int attackProc(Char enemy, int damage) {

		if (Random.Int(12) == 0 && enemy == Dungeon.hero) {

			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;

			if (weapon != null && !(weapon instanceof Knuckles)
					&& !weapon.cursed) {
				hero.belongings.weapon = null;
				Dungeon.level.drop(weapon, hero.pos).sprite.drop();
				GLog.w(TXT_DISARM, name, weapon.name());
				weapon.updateQuickslot();
			}
		}

		return damage;
	}

	@Override
	public String description() {
		return "These monks are fanatics, who devoted themselves to protecting their city's secrets from all aliens. "
				+ "They don't use any armor or weapons, relying solely on the art of hand-to-hand combat.";
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Amok.class);
		IMMUNITIES.add(Terror.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
