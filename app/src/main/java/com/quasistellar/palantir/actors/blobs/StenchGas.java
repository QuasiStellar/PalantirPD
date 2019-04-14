package com.quasistellar.palantir.actors.blobs;

import com.quasistellar.palantir.actors.Actor;
import com.quasistellar.palantir.actors.Char;
import com.quasistellar.palantir.actors.buffs.Buff;
import com.quasistellar.palantir.actors.buffs.Paralysis;
import com.quasistellar.palantir.effects.BlobEmitter;
import com.quasistellar.palantir.effects.Speck;

/**
 * Created by debenhame on 08/10/2014.
 */
public class StenchGas extends Blob {

	@Override
	protected void evolve() {
		super.evolve();

		Char ch;
		for (int i = 0; i < LENGTH; i++) {
			if (cur[i] > 0 && (ch = Actor.findChar(i)) != null) {
				if (!ch.immunities().contains(this.getClass()))
					Buff.prolong(ch, Paralysis.class,
							Paralysis.duration(ch) / 5);
			}
		}
	}

	@Override
	public void use(BlobEmitter emitter) {
		super.use(emitter);

		emitter.pour(Speck.factory(Speck.STENCH), 0.6f);
	}

	@Override
	public String tileDesc() {
		return "A cloud of fetid stench is swirling here.";
	}
}
