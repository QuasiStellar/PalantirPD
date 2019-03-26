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
package com.quasistellar.otiluke.scenes;

import com.quasistellar.otiluke.windows.WndStory;
import com.watabou.noosa.Game;

public class IntroScene extends PixelScene {

	private static final String TEXT = "Ancient secrets hidden in Palantir have remained a mystery for several centuries. "+
			"Many a wizard has attempted to uncover the mysteries of the magical stone, eventually walking away with nothing. "+
			"Apparently, the stone itself didn't let them use its magic. "+
			"Only Otiluke, a powerful but young mage-visualist, succeeded in bending its power to his will. "+
			"Using its power, Otiluke devised a method for creating extra-planar spaces out of nothing. "+
			"Maybe the safest interdimensional storage imaginable, but potentially the most dangerous. ";

	@Override
	public void create() {
		super.create();

		add(new WndStory(TEXT) {
			@Override
			public void hide() {
				super.hide();
				Game.switchScene(InterlevelScene.class);
			}
		});

		fadeIn();
	}
}
