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

import com.quasistellar.otiluke.Badges;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.effects.particles.SparkParticle;
import com.quasistellar.otiluke.items.Generator;
import com.quasistellar.otiluke.items.Gold;
import com.quasistellar.otiluke.items.keys.GoldenSkeletonKey;
import com.quasistellar.otiluke.items.keys.SkeletonKey;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.traps.LightningTrap;
import com.quasistellar.otiluke.mechanics.Ballistica;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.CharSprite;
import com.quasistellar.otiluke.sprites.HermitCrabSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.Camera;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class HermitCrab extends Mob implements Callback {

	private static final float TIME_TO_ZAP = 2f;

	private static final String TXT_LIGHTNING_KILLED = "%s's lightning bolt killed you...";
	private static final String TXT_SHELL_ABSORB = "Hermit Crab absorbed the attack in its shell.";
	private static final String TXT_SHELL_CHARGE = "Lightning Shell charged by %s.";

	{
		name = "hermit crab";
		spriteClass = HermitCrabSprite.class;

		HP = HT = 20;
		defenseSkill = 22;

		EXP = 6;

		loot = Generator.Category.BERRY;
		lootChance = 0.33f;
		
	
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(10, 20);
	}

	@Override
	public int attackSkill(Char target) {
		return 25;
	}

	@Override
	public int dr() {
		return 4;
	}

	@Override
	public void damage(int dmg, Object src) {
		
		if (dmg>HT/4 && src != LightningTrap.LIGHTNING){
            for (Mob mob : Dungeon.level.mobs) {
			if (mob instanceof Shell && mob.isAlive()){
				Dungeon.shellCharge+=dmg;
				GLog.n(TXT_SHELL_ABSORB);
				GLog.n(TXT_SHELL_CHARGE, dmg);
				dmg=1;
				
			    }			
			}
		}			
		super.damage(dmg, src);
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
				((HermitCrabSprite) sprite).zap(enemy.pos);
			}

			spend(TIME_TO_ZAP);

			if (hit(this, enemy, true)) {
				int dmg = Random.Int(15, 30);
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

	@Override
	public void call() {
		next();
	}
	
	@Override
	public void die(Object cause) {
		super.die(cause);  
		if(Random.Int(1)==0){
		Dungeon.level.drop(new GoldenSkeletonKey(0), pos).sprite.drop();	
		}
	}
	

	@Override
	public String description() {
		return "Minions of the crab king, these tough crabs absorb all but the weakest attacks with their shells. "
				+"The damage they absorb is used to charge the lightning shell. ";
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
