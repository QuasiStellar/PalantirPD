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
package com.quasistellar.otiluke.windows;

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.mobs.npcs.Tinkerer3;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.Mushroom;
import com.quasistellar.otiluke.scenes.PixelScene;
import com.quasistellar.otiluke.sprites.ItemSprite;
import com.quasistellar.otiluke.ui.RedButton;
import com.quasistellar.otiluke.ui.Window;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.BitmapTextMultiline;

public class WndTinkerer3 extends Window {

	private static final String TXT_MESSAGE = "Thanks for the Toadstool Mushroom! "
			                                  +"I can upgrade your dew vial for you. "
			                                  +"I can make it hold more and give you wings when you splash. ";
	private static final String TXT_UPGRADE = "Upgrade my Vial!";
	
	private static final String TXT_FARAWELL = "Good luck in your quest, %s!";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndTinkerer3(final Tinkerer3 tinkerer, final Item item) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(item.image(), null));
		titlebar.label(Utils.capitalize(item.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		BitmapTextMultiline message = PixelScene
				.createMultiline(TXT_MESSAGE, 6);
		message.maxWidth = WIDTH;
		message.measure();
		message.y = titlebar.bottom() + GAP;
		add(message);

		RedButton btnUpgrade = new RedButton(TXT_UPGRADE) {
			@Override
			protected void onClick() {
				selectUpgrade(tinkerer);
			}
		};
		btnUpgrade.setRect(0, message.y + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnUpgrade);

		
		resize(WIDTH, (int) btnUpgrade.bottom());
	}

	private void selectUpgrade(Tinkerer3 tinkerer) {
		hide();
		
		Mushroom mushroom = Dungeon.hero.belongings.getItem(Mushroom.class);
		mushroom.detach(Dungeon.hero.belongings.backpack);
		
			Dungeon.dewWater=true;				
			//Dungeon.dewDraw=true;
			Dungeon.wings=true;
	
		
		tinkerer.yell(Utils.format(TXT_FARAWELL, Dungeon.hero.givenName()));
		tinkerer.destroy();

		tinkerer.sprite.die();

		//Wandmaker.Quest.complete();
	}
}
