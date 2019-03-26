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
package com.quasistellar.otiluke.items.wands;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.buffs.Invisibility;
import com.quasistellar.otiluke.effects.MagicMissile;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.mechanics.Ballistica;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Callback;

public class WandOfBlink extends Wand {

	{
		name = "Wand of Blink";
	}
	private static final String TXT_PREVENTING = "Something scrambles the blink magic! ";
	@Override
	protected void onZap(int cell) {
		
		if (Dungeon.sokobanLevel(Dungeon.depth)){
			GLog.w(TXT_PREVENTING);	
			Invisibility.dispel();
			setKnown();
			return;
		}

		int level = level();

		if (Ballistica.distance > level + 4) {
			cell = Ballistica.trace[level + 3];
		} else if (Actor.findChar(cell) != null && Ballistica.distance > 1) {
			cell = Ballistica.trace[Ballistica.distance - 2];
		}

		curUser.sprite.visible = true;
		appear(Dungeon.hero, cell);
		Dungeon.observe();
	}

	@Override
	protected void fx(int cell, Callback callback) {
		MagicMissile.whiteLight(curUser.sprite.parent, curUser.pos, cell,
				callback);
		Sample.INSTANCE.play(Assets.SND_ZAP);
		if (!Dungeon.sokobanLevel(Dungeon.depth)){
		curUser.sprite.visible = false;
		}
	}

	public static void appear(Char ch, int pos) {

		ch.sprite.interruptMotion();

		ch.move(pos);
		ch.sprite.place(pos);

		if (ch.invisible == 0) {
			ch.sprite.alpha(0);
			ch.sprite.parent.add(new AlphaTweener(ch.sprite, 1, 0.4f));
		}

		ch.sprite.emitter().start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		Sample.INSTANCE.play(Assets.SND_TELEPORT);
	}

	@Override
	public String desc() {
		return "This wand will allow you to teleport in the chosen direction. "
				+ "Creatures and inanimate obstructions will block the teleportation.";
	}
}
