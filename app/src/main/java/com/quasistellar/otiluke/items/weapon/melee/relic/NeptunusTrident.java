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
package com.quasistellar.otiluke.items.weapon.melee.relic;

import java.util.ArrayList;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Slow;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.artifacts.Artifact.ArtifactBuff;
import com.quasistellar.otiluke.items.artifacts.CloakOfShadows.cloakStealth;
import com.quasistellar.otiluke.items.weapon.Weapon;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.ui.BuffIndicator;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class NeptunusTrident extends RelicMeleeWeapon {

	public NeptunusTrident() {
		super(6, 1f, 1f);
		// TODO Auto-generated constructor stub
	}

	
	{
		name = "Neptunus Trident";
		image = ItemSpriteSheet.TRIDENT;

		level = 0;
		exp = 0;
		levelCap = 15;

		charge = 0;
		chargeCap = 1000;

		cooldown = 0;
		bones = false;
	}
	
	
	private int distance(){
		return Math.round(level/5);
	}
	
	private void flood(int distance, Hero hero) {
        charge = 0;
		ArrayList<Integer> affected = new ArrayList<Integer>();
		
		int length = Level.getLength();
		int width = Level.getWidth();
		for (int i = width; i < length - width; i++){
			int	 dist = Level.distance(hero.pos, i);
			  if (dist<distance){
			    //GLog.i("TRI2 %s", dist);	
			    if (checkFloodable(i)) {
			    	affected.add(i);
			    	Dungeon.level.map[i]=Terrain.WATER;
					Level.water[i] = true;
			     }
			   }
			  
			}
		//GLog.i("TRI1 %s", length);
		for (int n : affected){
				int t = Terrain.WATER_TILES;
				for (int j = 0; j < Level.NEIGHBOURS4.length; j++) {
					if ((Terrain.flags[Dungeon.level.map[n + Level.NEIGHBOURS4[j]]] & Terrain.UNSTITCHABLE) != 0) {
						t += 1 << j;	
						
					}
				}
				
				Char ch = Actor.findChar(n);
				if (ch != null && ch != hero) {
					Buff.affect(ch, Slow.class, Slow.duration(ch) / 3 + level);
				}
				
				Dungeon.level.map[n] = t;
				//Level.water[i] = true;
				GameScene.updateMap(n);		  
		}
		Dungeon.observe();
		
	}
	
	private boolean checkFloodable (int cell){
		
		boolean check=false;
		
		if ((Dungeon.level.map[cell]==Terrain.EMPTY ||
			Dungeon.level.map[cell]==Terrain.GRASS ||	
			Dungeon.level.map[cell]==Terrain.HIGH_GRASS ||	
			Dungeon.level.map[cell]==Terrain.EMBERS ||
			Dungeon.level.map[cell]==Terrain.EMPTY_DECO ||
			Dungeon.level.map[cell]==Terrain.SIGN ||
			Dungeon.level.map[cell]==Terrain.SHRUB ||
			Dungeon.level.map[cell]==Terrain.STATUE ||
			Dungeon.level.map[cell]==Terrain.SECRET ||
			Dungeon.level.map[cell]==Terrain.AVOID)
			&& 
			!(Dungeon.level.map[cell]==Terrain.UNSTITCHABLE||Dungeon.level.map[cell]==Terrain.WELL)
				){
			check=true;
		} 
		
		if (Level.water[cell]){
			check=true;			
		}
		
		if(Dungeon.level.map[cell]==Terrain.ENTRANCE || Dungeon.level.map[cell]==Terrain.EXIT){
			check=false;
		}
		
		return check;
	}
	
	
	public static final String AC_FLOOD = "FLOOD";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= chargeCap)
			actions.add(AC_FLOOD);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_FLOOD)) {
			int distance=distance();
			GLog.w("Unleash the waters!");
			flood(distance, hero);		
		} else
			super.execute(hero, action);
	}

	
	public class Flooding extends WeaponBuff {

		@Override
		public boolean act() {
			if (charge < chargeCap) {
				charge+=level;
				if (charge >= chargeCap) {
					GLog.w("Your trident draws in the tides.");
				}
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}


		@Override
		public String toString() {
			return "Flooding";
		}

		@Override
		public int icon() {
			if (cooldown == 0)
				return BuffIndicator.NONE;
			else
				return BuffIndicator.NONE;
		}

		@Override
		public void detach() {
			cooldown = 0;
			charge = 0;
			super.detach();
		}

	}
	
	@Override
	protected WeaponBuff passiveBuff() {
		return new Flooding();
	}
	
}


