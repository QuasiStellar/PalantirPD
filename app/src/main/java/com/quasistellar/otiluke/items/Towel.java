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

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.buffs.Bleeding;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Ooze;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.mobs.Mob;
import com.quasistellar.otiluke.actors.mobs.pets.PET;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;

public class Towel extends Item {

	private static final String TXT_PREVENTING = "This is not the place to use the towel.";
	private static final String TXT_APPLY = "You apply the towel.";
	private static final String TXT_END = "Your towel disintegrates.";
	
	public static final float TIME_TO_USE = 1;

	public static final String AC_TOWEL = "APPLY";
	public static final String AC_TOWEL_PET = "APPLY TO PET";

		{
		name = "towel";
		image = ItemSpriteSheet.TOWEL;
		unique = true;
		level = 10;

		}

	
	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		
		actions.add(AC_TOWEL);
		
		if(checkpet()!=null && checkpetNear()){
			actions.add(AC_TOWEL_PET);
		}
		
		return actions;
	}

	private PET checkpet(){
		for (Mob mob : Dungeon.level.mobs) {
			if(mob instanceof PET) {
				return (PET) mob;
			}
		}	
		return null;
	}
	
	private boolean checkpetNear(){
		Hero hero = Dungeon.hero;
		for (int n : Level.NEIGHBOURS8) {
			int c = hero.pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public void execute(Hero hero, String action) {

		
		if (action == AC_TOWEL) {
           	  		  		 
			  Buff.detach(hero, Bleeding.class);
			  Buff.detach(hero, Ooze.class);
			  Dungeon.observe();
			  			  
			  GLog.w(TXT_APPLY);  
			  
			  level -= 1;
			  if (level==0){
			     detach(Dungeon.hero.belongings.backpack);	
			     GLog.w(TXT_END);
			  }
				  
	
		} else if (action == AC_TOWEL_PET) {
			
			PET pet = checkpet();
			
			  Buff.detach(pet, Bleeding.class);
			  Buff.detach(pet, Ooze.class);
			  Dungeon.observe();
			  			  
			  GLog.w(TXT_APPLY);  
			  
			  level -= 1;
			  if (level==0){
			     detach(Dungeon.hero.belongings.backpack);	
			     GLog.w(TXT_END);
			  }
			
				
		} else {
			
			super.execute(hero, action);
		}
	}

	@Override
	public int price() {
		return 500 * quantity;
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
	public String info() {
		return "Don't forget your towel! ";
	}

}
