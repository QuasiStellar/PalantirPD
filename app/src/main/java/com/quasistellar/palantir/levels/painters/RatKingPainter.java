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
package com.quasistellar.palantir.levels.painters;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.Statistics;
import com.quasistellar.palantir.actors.mobs.npcs.RatKing;
import com.quasistellar.palantir.items.Generator;
import com.quasistellar.palantir.items.Gold;
import com.quasistellar.palantir.items.Heap;
import com.quasistellar.palantir.items.Item;
import com.quasistellar.palantir.items.journalpages.Town;
import com.quasistellar.palantir.items.weapon.missiles.MissileWeapon;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.levels.Room;
import com.quasistellar.palantir.levels.Terrain;
import com.watabou.utils.Random;

public class RatKingPainter extends Painter {

	static boolean page = false;
	
	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY_SP);

		Room.Door entrance = room.entrance();
		entrance.set(Room.Door.Type.HIDDEN);
		int door = entrance.x + entrance.y * Level.getWidth();
		
		Dungeon.ratChests=0;

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

		RatKing king = new RatKing();
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
			prize = Generator.random(Generator.Category.WEAPON);
			if (prize instanceof MissileWeapon) {
				prize.quantity(1);
			} else {
				prize.degrade(Random.Int(3));
			}
			break;
		case 1:
			prize = Generator.random(Generator.Category.ARMOR).degrade(
					Random.Int(3));
			break;
		default:
			prize = new Gold(Random.IntRange(1, 5));
			break;
		}

		if (!page && Statistics.enemiesSlain<21 && Dungeon.limitedDrops.journal.dropped()){
			level.drop(new Town(), pos);
			page=true;
		} else {
		level.drop(prize, pos).type = Heap.Type.CHEST;
		Dungeon.ratChests++;
		}
	}
}
