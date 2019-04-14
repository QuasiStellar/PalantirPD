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

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.GamesInProgress;
import com.quasistellar.palantir.Palantir;
import com.quasistellar.palantir.actors.hero.HeroClass;
import com.quasistellar.palantir.ui.Archs;
import com.quasistellar.palantir.ui.ExitButton;
import com.quasistellar.palantir.windows.WndOptions;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;

public class LevelScene extends PixelScene {

	private static final String TXT_STORY_MODE = "Story Mode";
	private static final String TXT_LOAD = "Game Load";
	private static final String TXT_LDESC = "Do you want to load your progress?";
	private static final String TXT_YES = "Yes";
	private static final String TXT_NG = "Start new game";
	private static final String TXT_NO = "No";
	private static final String TXT_REALLY = "Really?";

	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		LevelScene.StoryMode btnPlay = new LevelScene.StoryMode(TXT_STORY_MODE, 0) {
			@Override
			protected void onClick() {
				//Palantir.switchNoFade(StartScene.class);
				GamesInProgress.Info info = GamesInProgress.check(HeroClass.WARRIOR);
				if (info != null) {
					LevelScene.this.add(new WndOptions(TXT_LOAD, TXT_LDESC, TXT_YES, TXT_NG) {
						@Override
						protected void onSelect(int index) {
							if (index == 0) {
								InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
								Game.switchScene(InterlevelScene.class);
							} else {
								LevelScene.this.add(new WndOptions(TXT_REALLY, "", TXT_YES, TXT_NO) {
									@Override
									protected void onSelect(int index) {
										if (index == 0) {
											Dungeon.hero = null;
											InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
											Game.switchScene(InterlevelScene.class);
										}
									};
								});
							}
						};
					});
				} else {
					Dungeon.hero = null;
					InterlevelScene.mode = InterlevelScene.Mode.DESCEND;

					if (Palantir.intro()) {
						Palantir.intro(false);
						Game.switchScene(IntroScene.class);
					} else {
						Game.switchScene(InterlevelScene.class);
					}
				}
			}
		};
		add(btnPlay);

		btnPlay.setPos((w - btnPlay.width()) / 2, 30);

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

	private static class StoryMode extends Button {

		public static final float SIZE = 48;

		private static final int IMAGE_SIZE = 32;

		private Image image;
		private BitmapText label;

		public StoryMode(String text, int index) {
			super();

			image.frame(image.texture.uvRect(index * IMAGE_SIZE, 0, (index + 1)
					* IMAGE_SIZE, IMAGE_SIZE));
			this.label.text(text);
			this.label.measure();

			setSize(SIZE, SIZE);
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = new Image(Assets.STORY_MODE);
			add(image);

			label = createText(9);
			add(label);
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = align(x + (width - image.width()) / 2);
			image.y = align(y);

			label.x = align(x + (width - label.width()) / 2);
			label.y = align(image.y + image.height() / 2 - 4);
		}

		@Override
		protected void onTouchDown() {
			image.brightness(1.5f);
			Sample.INSTANCE.play(Assets.SND_CLICK, 1, 1, 0.8f);
		}

		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
	}
}
