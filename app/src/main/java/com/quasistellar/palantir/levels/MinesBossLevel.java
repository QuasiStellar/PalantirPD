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
package com.quasistellar.palantir.levels;

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.Alchemy;
import com.quasistellar.palantir.actors.blobs.WellWater;
import com.quasistellar.palantir.actors.mobs.LitTower;
import com.quasistellar.palantir.actors.mobs.MineSentinel;
import com.quasistellar.palantir.actors.mobs.Mob;
import com.quasistellar.palantir.actors.mobs.Otiluke;
import com.quasistellar.palantir.actors.mobs.npcs.SheepSokoban;
import com.quasistellar.palantir.actors.mobs.npcs.SheepSokobanBlack;
import com.quasistellar.palantir.actors.mobs.npcs.SheepSokobanCorner;
import com.quasistellar.palantir.actors.mobs.npcs.SheepSokobanSwitch;
import com.quasistellar.palantir.items.Heap;
import com.quasistellar.palantir.items.Palantir;
import com.quasistellar.palantir.items.artifacts.TimekeepersHourglass;
import com.quasistellar.palantir.items.keys.IronKey;
import com.quasistellar.palantir.items.wands.WandOfFlock.Sheep;
import com.quasistellar.palantir.levels.features.Chasm;
import com.quasistellar.palantir.levels.features.Door;
import com.quasistellar.palantir.levels.features.HighGrass;
import com.quasistellar.palantir.levels.traps.AlarmTrap;
import com.quasistellar.palantir.levels.traps.ChangeSheepTrap;
import com.quasistellar.palantir.levels.traps.FireTrap;
import com.quasistellar.palantir.levels.traps.FleecingTrap;
import com.quasistellar.palantir.levels.traps.GrippingTrap;
import com.quasistellar.palantir.levels.traps.LightningTrap;
import com.quasistellar.palantir.levels.traps.ParalyticTrap;
import com.quasistellar.palantir.levels.traps.PoisonTrap;
import com.quasistellar.palantir.levels.traps.SummoningTrap;
import com.quasistellar.palantir.levels.traps.ToxicTrap;
import com.quasistellar.palantir.plants.Plant;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.windows.WndOtilukeMessage;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;

public class MinesBossLevel extends Level {


