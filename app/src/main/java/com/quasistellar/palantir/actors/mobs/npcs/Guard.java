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

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.items.TownReturnBeacon;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.GuardSprite;
import com.quasistellar.palantir.utils.Utils;
import com.quasistellar.palantir.windows.WndQuest;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Guard extends NPC {

	{
		name = "guard";
		spriteClass = GuardSprite.class;
	}

	private static final String TXT_DUNGEON = "We used to mine stone ore out of the mines. "
			                                  +"Once they became infested with demons, the passageways started to shift. "
			                                  +"Be careful if you go there, once you leave a floor you may return to find it completely different! ";
	
	
	private static final String TXT_DUNGEON2 = "The building directly to the East from here houses the alter of the Gods. "
			                                   +"Bringing three Norn stones to the alter will be rewarded with a special treasure. ";
	
	private static final String TXT_DUNGEON3 = "You will need this Beacon left by Otiluke. Use it when you need to return to the town. Keep it with you always.";

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
		       tell(TXT_DUNGEON3);		
		       Dungeon.level.drop(new TownReturnBeacon(), Dungeon.hero.pos).sprite.drop();	
	        } else if (Random.Int(2)==0) {
				tell(TXT_DUNGEON);
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
