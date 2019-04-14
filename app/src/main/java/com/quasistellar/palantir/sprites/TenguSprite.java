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
package com.quasistellar.palantir.sprites;

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Burning;
import com.quasistellar.palantir.actors.buffs.Paralysis;
import com.quasistellar.palantir.actors.buffs.Slow;
import com.quasistellar.palantir.effects.particles.FlameParticle;
import com.quasistellar.palantir.items.weapon.missiles.Shuriken;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.scenes.GameScene;
import com.watabou.noosa.TextureFilm;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class TenguSprite extends MobSprite {

	private static final float DURATION = 2f;
	private Animation cast;

	public TenguSprite() {
		super();

		texture(Assets.TENGU);

		TextureFilm frames = new TextureFilm(texture, 14, 16);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 0, 0, 1);

		run = new Animation(15, false);
		run.frames(frames, 2, 3, 4, 5, 0);

		attack = new Animation(15, false);
		attack.frames(frames, 6, 7, 7, 0);

		cast = attack.clone();

		die = new Animation(8, false);
		die.frames(frames, 8, 9, 10, 10, 10, 10, 10, 10);

		play(run.clone());
	}

	@Override
	public void move(int from, int to) {

		place(to);

		play(run);
		turnTo(from, to);

		isMoving = true;

		if (Level.water[to]) {
			GameScene.ripple(to);
		}

		ch.onMotionComplete();
	}

	@Override
	public void attack(int cell) {
		if (!Level.adjacent(cell, ch.pos)) {
			Char enemy = Actor.findChar(cell);
				  ((MissileSprite) parent.recycle(MissileSprite.class)).reset(ch.pos,
					cell, new Shuriken(), new Callback() {
						@Override
						public void call() {
							ch.onAttackComplete();
						}
				});
		 	  
		  		if(Random.Int(5)==0){
		  				Buff.affect(enemy, Burning.class).reignite(enemy);
		  				enemy.sprite.emitter().burst(FlameParticle.FACTORY, 5);
		  			}
		  		if(Random.Int(10)==0){
		  			Buff.affect(enemy, Slow.class, Slow.duration(enemy) / 2);
		  		}
		  				  		
		  		if(Random.Int(20)==0){
		  		       Buff.prolong(enemy, Paralysis.class, DURATION);
		  		}
		  		
			play(cast);
			turnTo(ch.pos, cell);

		} else {

			super.attack(cell);

		}
	}

	@Override
	public void onComplete(Animation anim) {
		if (anim == run) {
			isMoving = false;
			idle();
		} else {
			super.onComplete(anim);
		}
	}
}
