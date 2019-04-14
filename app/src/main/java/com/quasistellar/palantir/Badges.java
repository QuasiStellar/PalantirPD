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
package com.quasistellar.palantir;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import android.content.Context;

import com.quasistellar.palantir.scenes.PixelScene;
import com.quasistellar.palantir.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;

public class Badges {

	public static enum Badge {

		CRYSTAL_1("Green crystal collected", 0),
		CRYSTAL_2("Blue crystal collected", 1),
		CRYSTAL_3("Orange crystal collected", 2),
		CRYSTAL_4("Purple crystal collected", 3),

		STONE_1("All green stones collected", 4),
		STONE_2("All blue stones collected", 5),
		STONE_3("All orange stones collected", 6),
		STONE_4("All purple stones collected", 7),

		ALL_CRYSTALS("All crystals collected", 8, true),
		ALL_STONES("All stones collected", 9, true),

		DEATH_FROM_STATUE("Death from statue", 10),

		PALANTIR("Palantir obtained", 11),

		SPEEDRUN_1("Complete game in 3000 moves", 12),
		SPEEDRUN_2("Complete game in 2500 moves", 13),
		SPEEDRUN_3("Complete game in 2000 moves", 14),
		SPEEDRUN_4("Complete game in 1500 moves", 15),

		//update 0.2
		CRYSTAL_5("Yellow crystal collected", 16),
		STONE_5("All yellow stones collected", 17);

		public boolean meta;

		public String description;
		public int image;

		private Badge(String description, int image) {
			this(description, image, false);
		}

		private Badge(String description, int image, boolean meta) {
			this.description = description;
			this.image = image;
			this.meta = meta;
		}

		private Badge() {
			this("", -1);
		}
	}

	private static HashSet<Badge> global;
	private static HashSet<Badge> local = new HashSet<Badges.Badge>();

	private static boolean saveNeeded = false;

	public static Callback loadingListener = null;

	public static void reset() {
		local.clear();
		loadGlobal();
	}

	private static final String BADGES_FILE = "new_badges.dat";
	private static final String BADGES = "badges";

	private static HashSet<Badge> restore(Bundle bundle) {
		HashSet<Badge> badges = new HashSet<Badge>();

		String[] names = bundle.getStringArray(BADGES);
		for (int i = 0; i < names.length; i++) {
			try {
				badges.add(Badge.valueOf(names[i]));
			} catch (Exception e) {
			}
		}

		return badges;
	}

	private static void store(Bundle bundle, HashSet<Badge> badges) {
		int count = 0;
		String names[] = new String[badges.size()];

		for (Badge badge : badges) {
			names[count++] = badge.toString();
		}
		bundle.put(BADGES, names);
	}

	public static void loadLocal(Bundle bundle) {
		local = restore(bundle);
	}

	public static void saveLocal(Bundle bundle) {
		store(bundle, local);
	}

	public static void loadGlobal() {
		if (global == null) {
			try {
				InputStream input = Game.instance.openFileInput(BADGES_FILE);
				Bundle bundle = Bundle.read(input);
				input.close();

				global = restore(bundle);

			} catch (Exception e) {
				global = new HashSet<Badge>();
			}
		}
	}

	public static void saveGlobal() {
		if (saveNeeded) {

			Bundle bundle = new Bundle();
			store(bundle, global);

			try {
				OutputStream output = Game.instance.openFileOutput(BADGES_FILE,
						Context.MODE_PRIVATE);
				Bundle.write(bundle, output);
				output.close();
				saveNeeded = false;
			} catch (IOException e) {

			}
		}
	}

	public static void validateSpeedrun() {
		Badge badge = null;

		if (!local.contains(Badge.SPEEDRUN_1)
				&& Statistics.duration <= 3000f) {
			badge = Badge.SPEEDRUN_1;
			local.add(badge);
		}
		if (!local.contains(Badge.SPEEDRUN_2)
				&& Statistics.duration <= 2500f) {
			badge = Badge.SPEEDRUN_2;
			local.add(badge);
		}
		if (!local.contains(Badge.SPEEDRUN_3)
				&& Statistics.duration <= 2000f) {
			badge = Badge.SPEEDRUN_3;
			local.add(badge);
		}
		if (!local.contains(Badge.SPEEDRUN_4)
				&& Statistics.duration <= 1500f) {
			badge = Badge.SPEEDRUN_4;
			local.add(badge);
		}

		displayBadge(badge);
	}

	public static void validatePalantir() {
		if (!local.contains(Badge.PALANTIR)) {
			Badge badge = Badge.PALANTIR;
			local.add(badge);
			displayBadge(badge);
		}
	}

	public static void validateDeathFromStatue() {
		if (!local.contains(Badge.DEATH_FROM_STATUE)) {
			Badge badge = Badge.DEATH_FROM_STATUE;
			local.add(badge);
			displayBadge(badge);
		}
	}

	public static void validateCrystal1() {
		if (!local.contains(Badge.CRYSTAL_1)) {
			Badge badge = Badge.CRYSTAL_1;
			local.add(badge);
			displayBadge(badge);

			validateAllCrystals();
		}
	}

	public static void validateCrystal2() {
		if (!local.contains(Badge.CRYSTAL_2)) {
			Badge badge = Badge.CRYSTAL_2;
			local.add(badge);
			displayBadge(badge);

			validateAllCrystals();
		}
	}

