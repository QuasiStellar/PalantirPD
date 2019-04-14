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
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Burning;
import com.quasistellar.palantir.actors.buffs.Terror;
import com.quasistellar.palantir.actors.buffs.Weakness;
import com.quasistellar.palantir.items.AdamantWeapon;
import com.quasistellar.palantir.items.Gold;
import com.quasistellar.palantir.items.potions.PotionOfLiquidFlame;
import com.quasistellar.palantir.items.wands.WandOfFirebolt;
import com.quasistellar.palantir.items.weapon.enchantments.Fire;
import com.quasistellar.palantir.scenes.GameScene;
import com.quasistellar.palantir.sprites.SkeletonKingSprite;
import com.watabou.utils.Random;

public class SkeletonKing extends Mob {

	{
		name = "skeleton king";
		spriteClass = SkeletonKingSprite.class;

		HP = HT = 550;
		defenseSkill = 30;

		EXP = 10;
		maxLvl = 20;

		flying = true;

		loot = new PotionOfLiquidFlame();
		lootChance = 0.1f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(20, 40);
	}

	@Override
	public int attackSkill(Char target) {
		return 25;
	}

	@Override
	public int dr() {
		return 25;
	}

	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Weakness.class) == null) {
			state = HUNTING;
		}
		return result;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(2) == 0) {
			if(enemy == Dungeon.hero){
			 Buff.prolong(enemy, Weakness.class, Weakness.duration(enemy));
			state = FLEEING;
			}
		}

		return damage;
	}
	
	
	@Override
	public void die(Object cause) {

		GameScene.bossSlain();
		Dungeon.level.drop(new Gold(Random.Int(1900, 4000)), pos).sprite.drop();
		Dungeon.level.drop(new AdamantWeapon(), pos).sprite.drop();
		Dungeon.skeletonkingkilled=true;
			
		super.die(cause);
		
		yell("Ughhhh...");
					
	}
	

	@Override
	public void notice() {
		super.notice();
		yell("You will die screaming in flames, " + Dungeon.hero.givenName() + "!");
	}

	
	@Override
	public String description() {
		return "This ancient giant skull belongs to the king of skeletons. ";
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Burning.class);
		IMMUNITIES.add(Fire.class);
		IMMUNITIES.add(WandOfFirebolt.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
