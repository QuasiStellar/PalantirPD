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
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.MagicMissile;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.effects.particles.ShadowParticle;
import com.quasistellar.otiluke.items.wands.WandOfBlink;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.sprites.SokobanBlackSheepSprite;
import com.quasistellar.otiluke.sprites.SokobanSheepSwitchSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class SheepSokobanBlack extends NPC {

	private static final String[] QUOTES = { "Baa!", "Baa?", "Baa.",
	"Baa..." };

{
name = "sheep";
spriteClass = SokobanBlackSheepSprite.class;
}


@Override
protected boolean act() {
	throwItem();
	return super.act();
}

@Override
public void damage(int dmg, Object src) {
}
@Override
public void add(Buff buff) {
}

@Override
public String description() {
return "This is a magic sheep. What's so magical about it? You can't kill it. "
		+ "It will stand anywhere you move it, all the while chewing cud with a blank stare.";
}

@Override
public void interact() {
	int newPos = -1;
	int curPos = pos;
	int dist = 6;
	boolean moved = false;

	int traps2 = Dungeon.level.countFleeceTraps(pos, 2);
	int traps3 = Dungeon.level.countFleeceTraps(pos, 3);
	int traps4 = Dungeon.level.countFleeceTraps(pos, 4);
	int traps5 = Dungeon.level.countFleeceTraps(pos, 5);
	int traps6 = Dungeon.level.countFleeceTraps(pos, 6);

//	GLog.w(String.valueOf(traps2));
//	GLog.w(String.valueOf(traps3));
//	GLog.w(String.valueOf(traps4));
//	GLog.w(String.valueOf(traps5));
//	GLog.w(String.valueOf(traps6));

	if (traps2>0) {
		newPos = Dungeon.level.randomRespawnCellSheep(pos, 1);
	} else if (traps3>0) {
		newPos = Dungeon.level.randomRespawnCellSheep(pos, 2);
	} else if (traps4>0) {
		newPos = Dungeon.level.randomRespawnCellSheep(pos, 3);
	} else if (traps5>0) {
		newPos = Dungeon.level.randomRespawnCellSheep(pos, 4);
	} else if (traps6>0) {
		newPos = Dungeon.level.randomRespawnCellSheep(pos, 5);
	}

	if (newPos == -1) {
		yell(Random.element(QUOTES));
		//yell("pos = " + dist);		
		destroy();
		sprite.killAndErase();
		sprite.emitter().burst(ShadowParticle.UP, 5);
		moved=true;
	} else {
		yell("BAA!");
		//yell("pos = " + dist);
		Actor.freeCell(pos);
		CellEmitter.get(pos).start(Speck.factory(Speck.LIGHT), 0.2f, 3);
		MagicMissile.purpleLight(Dungeon.hero.sprite.parent, curPos, newPos,
				new Callback() {
					@Override
					public void call() {

					}
				});
		pos = newPos;
		move(pos);
		moved=true;
	}	

	if(moved){
	      Dungeon.hero.sprite.move(Dungeon.hero.pos, curPos);
		  Dungeon.hero.move(curPos);
		  Dungeon.hero.spendAndNext(1);
		}
	
	Dungeon.hero.spend(1 / Dungeon.hero.speed());
	Dungeon.hero.busy();    
 }

}
