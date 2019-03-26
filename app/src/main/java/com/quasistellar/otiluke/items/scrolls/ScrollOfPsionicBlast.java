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
package com.quasistellar.otiluke.items.scrolls;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.Statistics;
import com.quasistellar.otiluke.actors.buffs.Blindness;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Invisibility;
import com.quasistellar.otiluke.actors.buffs.Paralysis;
import com.quasistellar.otiluke.actors.mobs.Mob;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class ScrollOfPsionicBlast extends Scroll {

	{
		name = "Scroll of Psionic Blast";
		consumedValue = 10;
        MP_COST = 10;
		bones = true;
	}

	@Override
	protected void doRead() {

		GameScene.flash(0xFFFFFF);

		Sample.INSTANCE.play(Assets.SND_BLAST);
		Invisibility.dispel();

		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (Level.fieldOfView[mob.pos]) {
				mob.damage(mob.HT, this);
			}
		}

		curUser.damage(Math.max(curUser.HT / 5, curUser.HP / 2), this);
		Buff.prolong(curUser, Paralysis.class, Random.Int(4, 6));
		Buff.prolong(curUser, Blindness.class, Random.Int(6, 9));
		Dungeon.observe();

		setKnown();

		curUser.spendAndNext(TIME_TO_READ);

		if (!checkOriginalGenMobs() &&
				!Dungeon.level.cleared && Dungeon.dewDraw && Dungeon.depth>2 && Dungeon.depth<25 && !Dungeon.bossLevel(Dungeon.depth)
				){
				Dungeon.level.cleared=true;
				GameScene.levelCleared();		
				if(Dungeon.depth>0){Statistics.prevfloormoves=Math.max(Dungeon.pars[Dungeon.depth]-Dungeon.level.currentmoves,0);
				   if (Statistics.prevfloormoves>1){
				     GLog.h("Level cleared in %s moves under goal.", Statistics.prevfloormoves);
				   } else if (Statistics.prevfloormoves==1){
				     GLog.h("Level cleared in 1 move under goal."); 
				   } else if (Statistics.prevfloormoves==0){
					 GLog.h("Level cleared over goal moves.");
				   }
				} 
		}
		
		if (!curUser.isAlive()) {
			Dungeon.fail(Utils.format(ResultDescriptions.ITEM, name));
			GLog.n("The Psionic Blast tears your mind apart...");
		}
	}
	
	public boolean checkOriginalGenMobs (){
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])) {
			if (mob.originalgen){return true;}
		 }	
		return false;
	}

	@Override
	public String desc() {
		return "This scroll contains destructive energy that can be psionically channeled to tear apart "
				+ "the minds of all visible creatures. The power unleashed by the scroll will also temporarily "
				+ "blind, stun, and seriously harm the reader.";
	}

	@Override
	public int price() {
		return isKnown() ? 80 * quantity : super.price();
	}
}
