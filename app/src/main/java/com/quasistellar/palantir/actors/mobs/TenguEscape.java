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
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.ToxicGas;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Poison;
import com.quasistellar.palantir.effects.CellEmitter;
import com.quasistellar.palantir.effects.Speck;
import com.quasistellar.palantir.effects.particles.ElmoParticle;
import com.quasistellar.palantir.items.TenguKey;
import com.quasistellar.palantir.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.palantir.items.weapon.enchantments.Death;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.mechanics.Ballistica;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.TenguSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class TenguEscape extends Mob {

	private static final int JUMP_DELAY = 5;
	private static final int JUMPS_TO_ESCAPE = 5;
	protected static final float SPAWN_DELAY = 2f;
	
	{
		name = "Tengu";
		spriteClass = TenguSprite.class;
		baseSpeed = 1f;

		HP = HT = 100;
		EXP = 0;
		defenseSkill = 30;
	}

	private int timeToJump = JUMP_DELAY;
	private int escapeCount = JUMPS_TO_ESCAPE;
	private int jumps=0;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 18);
	}

	@Override
	public int attackSkill(Char target) {
		return 20;
	}

	@Override
	public int dr() {
		return 15;
	}

	
	
	
	@Override
	public void die(Object cause) {

		//super.die(cause);

		if (jumps>=JUMPS_TO_ESCAPE){
	    	yell("Escape!");
	    	     } else {
		    yell("Ugh...Too late " + Dungeon.hero.givenName() + ". Escape!");
		    if(!Dungeon.limitedDrops.tengukey.dropped()) {
			Dungeon.limitedDrops.tengukey.drop();
			Dungeon.level.drop(new TenguKey(), pos).sprite.drop();
		}
	    	     }
		destroy();
		sprite.killAndErase();
		CellEmitter.get(pos).burst(ElmoParticle.FACTORY, 6);	
					
	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target] && jumps<JUMPS_TO_ESCAPE) {
			jump();
			return true;
		} else {
			return super.getCloser(target);
		}
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {
		timeToJump--;
		if (timeToJump <= 0 && jumps<JUMPS_TO_ESCAPE) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	private void jump() {
		timeToJump = JUMP_DELAY;
		escapeCount = JUMPS_TO_ESCAPE;
		
		
		//GLog.i("%s! ",(JUMPS_TO_ESCAPE-jumps));

		int newPos;
		do {
			newPos = Random.Int(Level.getLength());
		} while (!Level.passable[newPos]
				|| Level.adjacent(newPos, Dungeon.hero.pos)
				|| Actor.findChar(newPos) != null);

		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}

		spend(1 / speed());
		
		jumps++;
		
		if (jumps>=JUMPS_TO_ESCAPE){
			HP=1;
			Buff.affect(this,Poison.class).set(Poison.durationFactor(this) * 2);
		}
		
	}

	public static TenguEscape spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			TenguEscape w = new TenguEscape();
			w.pos = pos;
			w.state = w.HUNTING;
			GameScene.add(w, SPAWN_DELAY);

			//w.sprite.alpha(0);
			//w.sprite.parent.add(new AlphaTweener(w.sprite, 1, 0.5f));

			return w;
  			
		} else {
			return null;
		}
	}
	
	@Override
	public void notice() {
		super.notice();
		yell("Finally free!");
	}

	@Override
	public String description() {
		return "Tengu are members of the ancient assassins clan, which is also called Tengu. "
				+ "These assassins are noted for extensive use of shuriken and traps.";
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(ToxicGas.class);
		RESISTANCES.add(Poison.class);
		RESISTANCES.add(Death.class);
		RESISTANCES.add(ScrollOfPsionicBlast.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
