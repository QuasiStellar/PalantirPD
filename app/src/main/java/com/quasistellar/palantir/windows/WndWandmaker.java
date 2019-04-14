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
package com.quasistellar.palantir.windows;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.Statistics;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.hero.HeroClass;
import com.quasistellar.palantir.actors.mobs.npcs.Wandmaker;
import com.quasistellar.palantir.items.AdamantWand;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.wands.Wand;
import com.quasistellar.palantir.scenes.PixelScene;
import com.quasistellar.palantir.sprites.ItemSprite;
import com.quasistellar.palantir.ui.RedButton;
import com.quasistellar.palantir.ui.Window;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.noosa.BitmapTextMultiline;

public class WndWandmaker extends Window {

	private static final String TXT_MESSAGE = "Oh, I see you have succeeded! I do hope it hasn't troubled you too much. "
			+ "As I promised, you can choose one of my high quality wands.";
	private static final String TXT_BATTLE = "Battle wand";
	private static final String TXT_NON_BATTLE = "Non-battle wand";

	private static final String TXT_ADAMANT = "You might find this raw material useful later on. I'm not powerful enough to work with it.";
	private static final String TXT_WOW = "How did you make it all this way!? I have another reward for you. ";
	private static final String TXT_FARAWELL = "Good luck in your quest, %s!";

	private static final int WIDTH = 120;
	private static final int BTN_HEIGHT = 20;
	private static final float GAP = 2;

	public WndWandmaker(final Wandmaker wandmaker, final Item item) {

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

		RedButton btnBattle = new RedButton(TXT_BATTLE) {
			@Override
			protected void onClick() {
				selectReward(wandmaker, item, Wandmaker.Quest.wand1);
			}
		};
		btnBattle.setRect(0, message.y + message.height() + GAP, WIDTH,
				BTN_HEIGHT);
		add(btnBattle);

		RedButton btnNonBattle = new RedButton(TXT_NON_BATTLE) {
			@Override
			protected void onClick() {
				selectReward(wandmaker, item, Wandmaker.Quest.wand2);
			}
		};
		btnNonBattle.setRect(0, btnBattle.bottom() + GAP, WIDTH, BTN_HEIGHT);
		add(btnNonBattle);

		resize(WIDTH, (int) btnNonBattle.bottom());
	}

	private void selectReward(Wandmaker wandmaker, Item item, Wand reward) {

		hide();

		item.detach(Dungeon.hero.belongings.backpack);

		reward.identify();
		if (reward.doPickUp(Dungeon.hero)) {
			GLog.i(Hero.TXT_YOU_NOW_HAVE, reward.name());
		} else {
			Dungeon.level.drop(reward, wandmaker.pos).sprite.drop();
		}

		
		
		wandmaker.yell(Utils.format(TXT_FARAWELL, Dungeon.hero.givenName()));
		
		if(Dungeon.hero.heroClass==HeroClass.MAGE){
		  Dungeon.level.drop(new AdamantWand(), wandmaker.pos).sprite.drop();
		  wandmaker.yell(TXT_ADAMANT);
		}
		
		if(Dungeon.hero.heroClass!=HeroClass.MAGE && Statistics.sewerKills==Statistics.enemiesSlain){
			Dungeon.level.drop(new AdamantWand(), wandmaker.pos).sprite.drop();
			  wandmaker.yell(TXT_WOW);
		}
		
		wandmaker.destroy();

		wandmaker.sprite.die();

		Wandmaker.Quest.complete();
	}
}
