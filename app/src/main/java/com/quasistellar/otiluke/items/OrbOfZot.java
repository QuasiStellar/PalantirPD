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
package com.quasistellar.otiluke.items;

import java.util.ArrayList;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Badges;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.Statistics;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.mobs.Mob;
import com.quasistellar.otiluke.actors.mobs.OrbOfZotMob;
import com.quasistellar.otiluke.effects.particles.ElmoParticle;
import com.quasistellar.otiluke.items.artifacts.DriedRose;
import com.quasistellar.otiluke.items.artifacts.TimekeepersHourglass;
import com.quasistellar.otiluke.items.journalpages.Town;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.scenes.InterlevelScene;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class OrbOfZot extends Item {

	//private static final String AC_END = "END THE GAME";

	{
		name = "Orb Of Zot";
		image = ItemSpriteSheet.ORBOFZOT;
		defaultAction = AC_ACTIVATETHROW;
		unique = true;
	}

	private static boolean activate = false;

	private static final String AC_ACTIVATETHROW = "Activate & Throw";
	private static final String AC_BREAK = "Break Open";
	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_ACTIVATETHROW);
		actions.add(AC_BREAK);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		
		if (action.equals(AC_ACTIVATETHROW)) {
			activate = true;
			action = AC_THROW;
		} else {
			activate = false;
		}
		
		if (action.equals(AC_BREAK)){
			Dungeon.level.drop(new Town(), Dungeon.hero.pos).sprite.drop(Dungeon.hero.pos);
			this.detachAll(Dungeon.hero.belongings.backpack);
			Sample.INSTANCE.play(Assets.SND_BLAST);
			hero.sprite.emitter().burst(ElmoParticle.FACTORY, 12);
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
			
			   if (!Level.pit[newCell] && activate) {
				   OrbOfZotMob.spawnAt(newCell);
			   } else {
			   Dungeon.level.drop(this, newCell).sprite.drop(cell);
			   }
			   
		} else if (!Level.pit[cell] && activate) {
			  OrbOfZotMob.spawnAt(cell);
		} else {
			
			super.onThrow(cell);
		}

	}
	
	@Override
	public boolean doPickUp(Hero hero) {
		if (super.doPickUp(hero)) {

			if (!Statistics.orbObtained) {
				Statistics.orbObtained = true;
				//showAmuletScene(true);
			}

			return true;
		} else {
			return false;
		}
	}
/*
	private void showAmuletScene(boolean showText) {
		try {
			Dungeon.saveAll();
			AmuletScene.noText = !showText;
			Game.switchScene(AmuletScene.class);
		} catch (IOException e) {
		}
	}
*/

	
	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public String info() {
		return "The Orb of Zot is source of unlimited power created by the wizard Zot. "
				+ "Apparently, Yog was harnessing its power to infest the dungeon. ";
	}
}
