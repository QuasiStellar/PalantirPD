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
package com.quasistellar.otiluke.actors.mobs;

import java.util.HashSet;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.actors.buffs.Poison;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.Speck;
import com.quasistellar.otiluke.items.AdamantArmor;
import com.quasistellar.otiluke.items.Gold;
import com.quasistellar.otiluke.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.otiluke.items.weapon.enchantments.Death;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.mechanics.Ballistica;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.CrabKingSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class CrabKing extends Mob {

	private static final int JUMP_DELAY = 5;

	{
		name = "Crab King";
		spriteClass = CrabKingSprite.class;
		baseSpeed = 2f;

		HP = HT = 300;
		EXP = 20;
		defenseSkill = 30;
	}

	private int timeToJump = JUMP_DELAY;
	
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 50);
	}

	@Override
	public int attackSkill(Char target) {
		return 35;
	}

	@Override
	public int dr() {
		return 10;
	}

	@Override
	protected boolean act() {
		boolean result = super.act();

		int regen=Math.round(Dungeon.shellCharge/10);
		
		if (HP < HT && Dungeon.shellCharge>10) {
			sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
			HP = HP + regen;
			Dungeon.shellCharge-=regen;
			GLog.n("Crab King draws power from the lightning shell!");
		}
		return result;
	}
	
	@Override
	public void die(Object cause) {

		
		
		GameScene.bossSlain();
		
		Dungeon.level.drop(new Gold(Random.Int(1900, 4000)), pos).sprite.drop();
		Dungeon.level.drop(new AdamantArmor(), pos).sprite.drop();
		Dungeon.crabkingkilled=true;
	
		
		super.die(cause);
		
		yell("Ughhhh...");
					
	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target]) {
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
		if (timeToJump <= 0 && Level.adjacent(pos, enemy.pos)) {
			jump();
			return true;
		} else {
			return super.doAttack(enemy);
		}
	}

	private void jump() {
		timeToJump = JUMP_DELAY;
		
		int newPos;
		do {
			newPos = Random.Int(Level.getLength());
		} while (!Level.fieldOfView[newPos] || !Level.passable[newPos]
				|| Level.adjacent(newPos, enemy.pos)
				|| Actor.findChar(newPos) != null);

		sprite.move(pos, newPos);
		move(newPos);

		if (Dungeon.visible[newPos]) {
			CellEmitter.get(newPos).burst(Speck.factory(Speck.WOOL), 6);
			Sample.INSTANCE.play(Assets.SND_PUFF);
		}

		spend(1 / speed());
	}

	@Override
	public void notice() {
		super.notice();
		yell("Ah! I want that " + Dungeon.hero.givenName() + "'s armor!");
	}

	@Override
	public String description() {
		return "The crab king collects protective magical items to make himself stronger. "
				+ "He is hiding himself in a powerful suit of armor.";
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
