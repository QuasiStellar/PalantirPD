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
package com.quasistellar.palantir.scenes;

import android.content.Intent;
import android.net.Uri;

import com.quasistellar.palantir.Palantir;
import com.quasistellar.palantir.effects.Flare;
import com.quasistellar.palantir.ui.Archs;
import com.quasistellar.palantir.ui.ExitButton;
import com.quasistellar.palantir.ui.Icons;
import com.quasistellar.palantir.ui.Window;
import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.TouchArea;

public class AboutScene extends PixelScene {

	private static final String TTL_SHPX = "Palantir";

	private static final String TXT_SHPX = "Code & Graphics: QuasiStellar\n"
			+ "Music: Dr.Blacker\n\n" + "Modified from Sprouted Pixel Dungeon\n"
			+ "Visit QuasiStellar for more info:";

	private static final String LNK_SHPX = "quasistellar.org";

	private static final String TTL_WATA = "Pixel Dungeon";

	private static final String TXT_WATA = "Code & Graphics: Watabou\n"
			+ "Music: Cube_Code\n\n" + "Visit Watabou for more info:";

	private static final String LNK_WATA = "pixeldungeon.watabou.ru";

	@Override
	public void create() {
		super.create();

		final float colWidth = Camera.main.width
				/ (Palantir.landscape() ? 2 : 1);
		final float colTop = (Camera.main.height / 2)
				- (Palantir.landscape() ? 30 : 90);
		final float wataOffset = Palantir.landscape() ? colWidth
				: 0;

		Image shpx = Icons.ORIGINS.get();
		shpx.x = align((colWidth - shpx.width()) / 2);
		shpx.y = align(colTop);
		add(shpx);

		new Flare(7, 64).color(0x112233, true).show(shpx, 0).angularSpeed = +20;

		BitmapTextMultiline shpxtitle = createMultiline(TTL_SHPX, 8);
		shpxtitle.maxWidth = (int) Math.min(colWidth, 120);
		shpxtitle.measure();
		shpxtitle.hardlight(Window.SHPX_COLOR);
		add(shpxtitle);

		shpxtitle.x = align((colWidth - shpxtitle.width()) / 2);
		shpxtitle.y = align(shpx.y + shpx.height + 5);

		BitmapTextMultiline shpxtext = createMultiline(TXT_SHPX, 8);
		shpxtext.maxWidth = shpxtitle.maxWidth;
		shpxtext.measure();
		add(shpxtext);

		shpxtext.x = align((colWidth - shpxtext.width()) / 2);
		shpxtext.y = align(shpxtitle.y + shpxtitle.height() + 12);

		BitmapTextMultiline shpxlink = createMultiline(LNK_SHPX, 8);
		shpxlink.maxWidth = shpxtitle.maxWidth;
		shpxlink.measure();
		shpxlink.hardlight(Window.SHPX_COLOR);
		add(shpxlink);

		shpxlink.x = shpxtext.x;
		shpxlink.y = shpxtext.y + shpxtext.height();

		TouchArea shpxhotArea = new TouchArea(shpxlink) {
			@Override
			protected void onClick(Touch touch) {
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("https://quasistellar.neocities.org"));
				Game.instance.startActivity(intent);
			}
		};
		add(shpxhotArea);

		Image wata = Icons.WATA.get();
		wata.x = align(wataOffset + (colWidth - wata.width()) / 2);
		wata.y = align(Palantir.landscape() ? colTop : shpxlink.y
				+ wata.height + 20);
		add(wata);

		new Flare(7, 64).color(0x112233, true).show(wata, 0).angularSpeed = +20;

		BitmapTextMultiline wataTitle = createMultiline(TTL_WATA, 8);
		wataTitle.maxWidth = (int) Math.min(colWidth, 120);
		wataTitle.measure();
		wataTitle.hardlight(Window.TITLE_COLOR);
		add(wataTitle);

		wataTitle.x = align(wataOffset + (colWidth - wataTitle.width()) / 2);
		wataTitle.y = align(wata.y + wata.height + 11);

		BitmapTextMultiline wataText = createMultiline(TXT_WATA, 8);
		wataText.maxWidth = wataTitle.maxWidth;
		wataText.measure();
		add(wataText);

		wataText.x = align(wataOffset + (colWidth - wataText.width()) / 2);
		wataText.y = align(wataTitle.y + wataTitle.height() + 12);

		BitmapTextMultiline wataLink = createMultiline(LNK_WATA, 8);
		wataLink.maxWidth = wataTitle.maxWidth;
		wataLink.measure();
		wataLink.hardlight(Window.TITLE_COLOR);
		add(wataLink);

		wataLink.x = wataText.x;
		wataLink.y = wataText.y + wataText.height();

		TouchArea hotArea = new TouchArea(wataLink) {
			@Override
			protected void onClick(Touch touch) {
				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse("http://" + LNK_WATA));
				Game.instance.startActivity(intent);
			}
		};
		add(hotArea);

		Archs archs = new Archs();
		archs.setSize(Camera.main.width, Camera.main.height);
		addToBack(archs);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos(Camera.main.width - btnExit.width(), 0);
		add(btnExit);

		fadeIn();
	}

	@Override
	protected void onBackPressed() {
		Palantir.switchNoFade(TitleScene.class);
	}
}
