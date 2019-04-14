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

import java.io.IOException;
import java.util.ArrayList;

import com.quasistellar.palantir.Badges;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.scenes.AmuletScene;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.watabou.noosa.Game;

public class Amulet extends Item {

	private static final String AC_END = "LEAVE PALANTIR";

	{
		name = "Palantir";
		image = ItemSpriteSheet.AMULET;

		unique = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = new ArrayList<>();
		actions.add(AC_END);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_END) {

			showAmuletScene(false);

		} else {

			super.execute(hero, action);

		}
	}

	private void showAmuletScene(boolean showText) {
		try {
			Dungeon.saveAll();
			AmuletScene.noText = showText;
			Game.switchScene(AmuletScene.class);
			Badges.validateSpeedrun();
		} catch (IOException e) {
		}
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public String info() {
		return "The ancient powerful visualistic device. Palantir has an ability to create a small world inside by its owner's will. "+
				"It can be used to store items and even constrain some kinds of magic...";
	}
}
