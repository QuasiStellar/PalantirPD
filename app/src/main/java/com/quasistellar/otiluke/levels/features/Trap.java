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
package com.quasistellar.otiluke.levels.features;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Badges;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Cripple;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.mobs.Mob;
import com.quasistellar.otiluke.actors.mobs.pets.PET;
import com.quasistellar.otiluke.items.SanChikarahTranscend;
import com.quasistellar.otiluke.items.artifacts.DriedRose;
import com.quasistellar.otiluke.items.artifacts.TimekeepersHourglass;
import com.quasistellar.otiluke.levels.RegularLevel;
import com.quasistellar.otiluke.levels.Room;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.scenes.InterlevelScene;
import com.quasistellar.otiluke.sprites.MobSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.windows.WndOptions;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Trap {

	private static final String TXT_TRAP = "Trap";
	private static final String TXT_YES = "Yes, I want to die";
	private static final String TXT_NO = "No, I changed my mind";
	private static final String TXT_STEP = "Do you really want to take step on this trap? You will die.";

	public static boolean stepConfirmed = false;

	public static void heroStep(final Hero hero) {
		GameScene.show(new WndOptions(TXT_TRAP, TXT_STEP, TXT_YES, TXT_NO) {
			@Override
			protected void onSelect(int index) {
				if (index == 0) {
					stepConfirmed = true;
					hero.resume();
					stepConfirmed = false;
				}
			};
		});
	}
}
