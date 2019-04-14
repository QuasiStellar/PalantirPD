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
package com.quasistellar.palantir.actors.hero;

import com.quasistellar.palantir.Assets;
import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.Palantir;
import com.quasistellar.palantir.items.Bomb;
import com.quasistellar.palantir.items.EasterEgg;
import com.quasistellar.palantir.items.Egg;
import com.quasistellar.palantir.items.OtilukesJournal;
import com.quasistellar.palantir.items.ShadowDragonEgg;
import com.quasistellar.palantir.items.stones.Diamond1;
import com.quasistellar.palantir.items.stones.Diamond2;
import com.quasistellar.palantir.items.stones.Diamond3;
import com.quasistellar.palantir.items.stones.Diamond4;
import com.quasistellar.palantir.items.stones.Diamond5;
import com.quasistellar.palantir.items.stones.Stone1;
import com.quasistellar.palantir.items.stones.Stone2;
import com.quasistellar.palantir.items.stones.Stone3;
import com.quasistellar.palantir.items.Whistle;
import com.quasistellar.palantir.items.armor.PlateArmor;
import com.quasistellar.palantir.items.artifacts.CloakOfShadows;
import com.quasistellar.palantir.items.bags.KeyRing;
import com.quasistellar.palantir.items.journalpages.JournalPage;
import com.quasistellar.palantir.items.journalpages.Sokoban1;
import com.quasistellar.palantir.items.journalpages.Sokoban2;
import com.quasistellar.palantir.items.journalpages.Sokoban3;
import com.quasistellar.palantir.items.journalpages.Sokoban4;
import com.quasistellar.palantir.items.journalpages.Vault;
import com.quasistellar.palantir.items.misc.Spectacles;
import com.quasistellar.palantir.items.potions.PotionOfMindVision;
import com.quasistellar.palantir.items.scrolls.Scroll;
import com.quasistellar.palantir.items.scrolls.ScrollOfIdentify;
import com.quasistellar.palantir.items.scrolls.ScrollOfMagicMapping;
import com.quasistellar.palantir.items.scrolls.ScrollOfMagicalInfusion;
import com.quasistellar.palantir.items.scrolls.ScrollOfPsionicBlast;
import com.quasistellar.palantir.items.scrolls.ScrollOfRemoveCurse;
import com.quasistellar.palantir.items.scrolls.ScrollOfUpgrade;
import com.quasistellar.palantir.items.stones.Stone4;
import com.quasistellar.palantir.items.stones.Stone5;
import com.quasistellar.palantir.items.wands.Wand;
import com.quasistellar.palantir.items.wands.WandOfFlock;
import com.quasistellar.palantir.items.wands.WandOfMagicMissile;
import com.quasistellar.palantir.items.weapon.melee.Dagger;
import com.quasistellar.palantir.items.weapon.melee.Knuckles;
import com.quasistellar.palantir.items.weapon.melee.WarHammer;
import com.quasistellar.palantir.items.weapon.missiles.Boomerang;
import com.quasistellar.palantir.items.weapon.missiles.Dart;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.utils.Bundle;

public enum HeroClass {

	WARRIOR("warrior"), MAGE("mage"), ROGUE("rogue"), HUNTRESS("huntress");

	private String title;

	private HeroClass(String title) {
		this.title = title;
	}

	public static final String[] WAR_PERKS = {
			"Warriors start with 11 points of Strength.",
			"Warriors start with a unique short sword. This sword can be later \"reforged\" to upgrade another melee weapon.",
			"Warriors are less proficient with missile weapons.",
			"Any piece of food restores more health when eaten.",
			"Potions of Strength are identified from the beginning.", };

	public static final String[] MAG_PERKS = {
			"Mages start with a unique Wand of Magic Missile. This wand can be later \"disenchanted\" to upgrade another wand.",
			"Mages recharge their wands faster.",
			"When eaten, any piece of food restores 1 charge for all wands in the inventory.",
			"Mages can use wands as a melee weapon.",
			"Dew blessings are more effective for mages.",
			"Scrolls of Identify are identified from the beginning." };

