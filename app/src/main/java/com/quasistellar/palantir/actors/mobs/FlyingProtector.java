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

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.ResultDescriptions;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.effects.particles.SparkParticle;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.levels.traps.LightningTrap;
import com.quasistellar.palantir.mechanics.Ballistica;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.CharSprite;
import com.quasistellar.palantir.sprites.FlyingProtectorSprite;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class FlyingProtector extends Mob implements Callback {

	private static final float TIME_TO_ZAP = 2f;
	
	private static final float SPAWN_DELAY = 0.2f;
	

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";

	{
		name = "flying protector";
		spriteClass = FlyingProtectorSprite.class;

		EXP = 18;
		state = HUNTING;
		flying = true;
		
		HP = HT = 10000;
		defenseSkill = 4 + Dungeon.depth * 1;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 30);
	}

	@Override
	public int attackSkill(Char target) {
		return (int) ((9 + Dungeon.depth));
	}

	@Override
	public int dr() {
		return Dungeon.depth;
	}

	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}

	@Override
	protected boolean doAttack(Char enemy) {

		if (Level.distance(pos, enemy.pos) <= 1) {

			return super.doAttack(enemy);

		} else {

			boolean visible = Level.fieldOfView[pos]
					|| Level.fieldOfView[enemy.pos];
			if (visible) {
				((FlyingProtectorSprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(25, 40);
				if (Level.water[enemy.pos] && !enemy.flying) {
					dmg *= 1.5f;
				}
				enemy.damage(dmg, LightningTrap.LIGHTNING);

				enemy.sprite.centerEmitter().burst(SparkParticle.FACTORY, 3);
				enemy.sprite.flash();

				if (enemy == Dungeon.hero) {

					Camera.main.shake(2, 0.3f);

					if (!enemy.isAlive()) {
						Dungeon.fail(Utils.format(ResultDescriptions.MOB,
								Utils.indefinite(name)));
						GLog.n(TXT_LIGHTNING_KILLED, name);
					}
				}
			} else {
				enemy.sprite
						.showStatus(CharSprite.NEUTRAL, enemy.defenseVerb());
			}

			return !visible;
		}
	}
	
	 public static FlyingProtector spawnAt(int pos) {
			
		 FlyingProtector b = new FlyingProtector();  
	    	
				b.pos = pos;
				b.state = b.HUNTING;
				GameScene.add(b, SPAWN_DELAY);
				return b;	     
	     }

	@Override
	public void call() {
		next();
	}

	@Override
	public String description() {
		return "These sentinant statues guard this level from the unclean. That means you. ";
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(LightningTrap.Electricity.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}
}
