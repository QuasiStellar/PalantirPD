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
package com.quasistellar.palantir.items.weapon.missiles;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Drowsy;
import com.quasistellar.palantir.actors.mobs.BlueWraith;
import com.quasistellar.palantir.actors.mobs.DwarfLich;
import com.quasistellar.palantir.actors.mobs.FlyingProtector;
import com.quasistellar.palantir.actors.mobs.Golem;
import com.quasistellar.palantir.actors.mobs.RedWraith;
import com.quasistellar.palantir.actors.mobs.Sentinel;
import com.quasistellar.palantir.actors.mobs.ShadowYog;
import com.quasistellar.palantir.actors.mobs.Skeleton;
import com.quasistellar.palantir.actors.mobs.SpectralRat;
import com.quasistellar.palantir.actors.mobs.Statue;
import com.quasistellar.palantir.actors.mobs.Wraith;
import com.quasistellar.palantir.actors.mobs.Yog;
import com.quasistellar.palantir.actors.mobs.npcs.NPC;
import com.quasistellar.palantir.effects.Speck;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.scrolls.ScrollOfTeleportation;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.utils.Random;

public class RiceBall extends MissileWeapon {
	
	public static final float DURATION = 10f;

	{
		name = "rice dumpling";
		image = ItemSpriteSheet.RICEBALL;

		MIN = 1;
		MAX = 2;
		
		DLY = 0.25f;

		bones = false; 
	}

	public RiceBall() {
		this(1);
	}

	public RiceBall(int number) {
		super();
		quantity = number;
	}

	@Override
	public String desc() {
		return "A satisfying dumpling lovingly crafted from magic rice. "
		       +"Anything that eats would gladly take a dumpling. ";
	}
	
	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
		
			
		if (defender != null 
				&& !(defender instanceof NPC)
				&& !(defender instanceof BlueWraith)
				&& !(defender instanceof Wraith)
				&& !(defender instanceof RedWraith)
				&& !(defender instanceof Sentinel)
				&& !(defender instanceof FlyingProtector)
				&& !(defender instanceof Golem)
				&& !(defender instanceof Skeleton)
				&& !(defender instanceof DwarfLich)
				&& !(defender instanceof Statue)
				&& !(defender instanceof Yog)
				&& !(defender instanceof ShadowYog)
				&& !(defender instanceof SpectralRat)
				) {
  
			Buff.affect(defender, Drowsy.class);
			defender.sprite.centerEmitter().start(Speck.factory(Speck.NOTE), 0.3f, 5);
			
			if  (defender.HP/defender.HT > 0.01f){ 
			int count = 20;
			int pos;
			do {
				pos = Dungeon.level.randomRespawnCell();
				if (count-- <= 0) {
					break;
				}
			} while (pos == -1);

			if (pos == -1) {

				GLog.w(ScrollOfTeleportation.TXT_NO_TELEPORT);

			} else {

				defender.pos = pos;
				defender.sprite.place(defender.pos);
				defender.sprite.visible = Dungeon.visible[pos];
				GLog.i(curUser.name + " teleported " + defender.name
						+ " to somewhere");

			}

		   } else {

			GLog.i("nothing happened");

		  }
	    }
		
		super.proc(attacker, defender, damage);
	}

	@Override
	public Item random() {
		quantity = Random.Int(5, 15);
		return this;
	}

	@Override
	public int price() {
		return quantity * 2;
	}
}