	public static void validateCrystal3() {
		if (!local.contains(Badge.CRYSTAL_3)) {
			Badge badge = Badge.CRYSTAL_3;
			local.add(badge);
			displayBadge(badge);

			validateAllCrystals();
		}
	}

	public static void validateCrystal4() {
		if (!local.contains(Badge.CRYSTAL_4)) {
			Badge badge = Badge.CRYSTAL_4;
			local.add(badge);
			displayBadge(badge);

			validateAllCrystals();
		}
	}

	public static void validateCrystal5() {
		if (!local.contains(Badge.CRYSTAL_5)) {
			Badge badge = Badge.CRYSTAL_5;
			local.add(badge);
			displayBadge(badge);

			validateAllCrystals();
		}
	}

	private static void validateAllCrystals() {
		if (global.contains( Badge.CRYSTAL_1 ) &&
				global.contains( Badge.CRYSTAL_2 ) &&
				global.contains( Badge.CRYSTAL_3 ) &&
				global.contains( Badge.CRYSTAL_4 ) &&
				global.contains( Badge.CRYSTAL_5)) {

			Badge badge = Badge.ALL_CRYSTALS;
			local.add( badge );
			displayBadge( badge );
		}
	}

	public static void validateStone1() {
		if (!local.contains(Badge.STONE_1)) {
			Badge badge = Badge.STONE_1;
			local.add(badge);
			displayBadge(badge);

			validateAllStones();
		}
	}

	public static void validateStone2() {
		if (!local.contains(Badge.STONE_2)) {
			Badge badge = Badge.STONE_2;
			local.add(badge);
			displayBadge(badge);

			validateAllStones();
		}
	}

	public static void validateStone3() {
		if (!local.contains(Badge.STONE_3)) {
			Badge badge = Badge.STONE_3;
			local.add(badge);
			displayBadge(badge);

			validateAllStones();
		}
	}

	public static void validateStone4() {
		if (!local.contains(Badge.STONE_4)) {
			Badge badge = Badge.STONE_4;
			local.add(badge);
			displayBadge(badge);

			validateAllStones();
		}
	}

	public static void validateStone5() {
		if (!local.contains(Badge.STONE_5)) {
			Badge badge = Badge.STONE_5;
			local.add(badge);
			displayBadge(badge);

			validateAllStones();
		}
	}

	private static void validateAllStones() {
		if (global.contains( Badge.STONE_1 ) &&
				global.contains( Badge.STONE_2 ) &&
				global.contains( Badge.STONE_3 ) &&
				global.contains( Badge.STONE_4 ) &&
				global.contains( Badge.STONE_5)) {

			Badge badge = Badge.ALL_STONES;
			local.add( badge );
			displayBadge( badge );
		}
	}

	private static void displayBadge(Badge badge) {

		if (badge == null) {
			return;
		}

		if (global == null) {
			loadGlobal();
		}

		try {
			if (global.contains(badge)) {

				if (!badge.meta) {
					GLog.h("Badge endorsed: %s", badge.description);
				}

			} else {

				global.add(badge);
				saveNeeded = true;

				if (badge.meta) {
					GLog.h("New super badge: %s", badge.description);
				} else {
					GLog.h("New badge: %s", badge.description);
				}
				PixelScene.showBadge(badge);
			}
		} catch (NullPointerException e) {

		}
	}

	public static boolean isUnlocked(Badge badge) {
		return global.contains(badge);
	}

	public static void disown(Badge badge) {
		loadGlobal();
		global.remove(badge);
		saveNeeded = true;
	}

	public static List<Badge> filtered(boolean global) {

		HashSet<Badge> filtered = new HashSet<Badge>(global ? Badges.global
				: Badges.local);

		if (!global) {
			Iterator<Badge> iterator = filtered.iterator();
			while (iterator.hasNext()) {
				Badge badge = iterator.next();
				if (badge.meta) {
					iterator.remove();
				}
			}
		}

		leaveBest(filtered, Badge.SPEEDRUN_1, Badge.SPEEDRUN_2, Badge.SPEEDRUN_3, Badge.SPEEDRUN_4);
		leaveBest(filtered, Badge.CRYSTAL_1, Badge.ALL_CRYSTALS);
		leaveBest(filtered, Badge.CRYSTAL_2, Badge.ALL_CRYSTALS);
		leaveBest(filtered, Badge.CRYSTAL_3, Badge.ALL_CRYSTALS);
		leaveBest(filtered, Badge.CRYSTAL_4, Badge.ALL_CRYSTALS);
		leaveBest(filtered, Badge.CRYSTAL_5, Badge.ALL_CRYSTALS);
		leaveBest(filtered, Badge.STONE_1, Badge.ALL_STONES);
		leaveBest(filtered, Badge.STONE_2, Badge.ALL_STONES);
		leaveBest(filtered, Badge.STONE_3, Badge.ALL_STONES);
		leaveBest(filtered, Badge.STONE_4, Badge.ALL_STONES);
		leaveBest(filtered, Badge.STONE_5, Badge.ALL_STONES);

		ArrayList<Badge> list = new ArrayList<Badge>(filtered);
		Collections.sort(list);

		return list;
	}

	private static void leaveBest(HashSet<Badge> list, Badge... badges) {
		for (int i = badges.length - 1; i > 0; i--) {
			if (list.contains(badges[i])) {
				for (int j = 0; j < i; j++) {
					list.remove(badges[j]);
				}
				break;
			}
		}
	}
}
