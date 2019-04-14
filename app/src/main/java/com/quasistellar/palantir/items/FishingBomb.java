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

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.mobs.AlbinoPiranha;
import com.quasistellar.palantir.effects.CellEmitter;
import com.quasistellar.palantir.effects.particles.SmokeParticle;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.sprites.ItemSprite;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class FishingBomb extends Item {

	{
		name = "dumpling bomb";
		image = ItemSpriteSheet.RICEBOMB;
		defaultAction = AC_LIGHTTHROW;
		stackable = true;
	}

	public Fuse fuse;
	
	public static final String TXT_NO_TELEPORT = "You missed a fish!";

	// FIXME using a static variable for this is kinda gross, should be a better
	// way
	private static boolean lightingFuse = false;

	private static final String AC_LIGHTTHROW = "Agitate & Throw";

	@Override
	public boolean isSimilar(Item item) {
		return item instanceof FishingBomb && this.fuse == ((FishingBomb) item).fuse;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_LIGHTTHROW);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_LIGHTTHROW)) {
			lightingFuse = true;
			action = AC_THROW;
		} else
			lightingFuse = false;

		super.execute(hero, action);
	}

	@Override
	protected void onThrow(int cell) {
		if (!Level.pit[cell] && lightingFuse) {
			Actor.addDelayed(fuse = new Fuse().ignite(this), 2);
		}
		if (Actor.findChar(cell) != null
				&& !(Actor.findChar(cell) instanceof Hero)) {
			ArrayList<Integer> candidates = new ArrayList<>();
			for (int i : Level.NEIGHBOURS8)
				if (Level.passable[cell + i])
					candidates.add(cell + i);
			int newCell = candidates.isEmpty() ? cell : Random
					.element(candidates);
			Dungeon.level.drop(this, newCell).sprite.drop(cell);
		} else
			super.onThrow(cell);
	}

	@Override
	public boolean doPickUp(Hero hero) {
		if (fuse != null) {
			GLog.w("You stop the fishing bomb.");
			fuse = null;
		}
		return super.doPickUp(hero);
	}

	public void explode(int cell) {
		// We're blowing up, so no need for a fuse anymore.
		this.fuse = null;
	    Sample.INSTANCE.play(Assets.SND_BLAST, 2);

		//if (Dungeon.visible[cell]) {
		//	CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
		//}

	     	for (int n: Level.NEIGHBOURS9DIST2) {
			int c = cell + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}

				Heap heap = Dungeon.level.heaps.get(c);
				if (heap != null)
					heap.dumpexplode();
				
				Char ch = Actor.findChar(c);
				if (ch instanceof AlbinoPiranha) {

						int count = 20;
						int pos;
						do {
							pos = Dungeon.level.randomRespawnCellFish();
							if (count-- <= 0) {
								break;
							}
						} while (pos == -1);

						if (pos == -1) {

							GLog.w(TXT_NO_TELEPORT);

						} else {

							ch.pos = pos;
							ch.sprite.place(ch.pos);
							ch.sprite.visible = Dungeon.visible[pos];
							GLog.i(curUser.name + " teleported " + ch.name + " to dry land");

						}

					}
				}
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
	public ItemSprite.Glowing glowing() {
		return fuse != null ? new ItemSprite.Glowing(0xFF0000, 0.6f) : null;
	}

	@Override
	public int price() {
		return 20 * quantity;
	}

	@Override
	public String info() {
		return "A weighty bundle of dumplings smashed together used for catching fish."
				+ (fuse != null ? "\n\nThe dumpling bomb looks agitated. It could explode at any moment!"
						: "\n\nShaking the bundle could make it unstable and release it's goodness.");
	}

	private static final String FUSE = "fuse";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(FUSE, fuse);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		if (bundle.contains(FUSE))
			Actor.add(fuse = ((Fuse) bundle.get(FUSE)).ignite(this));
	}

	public static class Fuse extends Actor {

		private FishingBomb bomb;

		public Fuse ignite(FishingBomb bomb) {
			this.bomb = bomb;
			return this;
		}

		@Override
		protected boolean act() {

			// something caused our bomb to explode early, or be defused. Do
			// nothing.
			if (bomb.fuse != this) {
				Actor.remove(this);
				return true;
			}

			// look for our bomb, remove it from its heap, and blow it up.
			for (Heap heap : Dungeon.level.heaps.values()) {
				if (heap.items.contains(bomb)) {
					heap.items.remove(bomb);

					bomb.explode(heap.pos);

					Actor.remove(this);
					return true;
				}
			}

			// can't find our bomb, this should never happen, throw an
			// exception.
			throw new RuntimeException(
					"Something caused an agitated dumpling to not be present in a heap on the level!");
		}
	}
	
	public FishingBomb() {
		this(1);
	}

	public FishingBomb(int value) {
		this.quantity = value;
	}
}
