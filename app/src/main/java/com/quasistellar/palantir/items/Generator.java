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
package com.quasistellar.palantir.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.hero.Hero;
import com.quasistellar.palantir.actors.mobs.npcs.Ghost;
import com.quasistellar.palantir.actors.mobs.npcs.Wandmaker.Rotberry;
import com.quasistellar.palantir.items.armor.Armor;
import com.quasistellar.palantir.items.armor.ClothArmor;
import com.quasistellar.palantir.items.armor.LeatherArmor;
import com.quasistellar.palantir.items.armor.MailArmor;
import com.quasistellar.palantir.items.armor.PlateArmor;
import com.quasistellar.palantir.items.armor.ScaleArmor;
import com.quasistellar.palantir.items.artifacts.AlchemistsToolkit;
import com.quasistellar.palantir.items.artifacts.Artifact;
import com.quasistellar.palantir.items.artifacts.CapeOfThorns;
import com.quasistellar.palantir.items.artifacts.ChaliceOfBlood;
import com.quasistellar.palantir.items.artifacts.CloakOfShadows;
import com.quasistellar.palantir.items.artifacts.DriedRose;
import com.quasistellar.palantir.items.artifacts.HornOfPlenty;
import com.quasistellar.palantir.items.artifacts.MasterThievesArmband;
import com.quasistellar.palantir.items.artifacts.RingOfDisintegration;
import com.quasistellar.palantir.items.artifacts.RingOfFrost;
import com.quasistellar.palantir.items.artifacts.SandalsOfNature;
import com.quasistellar.palantir.items.artifacts.TalismanOfForesight;
import com.quasistellar.palantir.items.artifacts.TimekeepersHourglass;
import com.quasistellar.palantir.items.artifacts.UnstableSpellbook;
import com.quasistellar.palantir.items.bags.Bag;
import com.quasistellar.palantir.items.food.Blackberry;
import com.quasistellar.palantir.items.food.BlueMilk;
import com.quasistellar.palantir.items.food.Blueberry;
import com.quasistellar.palantir.items.food.Cloudberry;
import com.quasistellar.palantir.items.food.DeathCap;
import com.quasistellar.palantir.items.food.Earthstar;
import com.quasistellar.palantir.items.food.Food;
import com.quasistellar.palantir.items.food.GoldenJelly;
import com.quasistellar.palantir.items.food.JackOLantern;
import com.quasistellar.palantir.items.food.Moonberry;
import com.quasistellar.palantir.items.food.MysteryMeat;
import com.quasistellar.palantir.items.food.Nut;
import com.quasistellar.palantir.items.food.Pasty;
import com.quasistellar.palantir.items.food.PixieParasol;
import com.quasistellar.palantir.items.nornstone.BlueNornStone;
import com.quasistellar.palantir.items.nornstone.GreenNornStone;
import com.quasistellar.palantir.items.nornstone.NornStone;
import com.quasistellar.palantir.items.nornstone.OrangeNornStone;
import com.quasistellar.palantir.items.nornstone.PurpleNornStone;
import com.quasistellar.palantir.items.nornstone.YellowNornStone;
import com.quasistellar.palantir.items.potions.Potion;
import com.quasistellar.palantir.items.potions.PotionOfExperience;
import com.quasistellar.palantir.items.potions.PotionOfFrost;
import com.quasistellar.palantir.items.potions.PotionOfHealing;
import com.quasistellar.palantir.items.potions.PotionOfInvisibility;
import com.quasistellar.palantir.items.potions.PotionOfLevitation;
import com.quasistellar.palantir.items.potions.PotionOfLiquidFlame;
import com.quasistellar.palantir.items.potions.PotionOfMending;
import com.quasistellar.palantir.items.potions.PotionOfMight;
import com.quasistellar.palantir.items.potions.PotionOfMindVision;
import com.quasistellar.palantir.items.potions.PotionOfOverHealing;
import com.quasistellar.palantir.items.potions.PotionOfParalyticGas;
import com.quasistellar.palantir.items.potions.PotionOfPurity;
import com.quasistellar.palantir.items.potions.PotionOfStrength;
import com.quasistellar.palantir.items.potions.PotionOfToxicGas;
import com.quasistellar.palantir.items.rings.Ring;
import com.quasistellar.palantir.items.rings.RingOfAccuracy;
import com.quasistellar.palantir.items.rings.RingOfElements;
import com.quasistellar.palantir.items.rings.RingOfEvasion;
import com.quasistellar.palantir.items.rings.RingOfForce;
import com.quasistellar.palantir.items.rings.RingOfFuror;
import com.quasistellar.palantir.items.rings.RingOfHaste;
import com.quasistellar.palantir.items.rings.RingOfMagic;
import com.quasistellar.palantir.items.rings.RingOfMight;
import com.quasistellar.palantir.items.rings.RingOfSharpshooting;
import com.quasistellar.palantir.items.rings.RingOfTenacity;
import com.quasistellar.palantir.items.rings.RingOfWealth;
import com.quasistellar.palantir.items.scrolls.Scroll;
import com.quasistellar.palantir.items.scrolls.ScrollOfIdentify;
import com.quasistellar.palantir.items.scrolls.ScrollOfLullaby;
import com.quasistellar.palantir.items.scrolls.ScrollOfMagicMapping;
import com.quasistellar.palantir.items.scrolls.ScrollOfMagicalInfusion;
import com.quasistellar.palantir.items.scrolls.ScrollOfMirrorImage;
import com.quasistellar.palantir.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.palantir.items.scrolls.ScrollOfRage;
import com.quasistellar.palantir.items.scrolls.ScrollOfRecharging;
import com.quasistellar.palantir.items.scrolls.ScrollOfRegrowth;
import com.quasistellar.palantir.items.scrolls.ScrollOfRemoveCurse;
import com.quasistellar.palantir.items.scrolls.ScrollOfTeleportation;
import com.quasistellar.palantir.items.scrolls.ScrollOfTerror;
import com.quasistellar.palantir.items.scrolls.ScrollOfUpgrade;
import com.quasistellar.palantir.items.wands.Wand;
import com.quasistellar.palantir.items.wands.WandOfAmok;
import com.quasistellar.palantir.items.wands.WandOfAvalanche;
import com.quasistellar.palantir.items.wands.WandOfBlink;
import com.quasistellar.palantir.items.wands.WandOfDisintegration;
import com.quasistellar.palantir.items.wands.WandOfFirebolt;
import com.quasistellar.palantir.items.wands.WandOfFlock;
import com.quasistellar.palantir.items.wands.WandOfLightning;
import com.quasistellar.palantir.items.wands.WandOfMagicMissile;
import com.quasistellar.palantir.items.wands.WandOfPoison;
import com.quasistellar.palantir.items.wands.WandOfRegrowth;
import com.quasistellar.palantir.items.wands.WandOfSlowness;
import com.quasistellar.palantir.items.wands.WandOfTelekinesis;
import com.quasistellar.palantir.items.wands.WandOfTeleportation;
import com.quasistellar.palantir.items.weapon.Weapon;
import com.quasistellar.palantir.items.weapon.melee.BattleAxe;
import com.quasistellar.palantir.items.weapon.melee.Dagger;
import com.quasistellar.palantir.items.weapon.melee.Glaive;
import com.quasistellar.palantir.items.weapon.melee.Knuckles;
import com.quasistellar.palantir.items.weapon.melee.Longsword;
import com.quasistellar.palantir.items.weapon.melee.Mace;
import com.quasistellar.palantir.items.weapon.melee.Quarterstaff;
import com.quasistellar.palantir.items.weapon.melee.ShortSword;
import com.quasistellar.palantir.items.weapon.melee.Spear;
import com.quasistellar.palantir.items.weapon.melee.Spork;
import com.quasistellar.palantir.items.weapon.melee.Sword;
import com.quasistellar.palantir.items.weapon.melee.WarHammer;
import com.quasistellar.palantir.items.weapon.missiles.Boomerang;
import com.quasistellar.palantir.items.weapon.missiles.CurareDart;
import com.quasistellar.palantir.items.weapon.missiles.Dart;
import com.quasistellar.palantir.items.weapon.missiles.IncendiaryDart;
import com.quasistellar.palantir.items.weapon.missiles.Javelin;
import com.quasistellar.palantir.items.weapon.missiles.Shuriken;
import com.quasistellar.palantir.items.weapon.missiles.Tamahawk;
import com.quasistellar.palantir.plants.BlandfruitBush;
import com.quasistellar.palantir.plants.Blindweed;
import com.quasistellar.palantir.plants.Dewcatcher;
import com.quasistellar.palantir.plants.Dreamfoil;
import com.quasistellar.palantir.plants.Earthroot;
import com.quasistellar.palantir.plants.Fadeleaf;
import com.quasistellar.palantir.plants.Firebloom;
import com.quasistellar.palantir.plants.Flytrap;
import com.quasistellar.palantir.plants.Icecap;
import com.quasistellar.palantir.plants.Phaseshift;
import com.quasistellar.palantir.plants.Plant;
import com.quasistellar.palantir.plants.Sorrowmoss;
import com.quasistellar.palantir.plants.Starflower;
import com.quasistellar.palantir.plants.Stormvine;
import com.quasistellar.palantir.plants.Sungrass;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Generator {

	public static enum Category {
		WEAPON(150, Weapon.class), ARMOR(100, Armor.class), POTION(500,
				Potion.class), POTION2(500, Potion.class),
			    SCROLL(400, Scroll.class), WAND(40, Wand.class), RING(
				15, Ring.class), ARTIFACT(20, Artifact.class), SEED(0,
				Plant.Seed.class), SEED2(0,	Plant.Seed.class), SEEDRICH(0,	Plant.Seed.class),
				FOOD(0, Food.class), GOLD(500, Gold.class), BERRY(50, Food.class), MUSHROOM(0, Food.class),
				NORNSTONE(0,NornStone.class), NORNSTONE2(0,NornStone.class);

		public Class<?>[] classes;
		public float[] probs;

		public float prob;
		public Class<? extends Item> superClass;

		private Category(float prob, Class<? extends Item> superClass) {
			this.prob = prob;
			this.superClass = superClass;
		}

		public static int order(Item item) {
			for (int i = 0; i < values().length; i++) {
				if (values()[i].superClass.isInstance(item)) {
					return i;
				}
			}

			return item instanceof Bag ? Integer.MAX_VALUE
					: Integer.MAX_VALUE - 1;
		}
	};

	private static HashMap<Category, Float> categoryProbs = new HashMap<Generator.Category, Float>();
	
	private static final float[] INITIAL_ARTIFACT_PROBS = new float[]{  0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0};

	static {

		Category.GOLD.classes = new Class<?>[] { Gold.class };
		Category.GOLD.probs = new float[] { 1 };

		Category.SCROLL.classes = new Class<?>[] { ScrollOfIdentify.class,
				ScrollOfTeleportation.class, ScrollOfRemoveCurse.class,
				ScrollOfUpgrade.class, ScrollOfRecharging.class,
				ScrollOfMagicMapping.class, ScrollOfRage.class,
				ScrollOfTerror.class, ScrollOfLullaby.class,
				ScrollOfMagicalInfusion.class, ScrollOfPsionicBlast.class,
				ScrollOfMirrorImage.class, ScrollOfRegrowth.class };
		Category.SCROLL.probs = new float[] { 30, 10, 15, 0, 10, 20, 10, 8, 8,
				0, 3, 6, 0 };

		Category.POTION.classes = new Class<?>[] { PotionOfHealing.class,
				PotionOfExperience.class, PotionOfToxicGas.class,
				PotionOfParalyticGas.class, PotionOfLiquidFlame.class,
				PotionOfLevitation.class, PotionOfStrength.class,
				PotionOfMindVision.class, PotionOfPurity.class,
				PotionOfInvisibility.class, PotionOfMight.class,
				PotionOfFrost.class, PotionOfMending.class,
				PotionOfOverHealing.class, Egg.class};
		Category.POTION.probs = new float[] { 10, 4, 15, 10, 15, 10, 0, 20, 12,
				10, 0, 10, 45, 4, 10};

		Category.POTION2.classes = new Class<?>[] { PotionOfHealing.class,
				PotionOfExperience.class, PotionOfToxicGas.class,
				PotionOfParalyticGas.class, PotionOfLiquidFlame.class,
				PotionOfLevitation.class, PotionOfStrength.class,
				PotionOfMindVision.class, PotionOfPurity.class,
				PotionOfInvisibility.class, PotionOfMight.class,
				PotionOfFrost.class, PotionOfMending.class,
				PotionOfOverHealing.class, Egg.class, EasterEgg.class};
		Category.POTION2.probs = new float[] { 10, 4, 15, 10, 15, 10, 0, 20, 12,
				10, 0, 10, 45, 4, 10, 1};


		Category.WAND.classes = new Class<?>[] { WandOfTeleportation.class,
				WandOfSlowness.class, WandOfFirebolt.class,
				WandOfRegrowth.class, WandOfPoison.class, WandOfBlink.class,
				WandOfLightning.class, WandOfAmok.class,
				WandOfTelekinesis.class, WandOfFlock.class,
				WandOfMagicMissile.class, WandOfDisintegration.class,
				WandOfAvalanche.class };
		Category.WAND.probs = new float[] { 10, 10, 15, 6, 10, 11, 15, 10, 6,
				5, 0, 5, 5 };

		Category.WEAPON.classes = new Class<?>[] { Dagger.class,
				Knuckles.class, Quarterstaff.class, Spear.class, Mace.class,
				Sword.class, Longsword.class, BattleAxe.class, WarHammer.class,
				Glaive.class, ShortSword.class, Dart.class, Javelin.class,
				IncendiaryDart.class, CurareDart.class, Shuriken.class,
				Boomerang.class, Tamahawk.class, Spork.class };
		Category.WEAPON.probs = new float[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
				0, 1, 1, 1, 1, 0, 1, 0 };

		Category.ARMOR.classes = new Class<?>[] { ClothArmor.class,
				LeatherArmor.class, MailArmor.class, ScaleArmor.class,
				PlateArmor.class };
		Category.ARMOR.probs = new float[] { 1, 1, 1, 1, 1 };

		Category.FOOD.classes = new Class<?>[] { Food.class, Pasty.class,
				MysteryMeat.class };
		Category.FOOD.probs = new float[] { 4, 1, 0 };

		Category.RING.classes = new Class<?>[] { RingOfAccuracy.class,
				RingOfEvasion.class, RingOfElements.class, RingOfForce.class,
				RingOfFuror.class, RingOfHaste.class, RingOfMagic.class,
				RingOfMight.class, RingOfSharpshooting.class,
				RingOfTenacity.class, RingOfWealth.class };
		Category.RING.probs = new float[] { 10, 10, 10, 10, 10, 10, 2, 10, 10, 10, 0 };

		Category.ARTIFACT.classes = new Class<?>[] { CapeOfThorns.class,
				ChaliceOfBlood.class, CloakOfShadows.class, HornOfPlenty.class,
				MasterThievesArmband.class, SandalsOfNature.class,
				TalismanOfForesight.class, TimekeepersHourglass.class,
				UnstableSpellbook.class, AlchemistsToolkit.class, RingOfDisintegration.class,
				RingOfFrost.class,
				DriedRose.class // starts with no chance of spawning, chance is
								// set directly after beating ghost quest.
		};
		Category.ARTIFACT.probs =  INITIAL_ARTIFACT_PROBS;

		Category.SEED.classes = new Class<?>[] { 
				Firebloom.Seed.class, Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class, Sungrass.Seed.class,
				Earthroot.Seed.class, Fadeleaf.Seed.class, Rotberry.Seed.class, BlandfruitBush.Seed.class, Dreamfoil.Seed.class,
				Stormvine.Seed.class, Nut.class, Blackberry.class, Blueberry.class, Cloudberry.class,
				Moonberry.class, Starflower.Seed.class, Phaseshift.Seed.class, Flytrap.Seed.class, Dewcatcher.Seed.class};
		
		Category.SEED.probs = new float[] { 12, 12, 12, 12, 12,
				                            12, 12, 0, 2, 12,
				                            12, 48, 20, 4, 16,
				                            2, 1, 1, 4, 8};
		
		
		Category.SEED2.classes = new Class<?>[] { Firebloom.Seed.class,
				Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class,
				Sungrass.Seed.class, Earthroot.Seed.class, Fadeleaf.Seed.class,
				Rotberry.Seed.class, BlandfruitBush.Seed.class,
				Dreamfoil.Seed.class, Stormvine.Seed.class,
				Starflower.Seed.class, Phaseshift.Seed.class, Flytrap.Seed.class,
				Dewcatcher.Seed.class};
		
		Category.SEED2.probs = new float[] { 12, 12, 12, 12, 12, 12, 12, 0, 4, 12, 12, 2, 2, 6, 8};
		
		Category.SEEDRICH.classes = new Class<?>[] { Firebloom.Seed.class,
				Icecap.Seed.class, Sorrowmoss.Seed.class, Blindweed.Seed.class,
				Sungrass.Seed.class, Earthroot.Seed.class, Fadeleaf.Seed.class,
				Rotberry.Seed.class, BlandfruitBush.Seed.class,
				Dreamfoil.Seed.class, Stormvine.Seed.class,
				Starflower.Seed.class, Phaseshift.Seed.class, Flytrap.Seed.class,
				Dewcatcher.Seed.class};
		
		Category.SEEDRICH.probs = new float[] { 1, 1, 1, 1, 2, 1, 1, 0, 4, 1, 1, 4, 4, 8, 6};
		
		Category.BERRY.classes = new Class<?>[] {Blackberry.class, Blueberry.class, Cloudberry.class, Moonberry.class};
		Category.BERRY.probs = new float[] {8,2,2,1};	
		
		Category.MUSHROOM.classes = new Class<?>[] {BlueMilk.class, DeathCap.class, Earthstar.class, JackOLantern.class, PixieParasol.class, GoldenJelly.class};
		Category.MUSHROOM.probs = new float[] {2,2,2,2,2,2};
		
		Category.NORNSTONE.classes = new Class<?>[] {BlueNornStone.class, GreenNornStone.class, OrangeNornStone.class, PurpleNornStone.class, YellowNornStone.class};
		Category.NORNSTONE.probs = new float[] {2,2,2,2,2};
		
		Category.NORNSTONE2.classes = new Class<?>[] {BlueNornStone.class, GreenNornStone.class, OrangeNornStone.class, PurpleNornStone.class, YellowNornStone.class};
		Category.NORNSTONE2.probs = new float[] {2,0,2,2,2};
		
	}

	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put(cat, cat.prob);
		}
	}

	public static Item random() {
		return random(Random.chances(categoryProbs));
	}

	public static Item random(Category cat) {
		try {

			categoryProbs.put(cat, categoryProbs.get(cat) / 2);

			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			case ARTIFACT:
				Item item = randomArtifact();
				// if we're out of artifacts, return a ring instead.
				return item != null ? item : random(Category.RING);
			default:
				return ((Item) cat.classes[Random.chances(cat.probs)]
						.newInstance()).random();
			}

		} catch (Exception e) {

			return null;

		}
	}

	public static Item random(Class<? extends Item> cl) {
		try {

			return ((Item) cl.newInstance()).random();

		} catch (Exception e) {

			return null;

		}
	}

	public static Armor randomArmor() {
		int curStr = Hero.STARTING_STR
				+ Dungeon.limitedDrops.strengthPotions.count;

		return randomArmor(curStr);
	}

	public static Armor randomArmor(int targetStr) {

		Category cat = Category.ARMOR;

		try {
			Armor a1 = (Armor) cat.classes[Random.chances(cat.probs)]
					.newInstance();
			Armor a2 = (Armor) cat.classes[Random.chances(cat.probs)]
					.newInstance();

			a1.random();
			a2.random();

			return Math.abs(targetStr - a1.STR) < Math.abs(targetStr - a2.STR) ? a1
					: a2;
		} catch (Exception e) {
			return null;
		}
	}

	public static Weapon randomWeapon() {
		int curStr = Hero.STARTING_STR
				+ Dungeon.limitedDrops.strengthPotions.count;

		return randomWeapon(curStr);
	}

	public static Weapon randomWeapon(int targetStr) {

		try {
			Category cat = Category.WEAPON;

			Weapon w1 = (Weapon) cat.classes[Random.chances(cat.probs)]
					.newInstance();
			Weapon w2 = (Weapon) cat.classes[Random.chances(cat.probs)]
					.newInstance();

			w1.random();
			w2.random();

			return Math.abs(targetStr - w1.STR) < Math.abs(targetStr - w2.STR) ? w1
					: w2;
		} catch (Exception e) {
			return null;
		}
	}

	// enforces uniqueness of artifacts throughout a run.
	public static Artifact randomArtifact() {

		try {
			Category cat = Category.ARTIFACT;
			int i = Random.chances(cat.probs);

			// if no artifacts are left, return null
			if (i == -1) {
				return null;
			}

			Artifact artifact = (Artifact) cat.classes[i].newInstance();

			// remove the chance of spawning this artifact.
			cat.probs[i] = 0;
			spawnedArtifacts.add(cat.classes[i].getSimpleName());

			artifact.random();

			return artifact;

		} catch (Exception e) {
			return null;
		}
	}

	public static boolean removeArtifact(Artifact artifact) {
		if (spawnedArtifacts.contains(artifact.getClass().getSimpleName()))
			return false;

		Category cat = Category.ARTIFACT;
		for (int i = 0; i < cat.classes.length; i++)
			if (cat.classes[i].equals(artifact.getClass())) {
				if (cat.probs[i] == 1) {
					cat.probs[i] = 0;
					spawnedArtifacts.add(artifact.getClass().getSimpleName());
					return true;
				} else
					return false;
			}

		return false;
	}

	// resets artifact probabilities, for new dungeons
	public static void initArtifacts() {
		Category.ARTIFACT.probs = INITIAL_ARTIFACT_PROBS;
		
		//checks for dried rose quest completion, adds the rose in accordingly.
		if (Ghost.Quest.processed) Category.ARTIFACT.probs[12] = 1;
		spawnedArtifacts = new ArrayList<String>();
	}

	private static ArrayList<String> spawnedArtifacts = new ArrayList<String>();

	private static final String ARTIFACTS = "artifacts";

	// used to store information on which artifacts have been spawned.
	public static void storeInBundle(Bundle bundle) {
		bundle.put(ARTIFACTS,
				spawnedArtifacts.toArray(new String[spawnedArtifacts.size()]));
	}

	public static void restoreFromBundle(Bundle bundle) {
		initArtifacts();

		if (bundle.contains(ARTIFACTS)) {
			Collections.addAll(spawnedArtifacts,
					bundle.getStringArray(ARTIFACTS));
			Category cat = Category.ARTIFACT;

			for (String artifact : spawnedArtifacts)
				for (int i = 0; i < cat.classes.length; i++)
					if (cat.classes[i].getSimpleName().equals(artifact))
						cat.probs[i] = 0;
		}
	}
}
