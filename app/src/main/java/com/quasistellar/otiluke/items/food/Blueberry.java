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
package com.quasistellar.otiluke.items.food;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.buffs.Awareness;
import com.quasistellar.otiluke.actors.buffs.BerryRegeneration;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Hunger;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.items.misc.Spectacles.MagicSight;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Blueberry extends Food {

	{
		name = "dungeon blue berry";
		image = ItemSpriteSheet.SEED_BLUEBERRY;
		energy = (Hunger.STARVING - Hunger.HUNGRY)/10;
		message = "Juicy!";
		hornValue = 1;
		bones = false;
	}

	
	private static final String TXT_PREVENTING = "The magic of this place prevents this type of scrying! ";
	
	@Override
	public void execute(Hero hero, String action) {

		super.execute(hero, action);
		
         if (action.equals(AC_EAT)) {
			
			if (Dungeon.depth>50 && Dungeon.hero.buff(MagicSight.class) == null ){
				GLog.w(TXT_PREVENTING);
				return;
			}

		}

		if (action.equals(AC_EAT)) {

			if (Random.Float()<0.75f) {
			
				int length = Level.getLength();
				int[] map = Dungeon.level.map;
				boolean[] mapped = Dungeon.level.mapped;
				boolean[] discoverable = Level.discoverable;

				boolean noticed = false;

				for (int i = 0; i < length; i++) {

					int terr = map[i];

					if (discoverable[i]) {

						mapped[i] = true;
						if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

							Level.set(i, Terrain.discover(terr));
							GameScene.updateMap(i);

							if (Dungeon.visible[i]) {
								GameScene.discoverTile(i, terr);
								discover(i);

								noticed = true;
							}
						}
					}
				}
				Dungeon.observe();
				
				if (noticed) {
					Sample.INSTANCE.play(Assets.SND_SECRET);
				}

				GLog.p("The blueberry floods your mind with knowledge about the current floor.");
				
				Buff.affect(hero, Awareness.class, 10f);
				Dungeon.observe();

					
			} else {
			
				GLog.w("The berry releases energy into your body!");
				Buff.affect(hero, BerryRegeneration.class).level(hero.HT+hero.HT);
				
				int length = Level.getLength();
				int[] map = Dungeon.level.map;
				boolean[] mapped = Dungeon.level.mapped;
				boolean[] discoverable = Level.discoverable;

				boolean noticed = false;

				for (int i = 0; i < length; i++) {

					int terr = map[i];

					if (discoverable[i]) {

						mapped[i] = true;
						if ((Terrain.flags[terr] & Terrain.SECRET) != 0) {

							Level.set(i, Terrain.discover(terr));
							GameScene.updateMap(i);

							if (Dungeon.visible[i]) {
								GameScene.discoverTile(i, terr);
								discover(i);

								noticed = true;
							}
						}
					}
				}
				Dungeon.observe();
				
				if (noticed) {
					Sample.INSTANCE.play(Assets.SND_SECRET);
				}

				GLog.p("The blueberry floods your mind with knowledge about the current floor.");
				
				Buff.affect(hero, Awareness.class, 10f);
				Dungeon.observe();
							
				
			}
		}
	}	
	
	public static void discover(int cell) {
		CellEmitter.get(cell).start(Speck.factory(Speck.DISCOVER), 0.1f, 4);
	}
	
	@Override
	public String info() {
		return "A mysterious blue berry found in the depths of the dungeon. "
				+"As these berries grow slowly over several years, they soak in knowledge of the dungeon." ;
	}

	@Override
	public int price() {
		return 20 * quantity;
	}
	
	public Blueberry() {
		this(1);
	}

	public Blueberry(int value) {
		this.quantity = value;
	}
	
}
