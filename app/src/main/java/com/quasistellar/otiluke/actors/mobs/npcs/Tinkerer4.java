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
import com.quasistellar.otiluke.items.DewVial;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.Mushroom;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.TinkererSprite;
import com.quasistellar.otiluke.sprites.VillagerSprite;
import com.quasistellar.otiluke.utils.Utils;
import com.quasistellar.otiluke.windows.WndQuest;
import com.quasistellar.otiluke.windows.WndTinkerer3;
import com.watabou.utils.Bundle;

public class Tinkerer4 extends NPC {

	{
		name = "villager";
		spriteClass = VillagerSprite.class;
	}

	private static final String TXT_DUNGEON = "Otiluke? He came through here a few years ago.\n\n"
			                                   +"There used to be a mine to the Southwest of the town. "
			                                   +"It was overrun by demons and Otiluke sealed it off.\n\n"
			                                   +"We have not seen him since.";
	
	
	private static final String TXT_DUNGEON2 = "The mine was covered up by bushes in the Southwest corner of the town.  ";


	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}
	
	private boolean first=true;
	
	private static final String FIRST = "first";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(FIRST, first);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		first = bundle.getBoolean(FIRST);
	}

	@Override
	protected Char chooseEnemy() {
		return null;
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
	public void interact() {

		sprite.turnTo(pos, Dungeon.hero.pos);
		
			if (first){
				tell(TXT_DUNGEON);
				first=false;
			} else {
				tell(TXT_DUNGEON2);
			}				
		
	}

	private void tell(String format, Object... args) {
		GameScene.show(new WndQuest(this, Utils.format(format, args)));
	}

	@Override
	public String description() {
		return "A villager. ";
	}

}
