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
package com.quasistellar.otiluke.items.potions;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.actors.buffs.Buff;
import com.quasistellar.otiluke.actors.buffs.Invisibility;
import com.quasistellar.otiluke.actors.hero.Hero;
import com.quasistellar.otiluke.items.misc.AutoPotion.AutoHealPotion;
import com.quasistellar.otiluke.utils.GLog;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.tweeners.AlphaTweener;

public class PotionOfInvisibility extends Potion {

	private static final float ALPHA = 0.4f;

	{
		name = "Potion of Invisibility";
	}

	private static final String TXT_PREVENTING = "Enemies on this level are all blind. No point using invisibility.";
	
	@Override
	public void apply(Hero hero) {
		setKnown();
		Buff.affect(hero, Invisibility.class,  Dungeon.hero.buff(AutoHealPotion.class) != null ? Invisibility.DURATION*2 : Invisibility.DURATION);
		GLog.i("You see your hands turn invisible!");
		Sample.INSTANCE.play(Assets.SND_MELD);
	}

	@Override
	public String desc() {
		return "Drinking this potion will render you temporarily invisible. While invisible, "
				+ "enemies will be unable to see you. Attacking an enemy, as well as using a wand or a scroll "
				+ "before enemy's eyes, will dispel the effect.";
	}
	
	@Override
	public void execute(final Hero hero, String action) {
		if (action.equals(AC_DRINK)) {
			
		  if (Dungeon.depth==29) {
				GLog.w(TXT_PREVENTING);
				return;
		
		   } 
		}
		
	   super.execute(hero, action);
		 	
	}

	@Override
	public int price() {
		return isKnown() ? 40 * quantity : super.price();
	}

	public static void melt(Char ch) {
		if (ch.sprite.parent != null) {
			ch.sprite.parent.add(new AlphaTweener(ch.sprite, ALPHA, 0.4f));
		} else {
			ch.sprite.alpha(ALPHA);
		}
	}
}
