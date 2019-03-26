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
package com.quasistellar.otiluke.actors.mobs.npcs;

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.items.ActiveMrDestructo;
import com.quasistellar.otiluke.items.InactiveMrDestructo;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.Mushroom;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.TinkererSprite;
import com.quasistellar.otiluke.utils.Utils;
import com.quasistellar.otiluke.windows.WndQuest;
import com.quasistellar.otiluke.windows.WndTinkerer2;

public class Tinkerer2 extends NPC {

	{
		name = "tinkerer";
		spriteClass = TinkererSprite.class;
	}

	private static final String TXT_DUNGEON = "I'm scavenging for toadstool mushrooms. "
			+ "Could you bring me any toadstool mushrooms you find? ";
	

	private static final String TXT_MUSH = "Any luck finding toadstool mushrooms, %s?";

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}

	@Override
	public int defenseSkill(Char enemy) {
		return 1000;
	}

	@Override
	public String defenseVerb() {
		return "absorbed";
	}

	@Override
	public void damage(int dmg, Object src) {
	}

	@Override
	public void add(Buff buff) {
	}

	@Override
	public boolean reset() {
		return true;
	}

	@Override
	public void interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
		Item item = Dungeon.hero.belongings.getItem(Mushroom.class);
		Item inmrd = Dungeon.hero.belongings.getItem(InactiveMrDestructo.class);
		Item acmrd = Dungeon.hero.belongings.getItem(ActiveMrDestructo.class);
			if (item != null && inmrd != null) {
				GameScene.show(new WndTinkerer2(this, item, inmrd));
			} else if (item != null && acmrd != null) {
				GameScene.show(new WndTinkerer2(this, item, acmrd));
			} else if (item != null) {
				GameScene.show(new WndTinkerer2(this, item, null));
			} else {
				tell(TXT_DUNGEON);
			}
		
	}

	private void tell(String format, Object... args) {
		GameScene.show(new WndQuest(this, Utils.format(format, args)));
	}

	@Override
	public String description() {
		return "The tinkerer is protected by a magical shield. ";
	}

}
