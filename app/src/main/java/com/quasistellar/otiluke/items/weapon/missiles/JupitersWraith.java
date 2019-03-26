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
package com.quasistellar.otiluke.items.weapon.missiles;

import java.util.ArrayList;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Paralysis;
import com.quasistellar.otiluke.actors.buffs.Slow;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.actors.mobs.Gullin;
import com.quasistellar.otiluke.actors.mobs.Kupua;
import com.quasistellar.otiluke.actors.mobs.MineSentinel;
import com.quasistellar.otiluke.actors.mobs.Otiluke;
import com.quasistellar.otiluke.actors.mobs.RedWraith;
import com.quasistellar.otiluke.actors.mobs.Zot;
import com.quasistellar.otiluke.actors.mobs.ZotPhase;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.weapon.melee.relic.NeptunusTrident.Flooding;
import com.quasistellar.otiluke.items.weapon.melee.relic.RelicMeleeWeapon.WeaponBuff;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.ItemSpriteSheet;
import com.quasistellar.otiluke.sprites.MissileSprite;
import com.quasistellar.otiluke.ui.BuffIndicator;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class JupitersWraith extends MissileWeapon {
	
	protected Buff passiveBuff;
	protected Buff activeBuff;

	// level is used internally to track upgrades to artifacts, size/logic
	// varies per artifact.
	// already inherited from item superclass
	// exp is used to count progress towards levels for some artifacts
	protected int exp = 0;
	// levelCap is the artifact's maximum level
	protected int levelCap = 0;

	// the current artifact charge
	public int charge = 0;
	
	// the maximum charge, varies per artifact, not all artifacts use this.
	public int chargeCap = 1000;

	// used by some artifacts to keep track of duration of effects or cooldowns
	// to use.
	protected int cooldown = 0;


	{
		name = "jupiter's wraith";
		image = ItemSpriteSheet.JUPITERSWRAITH;

		STR = 10;

		MIN = 4;
		MAX = 8;

		stackable = false;
		unique = true;
		
		reinforced=true;

		bones = false;
	}
	
	
	@Override
	public boolean doEquip(Hero hero) {
		activate(hero);
		return super.doEquip(hero);
	}

	@Override
	public void activate(Hero hero) {
		// GLog.i("W2");
		passiveBuff = passiveBuff();
		// GLog.i("W3");
		passiveBuff.attachTo(hero);
	}

	@Override
	public boolean doUnequip(Hero hero, boolean collect, boolean single) {
		
		if (super.doUnequip(hero, collect, single)) {

			if (passiveBuff != null){			
			  passiveBuff.detach();
			  passiveBuff = null;
			}

			hero.belongings.weapon = null;
			return true;

		} else {

			return false;

		}
	}


	public class WeaponBuff extends Buff {

		public int level() {
			return level;
		}

		public boolean isCursed() {
			return cursed;
		}

	}


	@Override
	public boolean isUpgradable() {
		return true;
	}
	
	@Override
	public Item upgrade() {
		return upgrade(false);
	}

	@Override
	public Item upgrade(boolean enchant) {
		MIN += 2;
		MAX += 4;
		if (enchant){
			GLog.i("Your weapon screams, 'What are you trying to do to me!?' Relic weapons cannot be enchanted.");
		}

		super.upgrade(false);

		updateQuickslot();

		return this;
	}

	@Override
	public Item degrade() {
		MIN -= 1;
		MAX -= 2;
		return super.degrade();
	}

	@Override
	public void proc(Char attacker, Char defender, int damage) {
		
		if (defender instanceof Gullin 
        		|| defender instanceof Kupua
        		|| defender instanceof MineSentinel
        		|| defender instanceof Otiluke
        		|| defender instanceof Zot
        		|| defender instanceof ZotPhase){
        	
        	damage*=4;
		}
        
		
		super.proc(attacker, defender, damage);
		if (attacker instanceof Hero && ((Hero) attacker).rangedWeapon == this) {
			circleBack(defender.pos, (Hero) attacker);
		}
	}

	@Override
	protected void miss(int cell) {
		circleBack(cell, curUser);
	}

	private void circleBack(int from, Hero owner) {

		((MissileSprite) curUser.sprite.parent.recycle(MissileSprite.class))
				.reset(from, curUser.pos, curItem, null);

		if (throwEquiped) {
			owner.belongings.weapon = this;
			owner.spend(-TIME_TO_EQUIP);
			Dungeon.quickslot.replaceSimilar(this);
			updateQuickslot();
		} else if (!collect(curUser.belongings.backpack)) {
			Dungeon.level.drop(this, owner.pos).sprite.drop();
		}
	}

	private boolean throwEquiped;

	@Override
	public void cast(Hero user, int dst) {
		throwEquiped = isEquipped(user);
		super.cast(user, dst);
	}

	@Override
	public String desc() {
		String info = "This thrown weapon is the incarnation of Jupiter's wraith.";
		switch (imbue) {
		case LIGHT:
			info += "\n\nIt was balanced to be lighter. ";
			break;
		case HEAVY:
			info += "\n\nIt was balanced to be heavier. ";
			break;
		case NONE:
		}
		if(reinforced){
			info += "\n\nIt is reinforced. ";
		}
		if (charge>=chargeCap) {
			info  += "\n\nIt is fully charged.";
		} else {
			info  += "\n\nIt has " +charge+ " out of "+chargeCap+" charges.";
		}
		
		return info;
	}
	

	public static final String AC_EXPLODE = "EXPLODE";

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		if (isEquipped(hero) && charge >= chargeCap)
			actions.add(AC_EXPLODE);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		if (action.equals(AC_EXPLODE)) {
			int distance=distance();
			GLog.w("Unleash the wraith!");
			explode(distance, hero);		
		} else
			super.execute(hero, action);
	}
	

	private int distance(){
		return Math.round(level/3)+1;
	}
	
	private void explode(int distance, Hero hero) {
        charge = 0;
		
		int length = Level.getLength();
		int width = Level.getWidth();
		for (int i = width; i < length - width; i++){
			int	 dist = Level.distance(hero.pos, i);
			  if (dist<distance){
				  doExplode(i);			    
			     }
			   }
			  
	}
	
   public void doExplode(int cell) {
		
		Camera.main.shake(3, 0.7f);
		
				if (Dungeon.visible[cell] && Level.passable[cell]) {
					CellEmitter.center(cell).start(Speck.factory(Speck.ROCK), 0.07f, 10);
				}
				
				Char ch = Actor.findChar(cell);
				if (ch != null && ch!=Dungeon.hero) {
					// those not at the center of the blast take damage less
					// consistently.
					int minDamage = MIN*2;
					int maxDamage = MAX*4;
					                    
					
					int dmg = Random.NormalIntRange(minDamage, maxDamage) - Random.Int(ch.dr());
					
					
					if (dmg > 0) {
						ch.damage(dmg, this);
						if(Random.Int(3)==1 && ch.isAlive()){Buff.prolong(ch, Paralysis.class, 1);}
					}
											
     			}

	}	


	
	public class ExplodeCharge extends WeaponBuff {

		@Override
		public boolean act() {
			if (charge < chargeCap) {
				charge+=level;
				if (charge >= chargeCap) {
					GLog.w("Jupiter's Wraith is filled with energy!");
				}
				updateQuickslot();
			}
			spend(TICK);
			return true;
		}


		@Override
		public String toString() {
			return "Explode";
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
	
	protected WeaponBuff passiveBuff() {
		return new ExplodeCharge();
	}

}
