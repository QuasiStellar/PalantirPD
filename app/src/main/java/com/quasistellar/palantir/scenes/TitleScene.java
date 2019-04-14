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

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.GamesInProgress;
import com.quasistellar.palantir.Palantir;
import com.quasistellar.palantir.actors.hero.HeroClass;
import com.quasistellar.palantir.effects.BannerSprites;
import com.quasistellar.palantir.effects.Fireball;
import com.quasistellar.palantir.ui.Archs;
import com.quasistellar.palantir.ui.ExitButton;
import com.quasistellar.palantir.ui.PrefsButton;
import com.quasistellar.palantir.windows.WndOptions;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;

public class TitleScene extends PixelScene {

	private static final String TXT_PLAY = "Play";
	private static final String TXT_HIGHSCORES = "Rankings";
	private static final String TXT_BADGES = "Badges";
	private static final String TXT_ABOUT = "About";
	private static final String TXT_LOAD = "Game Load";
	private static final String TXT_LDESC = "Do you want to load your progress?";
	private static final String TXT_YES = "Yes";
	private static final String TXT_NG = "Start new game";
	private static final String TXT_NO = "No";
	private static final String TXT_REALLY = "Really?";


	@Override
	public void create() {

		super.create();

		int gameversion = Palantir.version();

		if (gameversion != Game.versionCode) {
			// new intro, make older players see it again.
			if (gameversion < 9)
				Palantir.intro(true);
			Game.switchScene(WelcomeScene.class);
		}

		Music.INSTANCE.play(Assets.THEME, true);
		Music.INSTANCE.volume(1f);

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		Archs archs = new Archs();
		archs.setSize(w, h);
		add(archs);

		Image title = BannerSprites.get(BannerSprites.Type.PIXEL_DUNGEON);
		add(title);

		float height = title.height
				+ (Palantir.landscape() ? DashboardItem.SIZE
						: DashboardItem.SIZE * 2);

		title.x = (w - title.width()) / 2;
		title.y = (h - height) / 2;

		placeTorch(title.x + title.width / 2, title.y + 15);
		//placeTorch(title.x + title.width - 18, title.y + 20);

		Image signs = new Image(
				BannerSprites.get(BannerSprites.Type.PIXEL_DUNGEON_SIGNS)) {
			private float time = 0;

			@Override
			public void update() {
				super.update();
				am = (float) Math.sin(-(time += Game.elapsed));
			}

			@Override
			public void draw() {
				GLES20.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
				super.draw();
				GLES20.glBlendFunc(GL10.GL_SRC_ALPHA,
						GL10.GL_ONE_MINUS_SRC_ALPHA);
			}
		};
		signs.x = title.x;
		signs.y = title.y;
		add(signs);

		DashboardItem btnBadges = new DashboardItem(TXT_BADGES, 3) {
			@Override
			protected void onClick() {
				Palantir.switchNoFade(BadgesScene.class);
			}
		};
		add(btnBadges);

		DashboardItem btnAbout = new DashboardItem(TXT_ABOUT, 1) {
			@Override
			protected void onClick() {
				Palantir.switchNoFade(AboutScene.class);
			}
		};
		add(btnAbout);

		DashboardItem btnPlay = new DashboardItem(TXT_PLAY, 0) {
			@Override
			protected void onClick() {
				//Palantir.switchNoFade(StartScene.class);
				GamesInProgress.Info info = GamesInProgress.check(HeroClass.WARRIOR);
				if (info != null) {
					TitleScene.this.add(new WndOptions(TXT_LOAD, TXT_LDESC, TXT_YES, TXT_NG) {
						@Override
						protected void onSelect(int index) {
							if (index == 0) {
								InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
								Game.switchScene(InterlevelScene.class);
							} else {
								TitleScene.this.add(new WndOptions(TXT_REALLY, "", TXT_YES, TXT_NO) {
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

		DashboardItem btnHighscores = new DashboardItem(TXT_HIGHSCORES, 2) {
			@Override
			protected void onClick() {
				Palantir.switchNoFade(RankingsScene.class);
			}
		};
		add(btnHighscores);

		if (Palantir.landscape()) {
			float y = (h + height) / 2 - DashboardItem.SIZE;
			btnHighscores.setPos(w / 2 - btnHighscores.width(), y);
			btnBadges.setPos(w / 2, y);
			btnPlay.setPos(btnHighscores.left() - btnPlay.width(), y);
			btnAbout.setPos(btnBadges.right(), y);
		} else {
			btnBadges.setPos(w / 2 - btnBadges.width(), (h + height) / 2
					- DashboardItem.SIZE);
			btnAbout.setPos(w / 2, (h + height) / 2 - DashboardItem.SIZE);
			btnPlay.setPos(w / 2 - btnPlay.width(), btnAbout.top()
					- DashboardItem.SIZE);
			btnHighscores.setPos(w / 2, btnPlay.top());
		}

		BitmapText version = new BitmapText("v " + Game.version + "", font1x);
		version.measure();
		version.hardlight(0xCCCCCC);
		version.x = w - version.width();
		version.y = h - version.height();

		add(version);

		PrefsButton btnPrefs = new PrefsButton();
		btnPrefs.setPos(0, 0);
		add(btnPrefs);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos(w - btnExit.width(), 0);
		add(btnExit);

		fadeIn();
	}

	private void placeTorch(float x, float y) {
		Fireball fb = new Fireball();
		fb.setPos(x, y);
		add(fb);
	}

	private static class DashboardItem extends Button {

		public static final float SIZE = 48;

		private static final int IMAGE_SIZE = 32;

		private Image image;
		private BitmapText label;

		public DashboardItem(String text, int index) {
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

			image = new Image(Assets.DASHBOARD);
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
			label.y = align(image.y + image.height() + 2);
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
