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
package com.quasistellar.palantir.items;

import java.util.ArrayList;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.Statistics;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.mobs.Mob;
import com.quasistellar.palantir.actors.mobs.pets.PET;
import com.quasistellar.palantir.items.artifacts.DriedRose;
import com.quasistellar.palantir.items.artifacts.TimekeepersHourglass;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.scenes.InterlevelScene;
import com.quasistellar.palantir.sprites.ItemSprite.Glowing;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;

public class BookOfDead extends Item {

	private static final String TXT_PREVENTING = "Strong magic aura of this place prevents you from reading the book!";
	private static final String TXT_PREVENTING2 = "Darkness blurs your eyes as you try to read! You will need to find the exit pedestal with the SanChikarah...";
	
	public static final float TIME_TO_USE = 1;

	public static final String AC_PORT = "READ";

	private int specialLevel = 31;
	private int returnDepth = -1;
	private int returnPos;

	{
		name = "book of the dead";
		image = ItemSpriteSheet.BOOKOFDEAD;

		unique = true;
	}

	
	private static final String DEPTH = "depth";
	private static final String POS = "pos";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(DEPTH, returnDepth);
		if (returnDepth != -1) {
			bundle.put(POS, returnPos);
		}
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		returnDepth = bundle.getInt(DEPTH);
		returnPos = bundle.getInt(POS);
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_PORT);
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {

		if (action == AC_PORT) {

			if (Dungeon.bossLevel() || hero.petfollow) {
				hero.spend(TIME_TO_USE);
				GLog.w(TXT_PREVENTING);
				return;
			}
			
			if (Dungeon.depth==specialLevel && hero.pos != Dungeon.level.exit) {
				hero.spend(TIME_TO_USE);
				GLog.w(TXT_PREVENTING2);
				return;
			}
			
			if (Dungeon.depth!=specialLevel && Dungeon.depth>26) {
				hero.spend(TIME_TO_USE);
				GLog.w(TXT_PREVENTING2);
				return;
			}



		}

		if (action == AC_PORT) {
			
			 hero.spend(TIME_TO_USE);

			if (Dungeon.depth==specialLevel){
				this.doDrop(hero);
			}
			Buff buff = Dungeon.hero
						.buff(TimekeepersHourglass.timeFreeze.class);
				if (buff != null)
					buff.detach();

				for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0]))
					if (mob instanceof DriedRose.GhostHero)
						mob.destroy();
              if (Dungeon.depth<27){
            	returnDepth = Dungeon.depth;
       			returnPos = hero.pos;
				InterlevelScene.mode = InterlevelScene.Mode.PORT1;
			} else {
				 checkPetPort();
				 removePet();
				InterlevelScene.mode = InterlevelScene.Mode.RETURN;	
			}
             
				InterlevelScene.returnDepth = returnDepth;
				InterlevelScene.returnPos = returnPos;
				Game.switchScene(InterlevelScene.class);
					
		} else {

			super.execute(hero, action);

		}
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
		for (int n : Level.NEIGHBOURS8) {
			int c =  Dungeon.hero.pos + n;
			if (Actor.findChar(c) instanceof PET) {
				return true;
			}
		}
		return false;
	}
	
	private void checkPetPort(){
		PET pet = checkpet();
		if(pet!=null && checkpetNear()){
		  //GLog.i("I see pet");
		  Dungeon.hero.petType=pet.type;
		  Dungeon.hero.petLevel=pet.level;
		  Dungeon.hero.petKills=pet.kills;	
		  Dungeon.hero.petHP=pet.HP;
		  Dungeon.hero.petExperience=pet.experience;
		  Dungeon.hero.petCooldown=pet.cooldown;
		  pet.destroy();
		  Dungeon.hero.petfollow=true;
		} else if (Dungeon.hero.haspet && Dungeon.hero.petfollow) {
			Dungeon.hero.petfollow=true;
		} else {
			Dungeon.hero.petfollow=false;
		}
		
	}
	
	private void removePet(){
		if (Dungeon.hero.haspet && !Dungeon.hero.petfollow){
		 for (Mob mob : Dungeon.level.mobs) {
				if(mob instanceof PET) {				 
					Dungeon.hero.haspet=false;
					Dungeon.hero.petCount++;
					mob.destroy();				
				}
			  }
		}
	}
	
	
	@Override
	public int price() {
		if (!Statistics.amuletObtained){return 9000 * quantity;}
		else {return 300*quantity;}
	}
	
	public void reset() {
		returnDepth = -1;
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	private static final Glowing BLACK = new Glowing(0x00000);

	@Override
	public Glowing glowing() {
		return BLACK;
	}
	
	@Override
	public String info() {
		return "This black book seems to dim the light around it. You feel a horrific presence ";
	}

}