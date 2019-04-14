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
package com.quasistellar.palantir.actors.mobs.npcs;

import com.quasistellar.palantir.Badges;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.items.Amulet;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.ZotNpcSprite;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.quasistellar.palantir.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class ZotNpc extends NPC {

	{
		name = "zot";
		spriteClass = ZotNpcSprite.class;
	}

	private static final String TXT_DUNGEON = "How long should I wait? You are not in a hurry. "+
			"I hope your tests are successful. You know, one of these sheep climbed here, and I had to turn it into a statue... "+
			"This place is going to collapse soon. We must leave before the Palantir energy fades away!";

	private static final String TXT_DUNGEON2 = "OK, there actually were twenty sheep.";
	
	private static final String TXT_DUNGEON3 = "What? I like statues.";

	@Override
	protected boolean act() {
		throwItem();
		return super.act();
	}
	
	@Override
	protected Char chooseEnemy() {
		return null;
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
		    	
	      if(first) {
		       first=false;
		       tell(TXT_DUNGEON);
		       Amulet reward = new Amulet();
			  if (reward.doPickUp(Dungeon.hero)) {
				  GLog.i(Hero.TXT_YOU_NOW_HAVE, reward.name());
			  } else {
				  Dungeon.level.drop(reward, Dungeon.hero.pos).sprite.drop();
			  }
			  Badges.validatePalantir();
	        } else if (Random.Int(2)==0) {
				tell(TXT_DUNGEON2);
			} else {
				tell(TXT_DUNGEON3);
			}				
		
	}

	private void tell(String format, Object... args) {
		GameScene.show(new WndQuest(this, Utils.format(format, args)));
	}

	@Override
	public String description() {
		return "A villager.";
	}

}
