package com.quasistellar.otiluke.actors.buffs;

import java.util.ArrayList;
import java.util.Collection;

import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.items.Item;
import com.quasistellar.otiluke.items.weapon.missiles.MissileWeapon;
import com.watabou.utils.Bundle;

/**
 * Created by debenhame on 06/02/2015.
 */
public class PinCushion extends Buff {

	private ArrayList<MissileWeapon> items = new ArrayList<MissileWeapon>();

	public void stick(MissileWeapon item) {
		items.add(item);
	}

	@Override
	public void detach() {
		for (Item item : items)
			Dungeon.level.drop(item, target.pos).sprite.drop();
		super.detach();
	}

	private static final String ITEMS = "items";

	@Override
	public void storeInBundle(Bundle bundle) {
		bundle.put(ITEMS, items);
		super.storeInBundle(bundle);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		items = new ArrayList<MissileWeapon>(
				(Collection<MissileWeapon>) ((Collection<?>) bundle
						.getCollection(ITEMS)));
		super.restoreFromBundle(bundle);
	}

}
