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
package com.quasistellar.palantir.actors.blobs;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Burning;
import com.quasistellar.palantir.effects.BlobEmitter;
import com.quasistellar.palantir.effects.particles.FlameParticle;
import com.quasistellar.palantir.items.Heap;
import com.quasistellar.palantir.items.journalpages.DragonCave;
import com.quasistellar.palantir.items.journalpages.Vault;
import com.quasistellar.palantir.items.misc.Spectacles.MagicSight;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.levels.Terrain;
import com.quasistellar.palantir.scenes.GameScene;
import com.watabou.utils.Random;

public class Fire extends Blob {

	@Override
	protected void evolve() {

		boolean[] flamable = Level.flamable;

		int from = WIDTH + 1;
		int to = Level.getLength() - WIDTH - 1;

		boolean observe = false;

		for (int pos = from; pos < to; pos++) {

			int fire;
			boolean shelf = false;

			if (cur[pos] > 0) {

				burn(pos);

				fire = cur[pos] - 1;
				if (fire <= 0 && flamable[pos]) {
					
					if(Dungeon.level.map[pos]==Terrain.BOOKSHELF){
						shelf=true;
					}

					int oldTile = Dungeon.level.map[pos];					
					Level.set(pos, Terrain.EMBERS);
					
					
					if (shelf && Random.Float()<.02 && Dungeon.hero.buff(MagicSight.class) != null){
						
						if (!Dungeon.limitedDrops.vaultpage.dropped()) {
							Dungeon.level.drop(new Vault(), pos);
							Dungeon.limitedDrops.vaultpage.drop();	
						}
					}
					
                    if (shelf && Random.Float()<.02 && Dungeon.hero.buff(MagicSight.class) != null){
						
						if (!Dungeon.limitedDrops.dragoncave.dropped()) {
							Dungeon.level.drop(new DragonCave(), pos);
							Dungeon.limitedDrops.dragoncave.drop();	
						}
					}
						
                    /*
					if (shelf && Random.Float()<.02 && Dungeon.hero.buff(MagicSight.class) != null){
							
							if (Dungeon.limitedDrops.vaultpage.dropped()) {
								Dungeon.level.drop(new RoyalSpork(), pos);
							}
					
					}
					*/
					
					

					observe = true;
					GameScene.updateMap(pos);
					if (Dungeon.visible[pos]) {
						GameScene.discoverTile(pos, oldTile);
					}
				}

			} else {

				if (flamable[pos]
						&& (cur[pos - 1] > 0 || cur[pos + 1] > 0
								|| cur[pos - WIDTH] > 0 || cur[pos + WIDTH] > 0)) {
					fire = 4;
					burn(pos);
				} else {
					fire = 0;
				}

			}

			volume += (off[pos] = fire);

		}

		if (observe) {
			Dungeon.observe();
		}
	}

	private void burn(int pos) {
		Char ch = Actor.findChar(pos);
		if (ch != null) {
			Buff.affect(ch, Burning.class).reignite(ch);
		}

		Heap heap = Dungeon.level.heaps.get(pos);
		if (heap != null) {
			heap.burn();
		}
	}

	@Override
	public void seed(int cell, int amount) {
		if (cur[cell] == 0) {
			volume += amount;
			cur[cell] = amount;
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);
		emitter.start(FlameParticle.FACTORY, 0.03f, 0);
	}
	@Override
	public String tileDesc() {
		return "A fire is raging here.";
	}
}
