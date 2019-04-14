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
package com.quasistellar.palantir.items.potions;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.buffs.Bleeding;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Cripple;
import com.quasistellar.palantir.actors.buffs.Poison;
import com.quasistellar.palantir.actors.buffs.Weakness;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.effects.Speck;
import com.quasistellar.palantir.utils.GLog;

public class PotionOfMending extends Potion {

	{
		name = "Potion of Mending";

		bones = true;
	}

	@Override
	public void apply(Hero hero) {
		setKnown();
		heal(Dungeon.hero);
		GLog.p("Your wounds heal some.");
	}

	public static void heal(Hero hero) {

		hero.HP = hero.HP+Math.min((hero.HT/4), hero.HT-hero.HP);
		Buff.detach(hero, Poison.class);
		Buff.detach(hero, Cripple.class);
		Buff.detach(hero, Weakness.class);
		Buff.detach(hero, Bleeding.class);

		hero.sprite.emitter().start(Speck.factory(Speck.HEALING), 0.4f, 4);
	}

	@Override
	public String desc() {
		return "An elixir that will mend some wounds and cure ailments.";
	}

	@Override
	public int price() {
		return isKnown() ? 20 * quantity : super.price();
	}
}
