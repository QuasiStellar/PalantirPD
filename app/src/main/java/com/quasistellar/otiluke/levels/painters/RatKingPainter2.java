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
package com.quasistellar.otiluke.levels.painters;

import com.quasistellar.otiluke.actors.mobs.npcs.RatKingDen;
import com.quasistellar.otiluke.items.ActiveMrDestructo;
import com.quasistellar.otiluke.items.Egg;
import com.quasistellar.otiluke.items.Generator;
import com.quasistellar.otiluke.items.Gold;
import com.quasistellar.otiluke.items.Heap;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.SeekingClusterBombItem;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Room;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.plants.Phaseshift;
import com.quasistellar.otiluke.plants.Starflower;
import com.watabou.utils.Random;

public class RatKingPainter2 extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY_SP);

		Room.Door entrance = room.entrance();
		entrance.set(Room.Door.Type.HIDDEN);
		int door = entrance.x + entrance.y * Level.getWidth();
		
		//Dungeon.ratChests=0;

		for (int i = room.left + 1; i < room.right; i++) {
			addChest(level, (room.top + 1) * Level.getWidth() + i, door);
			addChest(level, (room.bottom - 1) * Level.getWidth() + i, door);
		}

		for (int i = room.top + 2; i < room.bottom - 1; i++) {
			addChest(level, i * Level.getWidth() + room.left + 1, door);
			addChest(level, i * Level.getWidth() + room.right - 1, door);
		}

		while (true) {
			Heap chest = level.heaps.get(room.random());
			if (chest != null) {
				chest.type = Heap.Type.MIMIC;
				break;
			}
		}

		RatKingDen king = new RatKingDen();
		king.pos = room.random(1);
		level.mobs.add(king);
	}

	private static void addChest(Level level, int pos, int door) {

		if (pos == door - 1 || pos == door + 1 || pos == door - Level.getWidth()
				|| pos == door + Level.getWidth()) {
			return;
		}

		Item prize;
		switch (Random.Int(10)) {
		case 0:
			prize = new Egg();
			break;
		case 1:
			prize = new Phaseshift.Seed();
			break;
		case 2:
			prize = Generator.random(Generator.Category.BERRY);
			break;
		case 3:
			prize =  new Starflower.Seed();
			break;
		case 5:
			prize =  new ActiveMrDestructo();
			break;
		case 6:
			prize =  new SeekingClusterBombItem();
			break;
		default:
			prize = new Gold(Random.IntRange(1, 5));
			break;
		}

		level.drop(prize, pos).type = Heap.Type.CHEST;
		//Dungeon.ratChests++;
	}
}