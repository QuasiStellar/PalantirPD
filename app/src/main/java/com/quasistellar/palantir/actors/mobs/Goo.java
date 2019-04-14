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
package com.quasistellar.palantir.actors.mobs;

import java.util.HashSet;

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.Blob;
import com.quasistellar.palantir.actors.blobs.GooWarn;
import com.quasistellar.palantir.actors.blobs.ToxicGas;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Ooze;
import com.quasistellar.palantir.actors.buffs.Roots;
import com.quasistellar.palantir.effects.CellEmitter;
import com.quasistellar.palantir.effects.Speck;
import com.quasistellar.palantir.effects.particles.ElmoParticle;
import com.quasistellar.palantir.items.ActiveMrDestructo;
import com.quasistellar.palantir.items.Egg;
import com.quasistellar.palantir.items.journalpages.Sokoban1;
import com.quasistellar.palantir.items.keys.SkeletonKey;
import com.quasistellar.palantir.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.palantir.items.weapon.enchantments.Death;
import com.quasistellar.palantir.items.weapon.melee.Chainsaw;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.levels.SewerBossLevel;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.CharSprite;
import com.quasistellar.palantir.sprites.GooSprite;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Goo extends Mob {
	{
		name = "Goo";
		HP = HT = 200; //200
		EXP = 10;
		defenseSkill = 12;
		spriteClass = GooSprite.class;

		loot = new ActiveMrDestructo();
		lootChance = 0.5f;
		
		lootOther = Dungeon.getMonth() == 9 ? new Egg() : new Chainsaw().enchantBuzz();
		lootChanceOther = 0.05f;
		
		lootThird = Dungeon.getMonth() != 9 ? new Chainsaw().enchantBuzz() : new Egg();
		lootChanceThird = 1f;
	}

	private int pumpedUp = 0;
	private int goosAlive = 0;

	@Override
	public int damageRoll() {
		if (pumpedUp > 0) {
			pumpedUp = 0;
			for (int i = 0; i < Level.NEIGHBOURS9DIST2.length; i++) {
				int j = pos + Level.NEIGHBOURS9DIST2[i];
				if (j >= 0 && j <= 1023 && Level.passable[j])
					CellEmitter.get(j).burst(ElmoParticle.FACTORY, 10);
			}
			Sample.INSTANCE.play(Assets.SND_BURNING);
			return Random.NormalIntRange(5, 30);
		} else {
			return Random.NormalIntRange(2, 12);
		}
	}

	@Override
	public int attackSkill(Char target) {
		return (pumpedUp > 0) ? 30 : 15;
	}

	@Override
	public int dr() {
		return 2;
	}

	@Override
	public boolean act() {

		if (Level.water[pos] && HP < HT) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP++;
		}

		return super.act();
	}

	
	@Override
	protected boolean canAttack(Char enemy) {
		return (pumpedUp > 0) ? distance(enemy) <= 2 : super.canAttack(enemy);
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Ooze.class);
			enemy.sprite.burst(0x000000, 5);
		}

		if (pumpedUp > 0) {
			Camera.main.shake(3, 0.2f);
		}
				
		return damage;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		if (pumpedUp == 1) {
			((GooSprite) sprite).pumpUp();
			for (int i = 0; i < Level.NEIGHBOURS9DIST2.length; i++) {
				int j = pos + Level.NEIGHBOURS9DIST2[i];
				if (j >= 0 && j <= 1023 && Level.passable[j])
					GameScene.add(Blob.seed(j, 2, GooWarn.class));
			}
			pumpedUp++;

			spend(attackDelay());

			return true;
		} else if (pumpedUp >= 2 || Random.Int(3) > 0) {

			boolean visible = Dungeon.visible[pos];

			if (visible) {
				if (pumpedUp >= 2) {
					((GooSprite) sprite).pumpAttack();
				} else
					sprite.attack(enemy.pos);
			} else {
				attack(enemy);
			}

			spend(attackDelay());

			return !visible;

		} else {

			pumpedUp++;

			((GooSprite) sprite).pumpUp();

			for (int i = 0; i < Level.NEIGHBOURS9.length; i++) {
				int j = pos + Level.NEIGHBOURS9[i];
				GameScene.add(Blob.seed(j, 2, GooWarn.class));

			}

			if (Dungeon.visible[pos]) {
				sprite.showStatus(CharSprite.NEGATIVE, "!!!");
				GLog.n("Goo is pumping itself up!");
			}

			spend(attackDelay());

			return true;
		}
	}

	@Override
	public boolean attack(Char enemy) {
		boolean result = super.attack(enemy);
		pumpedUp = 0;
		return result;
	}

	@Override
	protected boolean getCloser(int target) {
		pumpedUp = 0;
		return super.getCloser(target);
	}

	@Override
	public void die(Object cause) {

		super.die(cause);

		for (Mob mob : Dungeon.level.mobs) {
			
			if (mob instanceof Goo || mob instanceof PoisonGoo){
				   goosAlive++;
				 }
			
			}
			
			 if(goosAlive==0){
			
			((SewerBossLevel) Dungeon.level).unseal();

			GameScene.bossSlain();
			Dungeon.level.drop(new SkeletonKey(Dungeon.depth), pos).sprite.drop();	
		}
	
		Dungeon.level.drop(new Sokoban1(), pos).sprite.drop();
		

		yell("glurp... glurp...");
	}
  
	protected boolean spawnedMini = false;
	
	@Override
	public void notice() {
		super.notice();
		yell("GLURP-GLURP!");
		if (!spawnedMini){
	    PoisonGoo.spawnAround(pos);
	    spawnedMini = true;
		}
	  }

	@Override
	public String description() {
		return "Little is known about The Goo. It's quite possible that it is not even a creature, but rather a "
				+ "conglomerate of vile substances from the sewers that somehow gained basic intelligence. "
				+ "Regardless, dark magic is certainly what has allowed Goo to exist.\n\n"
				+ "Its gelatinous nature has let it absorb lots of dark energy, you feel a chill just from being near. "
				+ "If goo is able to attack with this energy you won't live for long.";
	}

	private final String PUMPEDUP = "pumpedup";

	@Override
	public void storeInBundle(Bundle bundle) {

		super.storeInBundle(bundle);

		bundle.put(PUMPEDUP, pumpedUp);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {

		super.restoreFromBundle(bundle);

		pumpedUp = bundle.getInt(PUMPEDUP);
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();

	static {
		IMMUNITIES.add(Roots.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	}
