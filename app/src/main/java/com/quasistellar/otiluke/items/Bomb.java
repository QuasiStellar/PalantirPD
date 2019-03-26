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
package com.quasistellar.otiluke.items;

import java.util.ArrayList;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.hero.HeroClass;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.particles.BlastParticle;
import com.quasistellar.otiluke.effects.particles.SmokeParticle;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.CharSprite;
import com.quasistellar.otiluke.sprites.ItemSprite;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Bomb extends Item {

	{
		name = "bomb";
		image = ItemSpriteSheet.BOMB;
		defaultAction = AC_LIGHTTHROW;
		stackable = true;
	}

	public Fuse fuse;

	// FIXME using a static variable for this is kinda gross, should be a better
	// way
	private static boolean lightingFuse = false;

	private static final String AC_LIGHTTHROW = "Light & Throw";
	
	public static final String AC_DIZZYBOMB = "Make Dizzy Bomb";
	public static final String AC_SMARTBOMB = "Make Smart Bomb";
	public static final String AC_SEEKINGBOMB = "Make Seeking Bomb";
	public static final String AC_CLUSTERBOMB = "Make Cluster Bomb";
	public static final String AC_SEEKINGCLUSTERBOMB = "Make Seeking Cluster Bomb";
	
	public static final float TIME_TO_COOK_BOMB = 4;

	@Override
	public boolean isSimilar(Item item) {
		return item instanceof Bomb && this.fuse == ((Bomb) item).fuse;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_LIGHTTHROW);
		if (Dungeon.hero.heroClass==HeroClass.ROGUE && Dungeon.hero.lvl>4){
		  actions.add(AC_DIZZYBOMB);
		} 
         if (Dungeon.hero.heroClass==HeroClass.ROGUE && Dungeon.hero.lvl>9){
		  actions.add(AC_SMARTBOMB);
		}
         if (Dungeon.hero.heroClass==HeroClass.ROGUE && Dungeon.hero.lvl>14){
		  actions.add(AC_SEEKINGBOMB);
		}
         if (Dungeon.hero.heroClass==HeroClass.ROGUE && Dungeon.hero.lvl>19){
		  actions.add(AC_CLUSTERBOMB);
		}
          if (Dungeon.hero.heroClass==HeroClass.ROGUE && Dungeon.hero.lvl>29){
		  actions.add(AC_SEEKINGCLUSTERBOMB);
		}
		
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_LIGHTTHROW)) {
			lightingFuse = true;
			action = AC_THROW;
		} else {
			lightingFuse = false;
		}
		
		if (action.equals(AC_DIZZYBOMB)) {

			hero.spend(TIME_TO_COOK_BOMB);
			hero.busy();

			hero.sprite.operate(hero.pos);
			
			DizzyBomb dbomb = new DizzyBomb();
			if (dbomb.doPickUp(Dungeon.hero)) {
				GLog.i(Hero.TXT_YOU_NOW_HAVE, dbomb.name());
				} else {
				Dungeon.level.drop(dbomb, Dungeon.hero.pos).sprite.drop();	
				}
			  detach(Dungeon.hero.belongings.backpack);
		   }
		
		if (action.equals(AC_SMARTBOMB)) {

			hero.spend(TIME_TO_COOK_BOMB);
			hero.busy();

			hero.sprite.operate(hero.pos);
			
			SmartBomb smbomb = new SmartBomb();
			if (smbomb.doPickUp(Dungeon.hero)) {
				GLog.i(Hero.TXT_YOU_NOW_HAVE, smbomb.name());
				} else {
				Dungeon.level.drop(smbomb, Dungeon.hero.pos).sprite.drop();	
				}
			  detach(Dungeon.hero.belongings.backpack);
		   }
		
		if (action.equals(AC_SEEKINGBOMB)) {

			hero.spend(TIME_TO_COOK_BOMB);
			hero.busy();

			hero.sprite.operate(hero.pos);
			
			SeekingBombItem sbomb = new SeekingBombItem();
			if (sbomb.doPickUp(Dungeon.hero)) {
				GLog.i(Hero.TXT_YOU_NOW_HAVE, sbomb.name());
				} else {
				Dungeon.level.drop(sbomb, Dungeon.hero.pos).sprite.drop();	
				}
			  detach(Dungeon.hero.belongings.backpack);
		   }
		
		if (action.equals(AC_CLUSTERBOMB)) {

			hero.spend(TIME_TO_COOK_BOMB);
			hero.busy();

			hero.sprite.operate(hero.pos);
			
			ClusterBomb cbomb = new ClusterBomb();
			if (cbomb.doPickUp(Dungeon.hero)) {
				GLog.i(Hero.TXT_YOU_NOW_HAVE, cbomb.name());
				} else {
				Dungeon.level.drop(cbomb, Dungeon.hero.pos).sprite.drop();	
				}
			  detach(Dungeon.hero.belongings.backpack);
		   }
	
	if (action.equals(AC_SEEKINGCLUSTERBOMB)) {

		hero.spend(TIME_TO_COOK_BOMB);
		hero.busy();

		hero.sprite.operate(hero.pos);
		
		SeekingClusterBombItem scbomb = new SeekingClusterBombItem();
		if (scbomb.doPickUp(Dungeon.hero)) {
			GLog.i(Hero.TXT_YOU_NOW_HAVE, scbomb.name());
			} else {
			Dungeon.level.drop(scbomb, Dungeon.hero.pos).sprite.drop();	
			}
		  detach(Dungeon.hero.belongings.backpack);
	   }

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
			GLog.w("You quickly snuff the bomb's fuse.");
			fuse = null;
		}
		return super.doPickUp(hero);
	}

	public void explode(int cell) {
		// We're blowing up, so no need for a fuse anymore.
		this.fuse = null;

		Sample.INSTANCE.play(Assets.SND_BLAST, 2);

		if (Dungeon.visible[cell]) {
			CellEmitter.center(cell).burst(BlastParticle.FACTORY, 30);
		}

		boolean terrainAffected = false;
		for (int n : Level.NEIGHBOURS9) {
			int c = cell + n;
			if (c >= 0 && c < Level.getLength()) {
				if (Dungeon.visible[c]) {
					CellEmitter.get(c).burst(SmokeParticle.FACTORY, 4);
				}

				if (Level.flamable[c]) {
					Level.set(c, Terrain.EMBERS);
					GameScene.updateMap(c);
					terrainAffected = true;
				}

				// destroys items / triggers bombs caught in the blast.
				Heap heap = Dungeon.level.heaps.get(c);
				if (heap != null)
					heap.explode();

				Char ch = Actor.findChar(c);
				if (ch != null) {
					// those not at the center of the blast take damage less
					// consistently.
					int minDamage = c == cell ? Dungeon.depth + 5 : 1;
					int maxDamage = 10 + Dungeon.depth * 2;

					int dmg = Random.NormalIntRange(minDamage, maxDamage)
							- Random.Int(ch.dr());
					if (dmg > 0) {
						ch.damage(dmg, this);
					}

					if (ch == Dungeon.hero && !ch.isAlive())
						// constant is used here in the rare instance a player
						// is killed by a double bomb.
						Dungeon.fail(Utils.format(ResultDescriptions.ITEM,
								"bomb"));
				}
			}
		}

		if (terrainAffected) {
			Dungeon.observe();
		}
		
	}
	
	public void genBomb(){
		if (Dungeon.hero.heroClass==HeroClass.ROGUE && Random.Int(1) == 0){
			Dungeon.level.drop(new Bomb(), Dungeon.level.randomDestination()).sprite.drop();
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
	public Item random() {
		switch (Random.Int(2)) {
		case 0:
		default:
			return this;
		case 1:
			return new DoubleBomb();
		}
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
		return "A fairly hefty black powder bomb. An explosion from this would certainly do damage to anything nearby."
				+ (fuse != null ? "\n\nThe bomb's fuse is burning away, keep your distance or put it out!"
						: "\n\nIt looks like the fuse will take a couple rounds to burn down once it is lit.");
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

		private Bomb bomb;

		public Fuse ignite(Bomb bomb) {
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
					bomb.genBomb();

					Actor.remove(this);
					return true;
				}
			}

			// can't find our bomb, this should never happen, throw an
			// exception.
			throw new RuntimeException(
					"Something caused a lit bomb to not be present in a heap on the level!");
		}
	}

	public static class DoubleBomb extends Bomb {

		{
			name = "two bombs";
			image = ItemSpriteSheet.DBL_BOMB;
			stackable = false;
		}

		@Override
		public String info() {
			return "A stack of two hefty black powder bombs, looks like you get one free!";
		}

		@Override
		public boolean doPickUp(Hero hero) {
			Bomb bomb = new Bomb();
			bomb.quantity(2);
			if (bomb.doPickUp(hero)) {
				// isaaaaac....
				hero.sprite.showStatus(CharSprite.NEUTRAL, "1+1 free!");
				return true;
			}
			return false;
		}
	}
}