	public static final String[] ROG_PERKS = {
			"Rogues start with a unique Cloak of Shadows.",
			"Rogues identify a type of a ring on equipping it.",
			"Rogues are proficient with light armor, dodging better while wearing one.",
			"Rogues are proficient in detecting hidden doors and traps.",
			"Rogues can go without food longer.",
			"Scrolls of Magic Mapping are identified from the beginning." };

	public static final String[] HUN_PERKS = {
			"Huntresses start with 20 points of Health.",
			"Huntresses start with a unique upgradeable boomerang.",
			"Huntresses are proficient with missile weapons, getting bonus damage from excess strength.",
			"Huntresses are able to recover a single used missile weapon from each enemy.",
			"Huntresses gain more health from dewdrops.",
			"Huntresses sense neighbouring monsters even if they are hidden behind obstacles.",
			"Potions of Mind Vision are identified from the beginning." };

	public void initHero(Hero hero) {

		hero.heroClass = this;

		initCommon(hero);

		switch (this) {
		case WARRIOR:
			initWarrior(hero);
			break;

		case MAGE:
			initMage(hero);
			break;

		case ROGUE:
			initRogue(hero);
			break;

		case HUNTRESS:
			initHuntress(hero);
			break;
		}
		
			//new OtilukesJournal().collect();
			//Dungeon.limitedDrops.journal.drop();
		

		hero.updateAwareness();
	}

	private static void initCommon(Hero hero) {
//		if (!Dungeon.isChallenged(Challenges.NO_ARMOR))
//			(hero.belongings.armor = new ClothArmor()).identify();

//		if (!Dungeon.isChallenged(Challenges.NO_FOOD))
//			new Food().identify().collect();

		new Stone1().collect();
		new Diamond1().collect();

		new Stone2().collect();
		new Diamond2().collect();

        new Stone3().collect();
		new Diamond3().collect();

		new Stone4().collect();
		new Diamond4().collect();

		new Stone5().collect();
		new Diamond5().collect();

//		new Spectacles().collect();
//		new Blueberry().collect();
//		new PotionOfMindVision().collect();
	}

	private static void initWarrior(Hero hero) {
//		hero.STR = hero.STR + 1;
		

//		(hero.belongings.weapon = new ShortSword()).identify();
//		Dart darts = new Dart(8);
//		darts.identify().collect();
//
//		Dungeon.quickslot.setSlot(0, darts);
//
//		KeyRing keyring = new KeyRing(); keyring.collect();
//
//		new PotionOfStrength().setKnown();
//
		//playtest(hero);
	}

	private static void initMage(Hero hero) {
		(hero.belongings.weapon = new Knuckles()).identify();

		WandOfMagicMissile wand = new WandOfMagicMissile();
		wand.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();
		
		Dungeon.quickslot.setSlot(0, wand);

		new ScrollOfIdentify().setKnown();
		
		//playtest(hero);
	}

	private static void initRogue(Hero hero) {
		(hero.belongings.weapon = new Dagger()).identify();

		CloakOfShadows cloak = new CloakOfShadows();
		(hero.belongings.misc1 = cloak).identify();
		hero.belongings.misc1.activate(hero);

		Dart darts = new Dart(10);
		darts.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		Dungeon.quickslot.setSlot(0, cloak);
		if (Palantir.quickSlots() > 1)
			Dungeon.quickslot.setSlot(1, darts);
		
		Bomb bomb = new Bomb(); bomb.collect();
		new ScrollOfMagicMapping().setKnown();
	}

