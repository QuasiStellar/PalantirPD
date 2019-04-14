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
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.blobs.ToxicGas;
import com.quasistellar.palantir.actors.buffs.Terror;
import com.quasistellar.palantir.effects.CellEmitter;
import com.quasistellar.palantir.effects.particles.PurpleParticle;
import com.quasistellar.palantir.items.InactiveMrDestructo2;
import com.quasistellar.palantir.items.weapon.enchantments.Death;
import com.quasistellar.palantir.items.weapon.enchantments.Leech;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.mechanics.Ballistica;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.CharSprite;
import com.quasistellar.palantir.sprites.MrDestructo2dot0Sprite;
import com.quasistellar.palantir.utils.GLog;
import com.quasistellar.palantir.utils.Utils;
import com.watabou.utils.Random;

public class MrDestructo2dot0 extends Mob {

	private static final String TXT_DEATHGAZE_KILLED = "%s's deathray killed you...";

	{
		name = "mr destructo 2.0";
		spriteClass = MrDestructo2dot0Sprite.class;
		hostile = false;
		state = HUNTING;
		HP = HT= 200;
		defenseSkill = 35;	
	}

	
	private static final float SPAWN_DELAY = 0.1f;
	
	@Override
	public int dr() {
		return 35;
	}

	
	@Override
	protected boolean act() {
		
		for (int n : Level.NEIGHBOURS8DIST2) {
			int c = pos + n;
			if (c<Level.getLength() && c>0){
			Char ch = Actor.findChar(c);
				if (ch == Dungeon.hero && Dungeon.hero.isAlive() &&  enemy==null) {
					yell("Scanning...");
				}
			}
		}
		//Level.fieldOfView[Dungeon.hero.pos] &&
		
		boolean result = super.act();
		return result;
	}
	
	@Override
	public void move(int step) {		
	}
		
	@Override
	protected Char chooseEnemy() {

		if (enemy == null || !enemy.isAlive()) {
			HashSet<Mob> enemies = new HashSet<Mob>();
			for (Mob mob : Dungeon.level.mobs) {
				if (mob.hostile && Level.fieldOfView[mob.pos]) {
					enemies.add(mob);
				}
			}

			enemy = enemies.size() > 0 ? Random.element(enemies) : null;
		}

		return enemy;
	}

	
	
    public static MrDestructo2dot0 spawnAt(int pos) {
		
    	MrDestructo2dot0 b = new MrDestructo2dot0();  
    	
			b.pos = pos;
			b.state = b.HUNTING;
			GameScene.add(b, SPAWN_DELAY);

			return b;
     
     }
	
	private int hitCell;

	@Override
	protected boolean canAttack(Char enemy) {

		hitCell = Ballistica.cast(pos, enemy.pos, true, false);

		for (int i = 1; i < Ballistica.distance; i++) {
			if (Ballistica.trace[i] == enemy.pos) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int attackSkill(Char target) {
		return 30+(Dungeon.depth);
	}

	@Override
	protected float attackDelay() {
		return 0.5f;
	}
	
	@Override
	protected boolean doAttack(Char enemy) {

		spend(attackDelay());

		boolean rayVisible = false;

		for (int i = 0; i < Ballistica.distance; i++) {
			if (Dungeon.visible[Ballistica.trace[i]]) {
				rayVisible = true;
			}
		}

		if (rayVisible) {
			sprite.attack(hitCell);
			return false;
		} else {
			attack(enemy);
			return true;
		}
	}

	@Override
	public boolean attack(Char enemy) {

		for (int i = 1; i < Ballistica.distance; i++) {

			int pos = Ballistica.trace[i];

			Char ch = Actor.findChar(pos);
			if (ch == null) {
				continue;
			}

			if (hit(this, ch, true)) {
				ch.damage(Random.NormalIntRange(Dungeon.depth+20, Dungeon.depth+32), this);
				yell("MR DESTRUCTO TWO DOT OH!");
				damage(Random.NormalIntRange(5, 10), this);

				if (Dungeon.visible[pos]) {
					ch.sprite.flash();
					CellEmitter.center(pos).burst(PurpleParticle.BURST,
							Random.IntRange(1, 2));
				}

				if (!ch.isAlive() && ch == Dungeon.hero) {
					Dungeon.fail(Utils.format(ResultDescriptions.MOB,
							Utils.indefinite(name)));
					GLog.n(TXT_DEATHGAZE_KILLED, name);
				}
			} else {
				ch.sprite.showStatus(CharSprite.NEUTRAL, ch.defenseVerb());
			}
		}

		return true;
	}

	@Override
	public void beckon(int cell) {
	}
	
	@Override
	public String description() {
		return "The contraption has sprung to life! "
				+ "It is blowing away nearby mobs!";
	}

	
	@Override
	public void die(Object cause) {

		yell("Shutting down...");
		Dungeon.level.drop(new InactiveMrDestructo2(), pos);
		super.die(cause);

		
	
	}
			
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(Death.class);
		RESISTANCES.add(Leech.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Terror.class);
		IMMUNITIES.add(ToxicGas.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
