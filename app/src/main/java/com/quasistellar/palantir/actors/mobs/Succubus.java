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
import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Charm;
import com.quasistellar.palantir.actors.buffs.Light;
import com.quasistellar.palantir.actors.buffs.Sleep;
import com.quasistellar.palantir.effects.Speck;
import com.quasistellar.palantir.items.food.Meat;
import com.quasistellar.palantir.items.scrolls.ScrollOfLullaby;
import com.quasistellar.palantir.items.wands.WandOfBlink;
import com.quasistellar.palantir.items.weapon.enchantments.Leech;
import com.quasistellar.palantir.levels.Level;
import com.quasistellar.palantir.mechanics.Ballistica;
import com.quasistellar.palantir.sprites.SuccubusSprite;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Succubus extends Mob {

	private static final int BLINK_DELAY = 5;

	private int delay = 0;

	{
		name = "succubus";
		spriteClass = SuccubusSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(5, 7));
		defenseSkill = 25+adj(1);
		viewDistance = Light.DISTANCE;

		EXP = 12;
		maxLvl = 25;

		loot = new ScrollOfLullaby();
		lootChance = 0.05f;
		
		lootOther = new Meat();
		lootChanceOther = 0.1f; // by default, see die()
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(15, 25+adj(0));
	}

	@Override
	public int attackProc(Char enemy, int damage) {

		if (Random.Int(3) == 0) {
			Buff.affect(enemy, Charm.class, Charm.durationFactor(enemy)
					* Random.IntRange(3, 7)).object = id();
			enemy.sprite.centerEmitter().start(Speck.factory(Speck.HEART),
					0.2f, 5);
			Sample.INSTANCE.play(Assets.SND_CHARMS);
		}

		return damage;
	}

	@Override
	protected boolean getCloser(int target) {
		if (Level.fieldOfView[target] && Level.distance(pos, target) > 2
				&& delay <= 0) {

			blink(target);
			spend(-1 / speed());
			return true;

		} else {

			delay--;
			return super.getCloser(target);

		}
	}

	private void blink(int target) {

		int cell = Ballistica.cast(pos, target, true, true);
		
		if (Actor.findChar(cell) != null && Ballistica.distance > 1) {
			cell = Ballistica.trace[Ballistica.distance - 2];
		}
		
       if (!Level.pit[cell]){
		WandOfBlink.appear(this, cell);
       }

		delay = BLINK_DELAY;
	}

	@Override
	public int attackSkill(Char target) {
		return 40+adj(1);
	}

	@Override
	public int dr() {
		return 10+adj(1);
	}

	@Override
	public String description() {
		return "The succubi are demons that look like seductive (in a slightly gothic way) girls. Using its magic, the succubus "
				+ "can charm a hero, who will become unable to attack anything until the charm wears off.";
	}

	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add(Leech.class);
	}

	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Sleep.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
