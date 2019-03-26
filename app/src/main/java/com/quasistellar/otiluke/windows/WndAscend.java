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
import com.quasistellar.otiluke.items.DewVial;
import com.quasistellar.otiluke.scenes.PixelScene;
import com.quasistellar.otiluke.sprites.ItemSprite;
import com.quasistellar.otiluke.ui.RedButton;
import com.quasistellar.otiluke.ui.Window;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.BitmapTextMultiline;

public class WndAscend extends Window {

	private static final String TXT_MESSAGE = "Are you sure you want to ascend? "
                                             +"Ascending here will end your game. ";
	private static final String TXT_REWARD = "Ascend";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;

	public WndAscend() {

		super();
		
		DewVial dewvial = new DewVial();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(new ItemSprite(dewvial.image(), null));
		titlebar.label(Utils.capitalize(dewvial.name()));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		BitmapTextMultiline message = PixelScene
				.createMultiline(TXT_MESSAGE, 6);
		message.maxWidth = WIDTH;
		message.measure();
		message.y = titlebar.bottom() + GAP;
		add(message);

		RedButton btnReward = new RedButton(TXT_REWARD) {
			@Override
			protected void onClick() {
				Dungeon.level.forcedone=true;
				hide();
			}
		};
		btnReward.setRect(0, message.y + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnReward);

		resize(WIDTH, (int) btnReward.bottom());
	}

	
}
