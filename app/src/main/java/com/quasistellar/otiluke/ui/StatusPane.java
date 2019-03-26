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
package com.quasistellar.otiluke.ui;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.effects.particles.BloodParticle;
import com.quasistellar.otiluke.items.keys.IronKey;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.scenes.PixelScene;
import com.quasistellar.otiluke.sprites.HeroSprite;
import com.quasistellar.otiluke.windows.WndGame;
import com.quasistellar.otiluke.windows.WndHero;
import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;

public class StatusPane extends Component {

	private NinePatch shield;

	private int lastLvl = -1;
	private int lastKeys = -1;

	private BitmapText depth;
	private BitmapText keys;

	private DangerIndicator danger;

	private MenuButton btnMenu;

	@Override
	protected void createChildren() {

		shield = new NinePatch(Assets.STATUS, 80, 0, 30 + 18, 0);
		add(shield);

		btnMenu = new MenuButton();
		add(btnMenu);

		depth = new BitmapText(Integer.toString(Dungeon.depth),
				PixelScene.font1x);
		depth.hardlight(0xCACFC2);
		depth.measure();
		add(depth);

		Dungeon.hero.belongings.countIronKeys();
		keys = new BitmapText(PixelScene.font1x);
		keys.hardlight(0xCACFC2);
		add(keys);

		danger = new DangerIndicator();
		add(danger);

//		buffs = new BuffIndicator(Dungeon.hero);
//		add(buffs);
	}

	@Override
	protected void layout() {

		height = 32;

		shield.size(width, shield.height);

		depth.x = width - 24 - depth.width() - 18;
		depth.y = 5;

		keys.y = 5;

		danger.setPos(width - danger.width(), 18);

		//buffs.setPos(34, 16);

		btnMenu.setPos(width - btnMenu.width(), 1);
	}

	@Override
	public void update() {
		super.update();

		if (Dungeon.hero.lvl != lastLvl) {

			int k = IronKey.curDepthQuantity;
			if (k != lastKeys) {
				lastKeys = k;
				keys.text(Integer.toString(lastKeys));
				keys.measure();
				keys.x = width - 8 - keys.width() - 18;
			}
		}
	}

	private static class MenuButton extends Button {

		private Image image;

		public MenuButton() {
			super();

			width = image.width + 4;
			height = image.height + 4;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = new Image(Assets.STATUS, 114, 3, 12, 11);
			add(image);
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = x + 2;
			image.y = y + 2;
		}

		@Override
		protected void onTouchDown() {
			image.brightness(1.5f);
			Sample.INSTANCE.play(Assets.SND_CLICK);
		}

		@Override
		protected void onTouchUp() {
			image.resetColor();
		}

		@Override
		protected void onClick() {
			GameScene.show(new WndGame());
		}
	}
}
