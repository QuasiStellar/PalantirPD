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
package com.quasistellar.otiluke.sprites;

import com.quasistellar.otiluke.Assets;
import com.quasistellar.otiluke.Dungeon;
import com.quasistellar.otiluke.ResultDescriptions;
import com.quasistellar.otiluke.actors.Actor;
import com.quasistellar.otiluke.actors.Char;
import com.quasistellar.otiluke.effects.CellEmitter;
import com.quasistellar.otiluke.effects.particles.BlastParticle;
import com.quasistellar.otiluke.items.weapon.missiles.Skull;
import com.quasistellar.otiluke.levels.Level;
import com.quasistellar.otiluke.levels.Terrain;
import com.quasistellar.otiluke.scenes.GameScene;
import com.quasistellar.otiluke.utils.Utils;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class ZotNpcSprite extends MobSprite {

	public ZotNpcSprite() {
		super();

		texture(Assets.ZOT);

		TextureFilm frames = new TextureFilm(texture, 24, 18);

		idle = new Animation(2, true);
		idle.frames(frames, 0, 0, 1, 0);

		run = new Animation(8, true);
		run.frames(frames, 0);

		die = new Animation(8, false);
		die.frames(frames, 0);

		play(idle);
	}
}
