package com.quasistellar.palantir.plants;

import com.quasistellar.palantir.Dungeon;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.items.food.Blandfruit;
import com.quasistellar.palantir.sprites.ItemSpriteSheet;

/**
 * Created by Evan on 13/08/2014.
 */
public class BlandfruitBush extends Plant {

	private static final String TXT_DESC = "Distant cousin of the Rotberry, the pear-shaped produce of the Blandfruit bush tastes like caked dust. "
			+ "The fruit is gross and unsubstantial but isn't poisonous. perhaps it could be cooked.";

	{
		image = 8;
		plantName = "Blandfruit";
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);

		Dungeon.level.drop(new Blandfruit(), pos).sprite.drop();
	}

	@Override
	public String desc() {
		return TXT_DESC;
	}

	public static class Seed extends Plant.Seed {
		{
			plantName = "Blandfruit";

			name = "seed of " + plantName;
			image = ItemSpriteSheet.SEED_BLANDFRUIT;

			plantClass = BlandfruitBush.class;
			alchemyClass = null;
		}

		@Override
		public String desc() {
			return TXT_DESC;
		}
	}
}