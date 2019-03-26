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
import com.quasistellar.otiluke.Statistics;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Dewcharge;
import com.quasistellar.otiluke.actors.mobs.npcs.Tinkerer1;
import com.quasistellar.otiluke.actors.mobs.pets.PET;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.Mushroom;
import com.quasistellar.otiluke.items.rings.Ring;
import com.quasistellar.otiluke.items.rings.RingOfHaste;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.scenes.PixelScene;
import com.quasistellar.otiluke.sprites.ItemSprite;
import com.quasistellar.otiluke.ui.RedButton;
import com.quasistellar.otiluke.ui.Window;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.BitmapTextMultiline;

public class WndPetHaste extends Window {
	
	//if people don't get it after this, I quit. I just quit.

	private static final String TXT_MESSAGE = "You can apply a portion of your haste buff to your pet. " +
			                                  "This will limit your haste level to 10 but your pet will be able to keep up with you while you move. "
			                                  +"You can unhaste your pet by removing your ring of haste. "; 
	
	private static final String TXT_YES = "Haste my Pet";
	private static final String TXT_NO = "No Thanks";


	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndPetHaste(final PET pet, final RingOfHaste ring) {

		super();

		IconTitle titlebar = new IconTitle();
		titlebar.icon(pet.sprite());
		titlebar.label(Utils.capitalize(pet.name));
		titlebar.setRect(0, 0, WIDTH, 0);
		add(titlebar);

		BitmapTextMultiline message = PixelScene
				.createMultiline(TXT_MESSAGE, 6);
		message.maxWidth = WIDTH;
		message.measure();
		message.y = titlebar.bottom() + GAP;
		add(message);

		RedButton btnBattle = new RedButton(TXT_YES) {
			@Override
			protected void onClick() {
				Dungeon.petHasteLevel=ring.level;
				GLog.p("Your "+pet.name+" is moving faster!");
				hide();
			}
		};
		btnBattle.setRect(0, message.y + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnBattle);

		RedButton btnNonBattle = new RedButton(TXT_NO+" "+ring.level+" "+pet.speed()) {
			@Override
			protected void onClick() {
				hide();
			}
		};
		
		btnNonBattle.setRect(0, btnBattle.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnNonBattle);
		
		
		resize(WIDTH, (int) btnNonBattle.bottom());
	}

	
	
	
}
