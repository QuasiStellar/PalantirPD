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
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.Statistics;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.blobs.Blob;
import com.quasistellar.otiluke.actors.blobs.ConfusionGas;
import com.quasistellar.otiluke.actors.blobs.CorruptGas;
import com.quasistellar.otiluke.actors.blobs.ToxicGas;
import com.quasistellar.otiluke.items.Bone;
import com.quasistellar.otiluke.items.Generator;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.PrisonKey;
import com.quasistellar.otiluke.items.RedDewdrop;
import com.quasistellar.otiluke.items.StoneOre;
import com.quasistellar.otiluke.items.YellowDewdrop;
import com.quasistellar.otiluke.items.weapon.enchantments.Death;
import com.quasistellar.otiluke.items.weapon.melee.relic.RelicMeleeWeapon;
import com.quasistellar.otiluke.items.weapon.missiles.JupitersWraith;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.sprites.KupuaSprite;
import com.quasistellar.otiluke.sprites.MossySkeletonSprite;
import com.quasistellar.otiluke.sprites.SkeletonSprite;
import com.quasistellar.otiluke.utils.GLog;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Random;

public class Kupua extends Mob {
  //Gullin
	
	{
		name = "kupua";
		spriteClass = KupuaSprite.class;

		HP = HT = 550+(adj(0)*Random.NormalIntRange(3, 7));
		defenseSkill = 15+adj(1);
		baseSpeed = 2f;

		EXP = 10;
		maxLvl = 999;

		loot = new StoneOre();
		lootChance = 0.9f;
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(70+adj(0), 160+adj(0));
	}
		
	@Override
	public int attackSkill(Char target) {
		return 120+adj(0);
	}

	@Override
	public int dr() {
		return 50+adj(0);
	}

	@Override
	public void damage(int dmg, Object src) {
		
		if(!(src instanceof RelicMeleeWeapon || src instanceof JupitersWraith)){
			int max = Math.round(dmg*.5f);
			dmg = Random.Int(1,max);
		}
		
		if (dmg > HT/8){
		GameScene.add(Blob.seed(pos, 30, CorruptGas.class));
		}
		
		super.damage(dmg, src);
	}
	
	@Override
	public String defenseVerb() {
		return "blocked";
	}

	@Override
	public String description() {
		return "Kupua are minor trickster demons who like to appear enmasse. ";
	}

	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add(Death.class);
		IMMUNITIES.add(ToxicGas.class);
		IMMUNITIES.add(CorruptGas.class);
	}

	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