	{
		color1 = 0x534f3e;
		color2 = 0xb9d661;
		WIDTH = 48;
		HEIGHT = 48;
		LENGTH = HEIGHT*WIDTH;
	}
		
	
	private boolean entered = false;
	private static final String ENTERED = "entered";
	
	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(ENTERED, entered);
		
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);		
		entered = bundle.getBoolean(ENTERED);
		
	}

  
	
	@Override
	public void press(int cell, Char ch) {
		

		if (ch == Dungeon.hero && !entered) {

			entered = true;
			locked = true;
			GameScene.show(new WndOtilukeMessage());
			
		}
		
		if (pit[cell] && ch == Dungeon.hero) {
			Chasm.heroFall(cell);
			return;
		}

		TimekeepersHourglass.timeFreeze timeFreeze = null;

		if (ch != null)
			timeFreeze = ch.buff(TimekeepersHourglass.timeFreeze.class);

		boolean trap = false;
		
		switch (map[cell]) {

			case Terrain.FLEECING_TRAP:			
					
			if (ch != null && ch==Dungeon.hero){
				trap = true;
				FleecingTrap.trigger(cell, ch);
			}
			break;
			
		case Terrain.CHANGE_SHEEP_TRAP:
			
			if (ch instanceof SheepSokoban || ch instanceof SheepSokobanSwitch || ch instanceof SheepSokobanCorner || ch instanceof Sheep){
				trap = true;
				ChangeSheepTrap.trigger(cell, ch);
			}						
			break;
			
				
		case Terrain.HIGH_GRASS:
			HighGrass.trample(this, cell, ch);
			break;

		case Terrain.WELL:
			WellWater.affectCell(cell);
			break;

		case Terrain.ALCHEMY:
			if (ch == null) {
				Alchemy.transmute(cell);
			}
			break;

		case Terrain.DOOR:
			Door.enter(cell);
			break;
		}

		if (trap){

			if (Dungeon.visible[cell])
				Sample.INSTANCE.play(Assets.SND_TRAP);

			if (ch == Dungeon.hero)
				Dungeon.hero.interrupt();

			set(cell, Terrain.INACTIVE_TRAP);
			GameScene.updateMap(cell);					
		} 

		Plant plant = plants.get(cell);
		if (plant != null) {
			plant.activate(ch);
		}
	}

	
	
	@Override
	public void mobPress(Mob mob) {

		int cell = mob.pos;

		if (pit[cell] && !mob.flying) {
			Chasm.mobFall(mob);
			return;
		}

		boolean trap = true;
		boolean fleece = false;
		boolean sheep = false;
		switch (map[cell]) {

		case Terrain.TOXIC_TRAP:
			ToxicTrap.trigger(cell, mob);
			break;

		case Terrain.FIRE_TRAP:
			FireTrap.trigger(cell, mob);
			break;

		case Terrain.PARALYTIC_TRAP:
			ParalyticTrap.trigger(cell, mob);
			break;
			
		case Terrain.FLEECING_TRAP:
			if (mob instanceof SheepSokoban || mob instanceof SheepSokobanSwitch || mob instanceof SheepSokobanCorner || mob instanceof SheepSokobanBlack || mob instanceof Sheep){
				fleece=true;
			}
			FleecingTrap.trigger(cell, mob);
			break;
			
	
		case Terrain.POISON_TRAP:
			PoisonTrap.trigger(cell, mob);
			break;

		case Terrain.ALARM_TRAP:
			AlarmTrap.trigger(cell, mob);
			break;

		case Terrain.LIGHTNING_TRAP:
			LightningTrap.trigger(cell, mob);
			break;

		case Terrain.GRIPPING_TRAP:
			GrippingTrap.trigger(cell, mob);
			break;

		case Terrain.SUMMONING_TRAP:
			SummoningTrap.trigger(cell, mob);
			break;

		case Terrain.DOOR:
			Door.enter(cell);

		default:
			trap = false;
		}

		if (trap && !fleece && !sheep) {
			if (Dungeon.visible[cell]) {
				Sample.INSTANCE.play(Assets.SND_TRAP);
			}
			set(cell, Terrain.INACTIVE_TRAP);
			GameScene.updateMap(cell);
		}
		
		if (trap && fleece) {
			if (Dungeon.visible[cell]) {
				Sample.INSTANCE.play(Assets.SND_TRAP);
			}
			set(cell, Terrain.WOOL_RUG);
			GameScene.updateMap(cell);
		} 	
		
		if (trap && sheep) {
			if (Dungeon.visible[cell]) {
				Sample.INSTANCE.play(Assets.SND_TRAP);
			}
			set(cell, Terrain.EMPTY);
			GameScene.updateMap(cell);
		}
	
		
		Plant plant = plants.get(cell);
		if (plant != null) {
			plant.activate(mob);
		}
		
		Dungeon.observe();
	}

	@Override
	public String tilesTex() {
		return Assets.TILES_PRISON;
	}

	@Override
	public String waterTex() {
		return Assets.WATER_PRISON;
	}

	@Override
	protected boolean build() {
		
		
		map = Layouts.MINE_BOSS.clone();	
		
		decorate();

		buildFlagMaps();
		cleanWalls();
			
		entrance = 17 + WIDTH * 44;
		exit = 0;


		return true;
	}
	@Override
	protected void decorate() {
		//do nothing, all decorations are hard-coded.
	}

	@Override
	protected void createMobs() {
		 for (int i = 0; i < LENGTH; i++) {				
				if (map[i]==Terrain.SOKOBAN_SHEEP){
					MineSentinel npc = new MineSentinel(); 
					mobs.add(npc); npc.pos = i; 
					Actor.occupyCell(npc);
				} else if (map[i]==Terrain.CORNER_SOKOBAN_SHEEP){
					LitTower npc = new LitTower(); 
					mobs.add(npc); npc.pos = i; 
					Actor.occupyCell(npc);
				}
			}
		 
		    Otiluke mob = new Otiluke(); 
			mobs.add(mob); mob.pos = 33 + WIDTH * 10; 
			Actor.occupyCell(mob);
	}
	
	

	
	@Override
	public String tileDesc(int tile) {
		switch (tile) {
		case Terrain.EMPTY_DECO:
			return "There are old blood stains on the floor.";
		default:
			return super.tileDesc(tile);
		}
	}

	@Override
	public String tileName(int tile) {
		switch (tile) {
		case Terrain.WATER:
			return "Suspiciously colored water";
		case Terrain.HIGH_GRASS:
			return "High blooming flowers";
		default:
			return super.tileName(tile);
		}
	}
	
	

	@Override
	protected void createItems() {
		 			
				drop(new IronKey(Dungeon.depth), 30 + WIDTH * 44).type = Heap.Type.CHEST;	
				drop(new Palantir(), 14 + WIDTH * 10);
		 
	}

	@Override
	public int randomRespawnCell() {
		return -1;
	}
		
	

}