	private static void initHuntress(Hero hero) {

		//hero.HP = (hero.HT -= 5);

		(hero.belongings.weapon = new Dagger()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();
		
		KeyRing keyring = new KeyRing(); keyring.collect();

		Dungeon.quickslot.setSlot(0, boomerang);

		new PotionOfMindVision().setKnown();
	}

	public void playtest(Hero hero) {
		if (!Dungeon.playtest){
		//Playtest
		//TomeOfMastery tome = new TomeOfMastery(); tome.collect();
				
				hero.HT=hero.HP=999;
				hero.STR = hero.STR + 20;
			PlateArmor armor1 = new PlateArmor();
		   armor1.reinforce().upgrade(140).identify().collect();
		   // PlateArmor armor2 = new PlateArmor();
		   // armor2.upgrade(14).identify().collect();
		   WarHammer hammer = new WarHammer();
		    hammer.reinforce().upgrade(115).identify().collect();
		     Spectacles specs = new Spectacles(); specs.collect();
		     Whistle whistle = new Whistle(); whistle.collect();
		    //Dewcatcher.Seed seed3 = new Dewcatcher.Seed(); seed3.collect();
		    //Flytrap.Seed seed1 = new Flytrap.Seed(); seed1.collect();
		    //Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
		    //Starflower.Seed seed4 = new Starflower.Seed(); seed4.collect();
				//BlueNornStone stone1 = new BlueNornStone(); stone1.collect();
				//YellowNornStone stone2 = new YellowNornStone(); stone2.collect();
				//PurpleNornStone stone3 = new PurpleNornStone(); stone3.collect();
				//PotionBandolier bag1 = new PotionBandolier(); bag1.collect();
				//ScrollHolder bag2 = new ScrollHolder(); bag2.collect();
				// armor = new PlateArmor(); armor.upgrade(130); armor.identify().collect();
				//OrbOfZot zot = new OrbOfZot(); zot.collect();
			    //WarHammer hammer = new WarHammer();
			    //hammer.identify().collect();
			   // Wand wand = new WandOfDisintegration(); wand.upgrade(50); wand.collect();
				  Wand wand3 = new WandOfFlock(); wand3.upgrade(15); wand3.collect();
				 // Wand wand2 = new WandOfTelekinesis(); wand2.upgrade(15); wand2.collect();
				  //Wand wand3 = new WandOfTeleportation(); wand3.upgrade(15); wand3.collect();
			  // Wand wand4 = new WandOfBlink(); wand4.upgrade(15); wand4.collect();
				//Wand wand2 = new WandOfFirebolt(); wand2.upgrade(15); wand2.collect();
				//Wand wand3 = new WandOfLightning(); wand3.upgrade(15); wand3.collect();
				//Ring ring = new RingOfHaste(); ring.upgrade(25); ring.collect();
				//ConchShell conch = new ConchShell(); conch.collect();
				//AncientCoin coin = new AncientCoin(); coin.collect();
				//TenguKey key = new TenguKey(); key.collect();
				OtilukesJournal jn = new OtilukesJournal(); jn.collect();
				JournalPage sk1 = new Sokoban1(); sk1.collect();
				JournalPage sk2 = new Sokoban2(); sk2.collect();
				JournalPage sk3 = new Sokoban3(); sk3.collect();
				JournalPage sk4 = new Sokoban4(); sk4.collect();
				JournalPage sk5 = new Vault(); sk5.collect();
				//JournalPage town = new Town(); town.collect();
				//JournalPage cave = new DragonCave(); cave.collect();
				//NeptunusTrident trident = new NeptunusTrident(); trident.enchantNeptune(); trident.upgrade(200); trident.collect();
				//CromCruachAxe axe = new CromCruachAxe(); axe.enchantLuck(); axe.collect();
				//AresSword sword = new AresSword(); sword.enchantAres(); sword.collect();
				//JupitersWraith wraith = new JupitersWraith(); wraith.enchantJupiter(); wraith.collect();
				//LokisFlail flail = new LokisFlail(); flail.enchantLoki(); flail.collect();
				//JournalPage sk5 = new Town(); sk5.collect();
				//Wand wand = new WandOfAmok(); wand.upgrade(15); wand.collect();
				//Bone bone = new Bone(); bone.collect();
				//ConchShell conch = new ConchShell(); conch.collect();
				//AncientCoin coin = new AncientCoin(); coin.collect();
				//TenguKey key = new TenguKey(); key.collect();
				//CavesKey key2 = new CavesKey(); key2.collect();
			    //SanChikarah san = new SanChikarah(); san.collect();
				//BookOfLife lbook = new BookOfLife(); lbook.collect();
				//BookOfDead dbook = new BookOfDead(); dbook.collect();
			   // ReturnBeacon beacon = new ReturnBeacon(); beacon.collect();
			  //SanChikarahDeath san = new SanChikarahDeath(); san.collect();	
			   // Blueberry berry = new Blueberry(60); berry.collect();
			   // PotionOfMindVision potion4 = new PotionOfMindVision(); potion4.collect();
			    //Dewcatcher.Seed seed3 = new Dewcatcher.Seed(); seed3.collect();
			   // ActiveMrDestructo mrd = new ActiveMrDestructo(); mrd.collect();
			   // ActiveMrDestructo2 mrd2 = new ActiveMrDestructo2(); mrd2.collect();
			  // RingOfDisintegration ar = new RingOfDisintegration(); ar.collect();
			  //RingOfFrost fr = new RingOfFrost(); fr.collect();
			    //RingOfHaste ha = new RingOfHaste(); ha.upgrade(5); ha.collect();
				//PotionOfFrost pot = new PotionOfFrost(); pot.collect();
				//SteelHoneypot hpot = new SteelHoneypot(); hpot.collect();
				Egg egg = new Egg(); egg.collect();
				EasterEgg egg2 = new EasterEgg(); egg2.collect();
				ShadowDragonEgg egg3 = new ShadowDragonEgg(); egg3.collect();
				//GoldenSkeletonKey key = new GoldenSkeletonKey(0); key.collect(); 
				//Flytrap.Seed seed1 = new Flytrap.Seed(); seed1.collect();
				//Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
				//Starflower.Seed seed3 = new Starflower.Seed(); seed3.collect();
				//BlandfruitBush.Seed seed4 = new BlandfruitBush.Seed(); seed4.collect();
				
				//Chainsaw saw = new Chainsaw(); saw.enchantBuzz(); saw.collect();
				//PotionBandolier bag1 = new PotionBandolier(); bag1.collect();
				//ScrollHolder bag2 = new ScrollHolder(); bag2.collect();
				//AnkhChain chain = new AnkhChain(); chain.collect();
				//WandHolster holster = new WandHolster(); holster.collect();
				//AutoPotion apot = new AutoPotion(); apot.collect();
				//AdamantArmor aArmor = new AdamantArmor(); aArmor.collect();
				//AdamantWand aWand = new AdamantWand(); aWand.collect();
				//AdamantRing aRing = new AdamantRing(); aRing.collect();
				//AdamantWeapon aWeapon = new AdamantWeapon(); aWeapon.collect();
			    //PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();
				
				//PuddingCup cup = new PuddingCup(); cup.collect();
				
				Dungeon.playtest=true;
				GLog.i("Playtest Activated");
				
		
		         
		 			for(int i=0; i<199; i++){
					Scroll scroll = new ScrollOfMagicalInfusion();
			        scroll.identify().collect();
			        Scroll scroll2 = new ScrollOfUpgrade();
			        scroll2.identify().collect();  
			       
			        Scroll scroll3 = new ScrollOfIdentify();
			        scroll3.identify().collect();  
			        Scroll scroll4 = new ScrollOfRemoveCurse();
			        scroll4.identify().collect(); 
			        Scroll scroll5 = new ScrollOfPsionicBlast();
			        scroll5.identify().collect(); 
			        
			        hero.earnExp(hero.maxExp() - hero.exp);
			       }	
				
				/*
				for(int i=1; i<61; i++){
			        PotionOfExperience potion1 = new PotionOfExperience(); potion1.collect();
			       PotionOfInvisibility potion2 = new PotionOfInvisibility(); potion2.collect();
			      PotionOfHealing potion3 = new PotionOfHealing(); potion3.collect();
			       PotionOfMindVision potion4 = new PotionOfMindVision(); potion4.collect();
			      PotionOfLevitation potion6 = new PotionOfLevitation(); potion6.collect();
			      //PotionOfFrost potion6 = new PotionOfFrost(); potion6.collect();
			      PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();
			     // Bomb bomb = new Bomb(); bomb.collect();
			      //DarkGold darkgold = new DarkGold(); darkgold.collect();
			        }
				*/
				
		}
		
				/*
							 			      
			      
				         
				
				       
				  Blueberry berry = new Blueberry(10); berry.collect();
				  ClusterBomb cbomb = new ClusterBomb(); cbomb.collect();
				  DizzyBomb dbomb = new DizzyBomb(); dbomb.collect();
				  SmartBomb smbomb = new SmartBomb(); smbomb.collect();
				  SeekingBombItem sbomb = new SeekingBombItem(); sbomb.collect();
				  SeekingClusterBombItem scbomb = new SeekingClusterBombItem(); scbomb.collect();
				  
				  
				//  Bomb bomb = new Bomb(); bomb.collect();
				//DeathCap mush1 = new DeathCap(); mush1.collect();
				//GoldenJelly mush2 = new GoldenJelly(); mush2.collect();
				//BlueMilk mush3 = new BlueMilk(); mush3.collect();
				//JackOLantern mush4 = new JackOLantern(); mush4.collect();
				//Earthstar mush5 = new Earthstar(); mush5.collect();
				//Lichen mush6 = new Lichen(); mush6.collect();
				//PixieParasol mush7 = new PixieParasol(); mush7.collect();
				
				ActiveMrDestructo mrd = new ActiveMrDestructo(); mrd.collect();
				//OrbOfZot orb = new OrbOfZot(); orb.collect();
				        
				//Phaseshift.Seed seed = new Phaseshift.Seed(); seed.collect();
				//Phaseshift.Seed seed2 = new Phaseshift.Seed(); seed2.collect();
				//Starflower.Seed seed3 = new Starflower.Seed(); seed3.collect();
				
								
				//PotionOfLiquidFlame potion5 = new PotionOfLiquidFlame(); potion5.collect();
				//BookOfDead dbook = new BookOfDead(); dbook.collect();
				///BookOfLife lbook = new BookOfLife(); lbook.collect();
				//BookOfTranscendence tbook = new BookOfTranscendence(); tbook.collect();
				//SanChikarah san = new SanChikarah(); san.collect();	
				
		        //SewersKey key1 = new SewersKey(); key1.collect();
		        //PrisonKey key2 = new PrisonKey(); key2.collect();
		        //CavesKey key3 = new CavesKey(); key3.collect();
		        //CityKey key4 = new CityKey(); key4.collect();
		      // HallsKey key5 = new HallsKey(); key5.collect();
		        FullMoonberry berry2 = new FullMoonberry(10); berry2.collect();		
*/
	}
	
	public String title() {
		return title;
	}

	public String spritesheet() {

		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
		}

		return null;
	}

	public String[] perks() {

		switch (this) {
		case WARRIOR:
			return WAR_PERKS;
		case MAGE:
			return MAG_PERKS;
		case ROGUE:
			return ROG_PERKS;
		case HUNTRESS:
			return HUN_PERKS;
		}

		return null;
	}

	private static final String CLASS = "class";

	public void storeInBundle(Bundle bundle) {
		bundle.put(CLASS, toString());
	}

	public static HeroClass restoreInBundle(Bundle bundle) {
		String value = bundle.getString(CLASS);
		return value.length() > 0 ? valueOf(value) : ROGUE;
	}
}
