/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
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
package com.quasistellar.palantir.items;

import java.util.ArrayList;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.mobs.npcs.MirrorImage;
import com.quasistellar.palantir.actors.mobs.npcs.SeekingBombNPC;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class SeekingBombItem extends Item {

	{
		name = "seeking bomb";
		image = ItemSpriteSheet.SEEKING_BOMB;
		defaultAction = AC_LIGHTTHROW;
		stackable = true;
	}

	public MirrorImage mirrorimage;

	// FIXME using a static variable for this is kinda gross, should be a better
	// way
	private static boolean seek = false;

	private static final String AC_LIGHTTHROW = "Light & Throw";
	

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_LIGHTTHROW);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_LIGHTTHROW)) {
			seek = true;
			action = AC_THROW;
		} else {
			seek = false;
		}
		super.execute(hero, action);
	}

	@Override
	protected void onThrow(int cell) {
		if (Actor.findChar(cell) != null) {
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int i : Level.NEIGHBOURS8)
				if (Level.passable[cell + i])
					candidates.add(cell + i);
			int newCell = candidates.isEmpty() ? cell : Random
					.element(candidates);
			
			   if (!Level.pit[newCell] && seek) {
				  SeekingBombNPC.spawnAt(newCell);
				   } else {
			  Dungeon.level.drop(this, newCell).sprite.drop(cell);
			   }
			   
		} else if (!Level.pit[cell] && seek) {
			   SeekingBombNPC.spawnAt(cell);
			
		} else {
			
			super.onThrow(cell);
		}
	}

	

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}


	@Override
	public int price() {
		return 20 * quantity;
	}

	@Override
	public String info() {
		return "A bomb with internal magic machinery. Releasing it will send it after enemies.";
	}

	
}
