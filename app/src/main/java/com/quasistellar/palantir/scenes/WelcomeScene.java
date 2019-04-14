package com.quasistellar.palantir.scenes;

import com.quasistellar.palantir.Badges;
import com.quasistellar.palantir.Chrome;
import com.quasistellar.palantir.Palantir;
import com.quasistellar.palantir.Rankings;
import com.quasistellar.palantir.ui.Archs;
import com.quasistellar.palantir.ui.RedButton;
import com.quasistellar.palantir.ui.ScrollPane;
import com.quasistellar.palantir.ui.Window;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Component;

//TODO: update this class with relevant info as new versions come out.
public class WelcomeScene extends PixelScene {

	private static final String TTL_Welcome = "Welcome!";

	private static final String TTL_Update = "Hello! New version has released!";

	private static final String TTL_Future = "Wait What?";

	private static final String TXT_Welcome = "Palantir\n\n\n"
			+"This is a sokoban puzzle game based on sokoban levels from Sprouted Pixel Dungeon by dachhack."
			+"\n\n\n"

			+"Main Features:\n\n"
			+"\u007F Restart system\n"
			+"\u007F A lot of gameplay improvements\n"
			+"\u007F Stones and crystals scattered around the castle\n"
			+"\u007F Detailed descriptions and tutorial\n"
			+"\u007F Direction signs for black sheep and portal switchers\n"
			+"\u007F Improved black sheep target selection system\n"
			+"\u007F A great number of bug fixes"
			+"\n\n";
			
			

	private static final String TXT_Update = "Version 0.3\n\n"
			+"Palantir has been updated!\n\n"
			+"In this release:\n"
			+"\u007F New level!\n"
			+"\u007F AI improvements\n"
			+"\u007F Minor changes and bug fixes";

	private static final String TXT_Future = "It seems that your current saves are from a future version of Palantir!\n\n"
			+ "Either you're messing around with older versions of the app, or something has gone buggy.\n\n"
			+ "Regardless, tread with caution! Your saves may contain things which don't exist in this version, "
			+ "this could cause some very weird errors to occur.";

	private static final String LNK = "https://play.google.com/store/apps/details?id=com.github.dachhack.sprout";

	@Override
	public void create() {
		super.create();

		final int gameversion = Palantir.version();

		BitmapTextMultiline title;
		BitmapTextMultiline text;

		if (gameversion == 0) {

			text = createMultiline(TXT_Welcome, 8);
			title = createMultiline(TTL_Welcome, 16);

		} else if (gameversion <= Game.versionCode) {

			text = createMultiline(TXT_Update, 6);
			title = createMultiline(TTL_Update, 9);

		} else {

			text = createMultiline(TXT_Future, 8);
			title = createMultiline(TTL_Future, 16);

		}

		int w = Camera.main.width;
		int h = Camera.main.height;

		int pw = w - 10;
		int ph = h - 50;

		title.maxWidth = pw;
		title.measure();
		title.hardlight(Window.SHPX_COLOR);

		title.x = align((w - title.width()) / 2);
		title.y = align(8);
		add(title);

		NinePatch panel = Chrome.get(Chrome.Type.WINDOW);
		panel.size(pw, ph);
		panel.x = (w - pw) / 2;
		panel.y = (h - ph) / 2;
		add(panel);

		ScrollPane list = new ScrollPane(new Component());
		add(list);
		list.setRect(panel.x + panel.marginLeft(), panel.y + panel.marginTop(),
				panel.innerWidth(), panel.innerHeight());
		list.scrollTo(0, 0);

		Component content = list.content();
		content.clear();

		text.maxWidth = (int) panel.innerWidth();
		text.measure();

		content.add(text);

		content.setSize(panel.innerWidth(), text.height());

		RedButton okay = new RedButton("Okay!") {
			@Override
			protected void onClick() {

				if (gameversion <= 32) {
					// removes all bags bought badge from pre-0.2.4 saves.
					Badges.saveGlobal();

					// imports new ranking data for pre-0.2.3 saves.
					if (gameversion <= 29) {
						Rankings.INSTANCE.load();
						Rankings.INSTANCE.save();
					}
				}

				Palantir.version(Game.versionCode);
				Game.switchScene(TitleScene.class);
			}
		};

		/*
		 * okay.setRect(text.x, text.y + text.height() + 5, 55, 18); add(okay);
		 * 
		 * RedButton changes = new RedButton("Changes") {
		 * 
		 * @Override protected void onClick() { parent.add(new WndChanges()); }
		 * };
		 * 
		 * changes.setRect(text.x + 65, text.y + text.height() + 5, 55, 18);
		 * add(changes);
		 */

		okay.setRect((w - pw) / 2, h - 22, pw, 18);
		add(okay);

		Archs archs = new Archs();
		archs.setSize(Camera.main.width, Camera.main.height);
		addToBack(archs);

		fadeIn();
	}
}
