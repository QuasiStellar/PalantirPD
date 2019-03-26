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
package com.quasistellar.otiluke.items;

import java.util.ArrayList;

import com.quasistellar.otiluke.Badges;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.windows.WndBag;

public class UpgradeBlobYellow extends Item {

	private static final String TXT_SELECT = "Select an item to upgrade";
	private static final String TXT_UPGRADED = "your %s certainly looks better";

	private static final float TIME_TO_INSCRIBE = 2;

	private static final String AC_INSCRIBE = "UPGRADE";
	
	private static final int upgrades = 1;

	{
		name = "yellow upgrade goo";
		image = ItemSpriteSheet.UPGRADEGOO_YELLOW;

		stackable = true;

		bones = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_INSCRIBE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action == AC_INSCRIBE) {

			curUser = hero;
			GameScene.selectItem(itemSelector, WndBag.Mode.UPGRADEABLE,
					TXT_SELECT);

		} else {

			super.execute(hero, action);

		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	private void upgrade(Item item) {

		detach(curUser.belongings.backpack);

		GLog.w(TXT_UPGRADED, item.name());

		if (item.reinforced){		
			item.upgrade(upgrades);
			} else {
			item.upgrade(Math.min(upgrades, 15-item.level));
			}

		curUser.sprite.operate(curUser.pos);
		curUser.sprite.emitter().start(Speck.factory(Speck.UP), 0.2f, 3);

		curUser.spend(TIME_TO_INSCRIBE);
		curUser.busy();
	}

	@Override
	public int price() {
		return 30 * quantity;
	}

	@Override
	public String info() {
		return "This blob of yellow goo holds a powerful magic. It can upgrade your gear when applied. ";
	}

	private final WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect(Item item) {
			if (item != null) {
				UpgradeBlobYellow.this.upgrade(item);
			}
		}
	};
}
