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

import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.ToxicGas;
import com.quasistellar.palantir.actors.buffs.Poison;
import com.quasistellar.palantir.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.palantir.items.weapon.enchantments.Death;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.mechanics.Ballistica;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.AssassinSprite;
import com.watabou.utils.Random;

public class Assassin extends Mob {
	
	protected static final float SPAWN_DELAY = 2f;
	
	{
		name = "Shinobi";
		spriteClass = AssassinSprite.class;
		baseSpeed = 2f;

		HP = HT = 25+(5*Random.NormalIntRange(2, 5));
		EXP = 10;
		defenseSkill = 15;
	}

	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10, 23);
	}

	@Override
	public int attackSkill(Char target) {
		return 25;
	}

	@Override
	public int dr() {
		return 5;
	}
	@Override
	protected float attackDelay() {
		return 0.75f;
	}

	
	@Override
	protected boolean canAttack(Char enemy) {
		return Ballistica.cast(pos, enemy.pos, false, true) == enemy.pos;
	}
	
	public static Assassin spawnAt(int pos) {
		if (Level.passable[pos] && Actor.findChar(pos) == null) {
          
			Assassin w = new Assassin();
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
	public String description() {
		return "Shinobi are members of the Tengu assassin clan. "
				+ "They fight with ranged attacks at high speed. ";
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
