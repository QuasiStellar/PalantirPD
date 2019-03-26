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
package com.quasistellar.otiluke.actors.mobs.npcs;

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.Statistics;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.effects.particles.ElmoParticle;
import com.quasistellar.otiluke.items.Heap;
import com.quasistellar.otiluke.sprites.ImpSprite;
import com.quasistellar.otiluke.utils.Utils;

public class ImpShopkeeper extends Shopkeeper {

	private static final String TXT_GREETINGS = "Hello, %s! I'll give you a deal on the books once you clear the dungeon.";
	private static final String TXT_GREETINGS2 = "You did it.... Now you will need these books to chase Yog across the hidden places of the dungeon.";
	public static final String TXT_THIEF = "I thought I could trust you!";

	{
		name = "ambitious imp";
		spriteClass = ImpSprite.class;
	}

	private boolean seenBefore = false;
	private boolean killedYog = false;

	@Override
	protected boolean act() {

		if (!seenBefore && Dungeon.visible[pos]) {
			yell(Utils.format(TXT_GREETINGS, Dungeon.hero.givenName()));
			seenBefore = true;
		}
		
		if (Statistics.amuletObtained && !killedYog && Dungeon.visible[pos]) {
			yell(Utils.format(TXT_GREETINGS2, Dungeon.hero.givenName()));
			killedYog = true;
		}

		return super.act();
	}

	@Override
	public void flee() {
		for (Heap heap : Dungeon.level.heaps.values()) {
			if (heap.type == Heap.Type.FOR_SALE) {
				CellEmitter.get(heap.pos).burst(ElmoParticle.FACTORY, 4);
				heap.destroy();
			}
		}

		destroy();

		sprite.emitter().burst(Speck.factory(Speck.WOOL), 15);
		sprite.killAndErase();
	}

	@Override
	public String description() {
		return "Imps are lesser demons. They are notable for neither their strength nor their magic talent. "
				+ "But they are quite smart and sociable, and many of imps prefer to live and do business among non-demons.";
	}
}
