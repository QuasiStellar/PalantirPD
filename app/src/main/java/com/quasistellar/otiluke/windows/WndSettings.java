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

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Palantir;
import com.quasistellar.otiluke.scenes.PixelScene;
import com.quasistellar.otiluke.ui.CheckBox;
import com.quasistellar.otiluke.ui.RedButton;
import com.quasistellar.otiluke.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;

public class WndSettings extends Window {

	private static final String TXT_ZOOM_IN = "+";
	private static final String TXT_ZOOM_OUT = "-";
	private static final String TXT_ZOOM_DEFAULT = "Default Zoom";

	private static final String TXT_SCALE_UP = "Scale up UI";
	private static final String TXT_IMMERSIVE = "Immersive mode";

	private static final String TXT_MUSIC = "Music";

	private static final String TXT_SOUND = "Sound FX";

	private static final String TXT_BRIGHTNESS = "Brightness";

	private static final String TXT_QUICKSLOT = "Second QuickSlot";

	private static final String TXT_SWITCH_PORT = "Switch to portrait";
	private static final String TXT_SWITCH_LAND = "Switch to landscape";

	private static final int WIDTH = 112;
	private static final int BTN_HEIGHT = 20;
	private static final int GAP = 2;

	private RedButton btnZoomOut;
	private RedButton btnZoomIn;

	public WndSettings(boolean inGame) {
		super();

		CheckBox btnImmersive = null;

		if (inGame) {
			int w = BTN_HEIGHT;

			// Zoom out
			btnZoomOut = new RedButton(TXT_ZOOM_OUT) {
				@Override
				protected void onClick() {
					zoom(Camera.main.zoom - 1);
				}
			};
			add(btnZoomOut.setRect(0, 0, w, BTN_HEIGHT));

			// Zoom in
			btnZoomIn = new RedButton(TXT_ZOOM_IN) {
				@Override
				protected void onClick() {
					zoom(Camera.main.zoom + 1);
				}
			};
			add(btnZoomIn.setRect(WIDTH - w, 0, w, BTN_HEIGHT));

			// Default zoom
			add(new RedButton(TXT_ZOOM_DEFAULT) {
				@Override
				protected void onClick() {
					zoom(PixelScene.defaultZoom);
				}
			}.setRect(btnZoomOut.right(), 0, WIDTH - btnZoomIn.width()
					- btnZoomOut.width(), BTN_HEIGHT));

		} else {

			CheckBox btnScaleUp = new CheckBox(TXT_SCALE_UP) {
				@Override
				protected void onClick() {
					super.onClick();
					Palantir.scaleUp(checked());
				}
			};
			btnScaleUp.setRect(0, 0, WIDTH, BTN_HEIGHT);
			btnScaleUp.checked(Palantir.scaleUp());
			add(btnScaleUp);

			btnImmersive = new CheckBox(TXT_IMMERSIVE) {
				@Override
				protected void onClick() {
					super.onClick();
					Palantir.immerse(checked());
				}
			};
			btnImmersive.setRect(0, btnScaleUp.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			btnImmersive.checked(Palantir.immersed());
			btnImmersive.enable(android.os.Build.VERSION.SDK_INT >= 19);
			add(btnImmersive);

		}

		CheckBox btnMusic = new CheckBox(TXT_MUSIC) {
			@Override
			protected void onClick() {
				super.onClick();
				Palantir.music(checked());
			}
		};
		btnMusic.setRect(0, (btnImmersive != null ? btnImmersive.bottom()
				: BTN_HEIGHT) + GAP, WIDTH, BTN_HEIGHT);
		btnMusic.checked(Palantir.music());
		add(btnMusic);

		CheckBox btnSound = new CheckBox(TXT_SOUND) {
			@Override
			protected void onClick() {
				super.onClick();
				Palantir.soundFx(checked());
				Sample.INSTANCE.play(Assets.SND_CLICK);
			}
		};
		btnSound.setRect(0, btnMusic.bottom() + GAP, WIDTH, BTN_HEIGHT);
		btnSound.checked(Palantir.soundFx());
		add(btnSound);

		if (!inGame) {

			RedButton btnOrientation = new RedButton(orientationText()) {
				@Override
				protected void onClick() {
					Palantir.landscape(!Palantir
							.landscape());
				}
			};
			btnOrientation.setRect(0, btnSound.bottom() + GAP, WIDTH,
					BTN_HEIGHT);
			add(btnOrientation);

			resize(WIDTH, (int) btnOrientation.bottom());

		} else {

			CheckBox btnBrightness = new CheckBox(TXT_BRIGHTNESS) {
				@Override
				protected void onClick() {
					super.onClick();
					Palantir.brightness(checked());
				}
			};
			btnBrightness
					.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
			btnBrightness.checked(Palantir.brightness());
			add(btnBrightness);

//			CheckBox btnQuickSlot = new CheckBox(TXT_QUICKSLOT) {
//				@Override
//				protected void onClick() {
//					super.onClick();
//					Palantir.quickSlots(checked() ? 2 : 1);
//					Toolbar.QuickSlots = checked() ? 2 : 1;
//				}
//			};
//			btnQuickSlot.setRect(0, btnBrightness.bottom() + GAP, WIDTH,
//					BTN_HEIGHT);
//			btnQuickSlot.checked(Palantir.quickSlots() == 2);
//			add(btnQuickSlot);

			resize(WIDTH, (int) btnBrightness.bottom());


		}
	}

	private void zoom(float value) {

		Camera.main.zoom(value);
		Palantir.zoom((int) (value - PixelScene.defaultZoom));

		updateEnabled();
	}

	private void updateEnabled() {
		float zoom = Camera.main.zoom;
		btnZoomIn.enable(zoom < PixelScene.maxZoom);
		btnZoomOut.enable(zoom > PixelScene.minZoom);
	}

	private String orientationText() {
		return Palantir.landscape() ? TXT_SWITCH_PORT
				: TXT_SWITCH_LAND;
	}
}
