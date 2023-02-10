import java.awt.Canvas;
import java.awt.Color;
import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends Canvas implements Runnable {

	// Size of our screen
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "Gangsta's Terrabyte";

	// different states for the different levels
	public static boolean Level1 = true;
	public static boolean Level2 = false;
	public static boolean Level3 = false;
	public static boolean Level4 = false;
	public static boolean Finallevel = false;

	// variables to determine which way a player is facing. Or if
	// player is standing still
	boolean faceRight = false, faceLeft = false, faceUp = false, faceDown = false;
	boolean moveRight = false, moveLeft = false, moveUp = false, moveDown = false;
	boolean standstill = true;
	// checks if game is running
	private boolean running = false;
	private Thread thread;

	// Loads the back ground
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private static Main main = new Main();

	// a keyboard object
	public static Keyboard key = new Keyboard(main);

	// variables for all of our spritesheets
	private BufferedImage spriteSheetP = null;
	private BufferedImage spriteSheetEP = null;
	private BufferedImage spriteSheetRE = null;
	private BufferedImage spriteSheetEB = null;
	private BufferedImage spriteSheetPW = null;
	private BufferedImage spriteSheetHB = null;
	private BufferedImage spriteSheetHP = null;
	private BufferedImage spriteSheetDP = null;

	// variables for all of the frames that will display to the player
	private Menu menu;
	private LevelFrameSplice lfs;
	private StoryFrame story;
	private GuideFrame guide;
	private EndStoryFrame endStory;
	private Credits credits;

	// Player object
	public static Player p;

	// Playerweapon object
	private PlayerWeapon pw;

	// health bar object
	private HealthBar hb;

	// pack objects for damage and health
	private HealthPack hp;
	private DamagePack dp;

	// checks if player is alive set to true to start
	boolean playerAlive = true;

	// *********Enemy and Projectile Objects for all 8 enemies**********
	public static Projectile projectile1;
	public static Regular_Enemy re1;

	public static Projectile projectile2;
	public static Regular_Enemy re2;

	public static Projectile projectile3;
	public static Regular_Enemy re3;

	public static Projectile projectile4;
	public static Regular_Enemy re4;

	public static Projectile projectile5;
	public static Regular_Enemy re5;

	public static Projectile projectile6;
	public static Regular_Enemy re6;

	public static Projectile projectile7;
	public static Regular_Enemy re7;

	public static Projectile projectile8;
	public static Regular_Enemy re8;

	// boss object
	public static Boss_Enemy be;

	// detemrines if game is paused or not
	boolean pause = false;

	// sound object for music
	public static Sound themes = new Sound();

	// Method to set the states. Used to switch between the frames and the game
	public static enum STATE {
		MENU, GUIDE, STORY, ENDSTORY, GAME, LEVELSPLICE, CREDITS
	}

	// state is set to menu to start
	public static STATE state = STATE.MENU;

	/*
	 * Loads all our sprite sheets inserts out mouse and keyboard listeners
	 * initializes all our objects
	 */
	public void init() {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheetP = loader.loadImage("/updatedPlayer.png");
			spriteSheetPW = loader.loadImage("/Bullet.png");
			spriteSheetEP = loader.loadImage("/Enemy_Bullet.png");
			spriteSheetRE = loader.loadImage("/Enemy_One.png");
			spriteSheetEB = loader.loadImage("/Boss_Sprite.png");
			spriteSheetHB = loader.loadImage("/PlayerHealthBar.png");
			spriteSheetHP = loader.loadImage("/HealthPack.png");
			spriteSheetDP = loader.loadImage("/DamagePack.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
		addKeyListener(new Keyboard(this));
		addMouseListener(new MouseInput());

		// initializes the frames as a new frame obejct of the same name
		menu = new Menu();
		lfs = new LevelFrameSplice();
		story = new StoryFrame();
		endStory = new EndStoryFrame();
		guide = new GuideFrame();
		credits = new Credits();

		// Sets the health bar to the full health bar
		hb = new HealthBar(0, -35, 1, this);

		// health and damage upgrade packs
		hp = new HealthPack(570, 0, this);
		dp = new DamagePack(0, 400, this);

		// sets the player to roughly the center of the screen.
		// Sets health to 10 and damage output to 1
		// sets the player weapon to loacation of player
		p = new Player(300, 165, 300, 165, 10, 1, 2, 2, this);
		pw = new PlayerWeapon(700, 700, 10, 10, this);

		/*
		 * sets enemies to specified locations Starting with the top, then bottom, then
		 * right, then left Sets each projectiles to each enemy
		 */
		re1 = new Regular_Enemy(350, 0, 1, 2, 10, true, this);
		projectile1 = new Projectile(re1.getEnemyX(), re1.getEnemyY(), 
				                     re1.getEnemyX(), re1.getEnemyY(), this);
		
		re2 = new Regular_Enemy(350, 400, 2, 1, 10, true, this);
		projectile2 = new Projectile(re2.getEnemyX(), re2.getEnemyY(), 
				                     re2.getEnemyX(), re2.getEnemyY(), this);

		re3 = new Regular_Enemy(575, 200, 1, 1, 10, true, this);
		projectile3 = new Projectile(re3.getEnemyX(), re3.getEnemyY(), 
				                     re3.getEnemyX(), re3.getEnemyY(), this);
		
		re4 = new Regular_Enemy(0, 200, 2, 2, 10, true, this);
		projectile4 = new Projectile(re4.getEnemyX(), re4.getEnemyY(), 
				                     re4.getEnemyX(), re4.getEnemyY(), this);

		re5 = new Regular_Enemy(250, 0, 1, 2, 10, true, this);
		projectile5 = new Projectile(re5.getEnemyX(), re5.getEnemyY(), 
				                     re5.getEnemyX(), re5.getEnemyY(), this);
		
		re6 = new Regular_Enemy(250, 400, 2, 1, 10, true, this);
		projectile6 = new Projectile(re6.getEnemyX(), re6.getEnemyY(), 
				                     re6.getEnemyX(), re6.getEnemyY(), this);

		re7 = new Regular_Enemy(575, 130, 1, 1, 10, true, this);
		projectile7 = new Projectile(re7.getEnemyX(), re7.getEnemyY(), 
				                     re7.getEnemyX(), re7.getEnemyY(), this);
		
		re8 = new Regular_Enemy(0, 130, 2, 2, 10, true, this);
		projectile8 = new Projectile(re8.getEnemyX(), re8.getEnemyY(), 
				                     re8.getEnemyX(), re8.getEnemyY(), this);

		// sets the boss to the top of screen
		be = new Boss_Enemy(320, 0, 320, 0, 30, true, this);
	}

	// Checks is running
	private synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// checks is not running
	private synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(1);
	}

	/*
	 * This does most of our work The while loop will render our game and move the
	 * sprites using tick and tick direction
	 */
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		final double amountOfTics = 60.0;
		double ns = 1000000000 / amountOfTics;
		double delta = 0;
		long timer = System.currentTimeMillis();

		playThemes(6);

		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				tick();
				tickDirection(faceRight, faceLeft, faceUp, faceDown);
				delta--;
			}
			if (Level1 == true) {
				renderLevelOne();
			} else if (Level2 == true) {
				renderLevelTwo();
			} else if (Level3 == true) {
				renderLevelThree();
			} else if (Level4 == true) {
				renderLevelFour();
			} else if (Finallevel == true) {
				renderBossLevel();
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
			}
		}
		stop();
	}

	/*
	 * Renders the boss First loads the boss level frame When "proceed" is clicked
	 * the game will load After enemy is defeated we go to the endstory frame then
	 * credits and back to menu
	 */
	private void renderBossLevel() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		if (state == STATE.LEVELSPLICE) {
			lfs.render(g);
		}
		if (state == STATE.GAME) {
			renderPlayerComponents(g);
			renderBossComponents(g);
		} else if (state == STATE.ENDSTORY) {
			endStory.render(g);
		} else if (state == STATE.CREDITS) {
			credits.render(g);
		} else if (state == STATE.MENU) {
			menu.render(g);
		} else if (state == STATE.GUIDE) {
			guide.render(g);
		}
		updateHealthBar(p);
		g.dispose();
		bs.show();
	}

	/*
	 * Renders level one. Goes from the menu to the level one frame. When "prceed"
	 * is clicked We move to the game Renders first two enemies only if they are
	 * alive. When they both die level 2 is set to true and we move into the next
	 * level If the player dies we go back to the menu and the game *should reset
	 * to-do*
	 */
	public void renderLevelOne() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		if (state == STATE.STORY) {
			story.render(g);
		}
		if (state == STATE.LEVELSPLICE) {
			lfs.render(g);
		}
		if (state == STATE.GAME) {
			renderPlayerComponents(g);
			if (re1.getAlive() == true) {
				re1.render(g);
				if (projectile1.hitPrev() == false) {
					projectile1.render(g);
				}
				if (projectile1.hitPrev() == true) {
					projectile1.updatePlayerPos();
					projectile1 = new Projectile(re1.getEnemyX(), re1.getEnemyY(), 
					re1.getEnemyX(), re1.getEnemyY(),this);
				}
			}
			if (re2.getAlive() == true) {
				re2.render(g);
				if (projectile2.hitPrev() == false) {
					projectile2.render(g);
				}
				if (projectile2.hitPrev() == true) {
					projectile2.updatePlayerPos();
					projectile2 = new Projectile(re2.getEnemyX(), re2.getEnemyY(), 
					re2.getEnemyX(), re2.getEnemyY(),this);
				}
			}

			if (re1.getAlive() == false && re2.getAlive() == false) {
				dp.render(g);
				hp.render(g);

				if (dp.playerWalksOver(p) == true || hp.playerWalksOver(p) == true) {
					state = STATE.LEVELSPLICE;
					Level1 = false;
					Level2 = true;
					re2.enemyRegainFullHealth();
					re1.enemyRegainFullHealth();
					projectile2 = new Projectile(re2.getEnemyX(), re2.getEnemyY(), 
					re2.getEnemyX(), re2.getEnemyY(),this);
					projectile1 = new Projectile(re1.getEnemyX(), re1.getEnemyY(),
					re1.getEnemyX(), re1.getEnemyY(),this);
				}
			}
		}

		else if (state == STATE.MENU) {
			menu.render(g);
		} else if (state == STATE.GUIDE) {
			guide.render(g);
		}
		updateHealthBar(p);
		g.dispose();
		bs.show();
	}

	/*
	 * Renders level two. goes to the level two frame. When "proceed" is clicked We
	 * move to the game Renders first 4 enemies only if they are alive. When they
	 * all die level 3 is set to true and we move into the next level If the player
	 * dies we go back to the menu and the game *should reset to-do*
	 */
	public void renderLevelTwo() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		if (state == STATE.LEVELSPLICE) {
			lfs.render(g);
		}
		if (state == STATE.GAME) {
			renderPlayerComponents(g);

			if (re1.getAlive() == true) {

				re1.render(g);
				if (projectile1.hitPrev() == false) {
					projectile1.render(g);
				}
				if (projectile1.hitPrev() == true) {
					projectile1.updatePlayerPos();
					projectile1 = new Projectile(re1.getEnemyX(), re1.getEnemyY(), 
					re1.getEnemyX(), re1.getEnemyY(),this);
				}
			}
			if (re2.getAlive() == true) {

				re2.render(g);
				if (projectile2.hitPrev() == false) {
					projectile2.render(g);
				}
				if (projectile2.hitPrev() == true) {
					projectile2.updatePlayerPos();
					projectile2 = new Projectile(re2.getEnemyX(), re2.getEnemyY(), 
					re2.getEnemyX(), re2.getEnemyY(),this);
				}
			}

			if (re3.getAlive() == true) {

				re3.render(g);
				if (projectile3.hitPrev() == false) {
					projectile3.render(g);
				}
				if (projectile3.hitPrev() == true) {
					projectile3.updatePlayerPos();
					projectile3 = new Projectile(re3.getEnemyX(), re3.getEnemyY(), 
					re3.getEnemyX(), re3.getEnemyY(),this);
				}
			}
			if (re4.getAlive() == true) {

				re4.render(g);
				if (projectile4.hitPrev() == false) {
					projectile4.render(g);
				}
				if (projectile4.hitPrev() == true) {
					projectile4.updatePlayerPos();
					projectile4 = new Projectile(re4.getEnemyX(), re4.getEnemyY(), 
					re4.getEnemyX(), re4.getEnemyY(),this);
				}
			}
			if (re1.getAlive() == false && re2.getAlive() == false && re3.getAlive() == false
					&& re4.getAlive() == false) {
				dp.render(g);
				hp.render(g);

				if (dp.playerWalksOver(p) == true || hp.playerWalksOver(p) == true) {
					state = STATE.LEVELSPLICE;
					Level1 = false;
					Level2 = false;
					Level3 = true;
					re1.enemyRegainFullHealth();
					re2.enemyRegainFullHealth();
					re3.enemyRegainFullHealth();
					re4.enemyRegainFullHealth();
					projectile2 = new Projectile(re2.getEnemyX(), re2.getEnemyY(), 
					re2.getEnemyX(), re2.getEnemyY(),this);
					projectile1 = new Projectile(re1.getEnemyX(), re1.getEnemyY(), 
					re1.getEnemyX(), re1.getEnemyY(),this);
					projectile3 = new Projectile(re3.getEnemyX(), re3.getEnemyY(), 
					re3.getEnemyX(), re3.getEnemyY(),this);
					projectile4 = new Projectile(re4.getEnemyX(), re4.getEnemyY(), 
					re4.getEnemyX(), re4.getEnemyY(),this);
				}
			}
		} else if (state == STATE.MENU) {
			menu.render(g);
		} else if (state == STATE.GUIDE) {
			guide.render(g);
		}
		updateHealthBar(p);
		g.dispose();
		bs.show();
	}

	/*
	 * Renders level three. goes to the level three frame. When "proceed" is clicked
	 * We move to the game Renders first 6 enemies only if they are alive. When they
	 * all die level 4 is set to true and we move into the next level If the player
	 * dies we go back to the menu and the game *should reset to-do*
	 */
	public void renderLevelThree() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		if (state == STATE.LEVELSPLICE) {
			lfs.render(g);
		}
		if (state == STATE.GAME) {
			renderPlayerComponents(g);

			if (re1.getAlive() == true) {

				re1.render(g);
				if (projectile1.hitPrev() == false) {
					projectile1.render(g);
				}
				if (projectile1.hitPrev() == true) {
					projectile1.updatePlayerPos();
					projectile1 = new Projectile(re1.getEnemyX(), re1.getEnemyY(), 
					re1.getEnemyX(), re1.getEnemyY(),this);
				}
			}
			if (re2.getAlive() == true) {

				re2.render(g);
				if (projectile2.hitPrev() == false) {
					projectile2.render(g);
				}
				if (projectile2.hitPrev() == true) {
					projectile2.updatePlayerPos();
					projectile2 = new Projectile(re2.getEnemyX(), re2.getEnemyY(), 
					re2.getEnemyX(), re2.getEnemyY(),this);
				}
			}

			if (re3.getAlive() == true) {

				re3.render(g);
				if (projectile3.hitPrev() == false) {
					projectile3.render(g);
				}
				if (projectile3.hitPrev() == true) {
					projectile3.updatePlayerPos();
					projectile3 = new Projectile(re3.getEnemyX(), re3.getEnemyY(), 
				    re3.getEnemyX(), re3.getEnemyY(),this);
				}
			}
			if (re4.getAlive() == true) {

				re4.render(g);
				if (projectile4.hitPrev() == false) {
					projectile4.render(g);
				}
				if (projectile4.hitPrev() == true) {
					projectile4.updatePlayerPos();
					projectile4 = new Projectile(re4.getEnemyX(), re4.getEnemyY(), 
					re4.getEnemyX(), re4.getEnemyY(),this);
				}
			}
			if (re5.getAlive() == true) {

				re5.render(g);
				if (projectile5.hitPrev() == false) {
					projectile5.render(g);
				}
				if (projectile5.hitPrev() == true) {
					projectile5.updatePlayerPos();
					projectile5 = new Projectile(re5.getEnemyX(), re5.getEnemyY(), 
					re5.getEnemyX(), re5.getEnemyY(),this);
				}
			}
			if (re6.getAlive() == true) {

				re6.render(g);
				if (projectile6.hitPrev() == false) {
					projectile6.render(g);
				}
				if (projectile6.hitPrev() == true) {
					projectile6.updatePlayerPos();
					projectile6 = new Projectile(re6.getEnemyX(), re6.getEnemyY(), 
					re6.getEnemyX(), re6.getEnemyY(),this);
				}
			}
			if (re1.getAlive() == false && re2.getAlive() == false && re3.getAlive() == false && re4.getAlive() == false
					&& re5.getAlive() == false && re6.getAlive() == false) {
				dp.render(g);
				hp.render(g);

				if (dp.playerWalksOver(p) == true || hp.playerWalksOver(p) == true) {
					state = STATE.LEVELSPLICE;
					Level1 = false;
					Level2 = false;
					Level3 = false;
					Level4 = true;
					re1.enemyRegainFullHealth();
					re2.enemyRegainFullHealth();
					re3.enemyRegainFullHealth();
					re4.enemyRegainFullHealth();
					re5.enemyRegainFullHealth();
					re6.enemyRegainFullHealth();
					projectile2 = new Projectile(re2.getEnemyX(), re2.getEnemyY(), 
					re2.getEnemyX(), re2.getEnemyY(),this);
					projectile1 = new Projectile(re1.getEnemyX(), re1.getEnemyY(), 
					re1.getEnemyX(), re1.getEnemyY(),this);
					projectile3 = new Projectile(re3.getEnemyX(), re3.getEnemyY(), 
					re3.getEnemyX(), re3.getEnemyY(),this);
					projectile4 = new Projectile(re4.getEnemyX(), re4.getEnemyY(), 
					re4.getEnemyX(), re4.getEnemyY(),this);
					projectile5 = new Projectile(re5.getEnemyX(), re5.getEnemyY(), 
					re5.getEnemyX(), re5.getEnemyY(),this);
					projectile6 = new Projectile(re6.getEnemyX(), re6.getEnemyY(), 
					re6.getEnemyX(), re6.getEnemyY(),this);
				}
			}
		} else if (state == STATE.MENU) {
			menu.render(g);
		} else if (state == STATE.GUIDE) {
			guide.render(g);
		}
		updateHealthBar(p);
		g.dispose();
		bs.show();
	}

	/*
	 * Renders level four. goes to the level four frame. When "proceed" is clicked
	 * We move to the game Renders first 8 enemies only if they are alive. When they
	 * all die boss level is set to true and we move into the next level If the
	 * player dies we go back to the menu and the game *should reset to-do*
	 */
	public void renderLevelFour() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		if (state == STATE.LEVELSPLICE) {
			lfs.render(g);
		}
		if (state == STATE.GAME) {
			renderPlayerComponents(g);
			if (re1.getAlive() == true) {
				re1.render(g);
				if (projectile1.hitPrev() == false) {
					projectile1.render(g);
				}
				if (projectile1.hitPrev() == true) {
					projectile1.updatePlayerPos();
					projectile1 = new Projectile(re1.getEnemyX(), re1.getEnemyY(), 
					re1.getEnemyX(), re1.getEnemyY(),this);
				}
			}
			if (re2.getAlive() == true) {
				re2.render(g);
				if (projectile2.hitPrev() == false) {
					projectile2.render(g);
				}
				if (projectile2.hitPrev() == true) {
					projectile2.updatePlayerPos();
					projectile2 = new Projectile(re2.getEnemyX(), re2.getEnemyY(), 
					re2.getEnemyX(), re2.getEnemyY(),this);
				}
			}

			if (re3.getAlive() == true) {

				re3.render(g);
				if (projectile3.hitPrev() == false) {
					projectile3.render(g);
				}
				if (projectile3.hitPrev() == true) {
					projectile3.updatePlayerPos();
					projectile3 = new Projectile(re3.getEnemyX(), re3.getEnemyY(), 
					re3.getEnemyX(), re3.getEnemyY(),this);
				}
			}
			if (re4.getAlive() == true) {

				re4.render(g);
				if (projectile4.hitPrev() == false) {
					projectile4.render(g);
				}
				if (projectile4.hitPrev() == true) {
					projectile4.updatePlayerPos();
					projectile4 = new Projectile(re4.getEnemyX(), re4.getEnemyY(), 
				    re4.getEnemyX(), re4.getEnemyY(),this);
				}
			}
			if (re5.getAlive() == true) {

				re5.render(g);
				if (projectile5.hitPrev() == false) {
					projectile5.render(g);
				}
				if (projectile5.hitPrev() == true) {
					projectile5.updatePlayerPos();
					projectile5 = new Projectile(re5.getEnemyX(), re5.getEnemyY(), 
				    re5.getEnemyX(), re5.getEnemyY(),this);
				}
			}
			if (re6.getAlive() == true) {

				re6.render(g);
				if (projectile6.hitPrev() == false) {
					projectile6.render(g);
				}
				if (projectile6.hitPrev() == true) {
					projectile6.updatePlayerPos();
					projectile6 = new Projectile(re6.getEnemyX(), re6.getEnemyY(), 
				    re6.getEnemyX(), re6.getEnemyY(),this);
				}
			}
			if (re7.getAlive() == true) {

				re7.render(g);
				if (projectile7.hitPrev() == false) {
					projectile7.render(g);
				}
				if (projectile7.hitPrev() == true) {
					projectile7.updatePlayerPos();
					projectile7 = new Projectile(re7.getEnemyX(), re7.getEnemyY(), 
					re7.getEnemyX(), re7.getEnemyY(),this);
				}
			}
			if (re8.getAlive() == true) {
				re8.render(g);
				if (projectile8.hitPrev() == false) {
					projectile8.render(g);
				}
				if (projectile8.hitPrev() == true) {
					projectile8.updatePlayerPos();
					projectile8 = new Projectile(re8.getEnemyX(), re8.getEnemyY(), 
					re8.getEnemyX(), re8.getEnemyY(),this);
				}
			}
			if (re1.getAlive() == false && re2.getAlive() == false && re3.getAlive() == false
				&& re4.getAlive() == false && re5.getAlive() == false 
				&& re6.getAlive() == false && re7.getAlive() == false
				&& re8.getAlive() == false) {
				dp.render(g);
				hp.render(g);

				if (dp.playerWalksOver(p) == true || hp.playerWalksOver(p) == true) {
					state = STATE.LEVELSPLICE;
					Level1 = false;
					Level2 = false;
					Level3 = false;
					Level4 = false;
					Finallevel = true;
				}
			}
		} else if (state == STATE.MENU) {
			menu.render(g);
		} else if (state == STATE.GUIDE) {
			guide.render(g);
		}
		updateHealthBar(p);
		g.dispose();
		bs.show();
	}

	/*
	 * Method that actually moves our sprite and detects collision Between players
	 * and enemies/boss Moves sprites specific to each level Will freeze when pause
	 * is set to true by pressing the "p" key
	 */
	private void tick() {
		if (state == STATE.GAME && pause == false) {
			p.tick();
			if (Level1 == true) {
				hitEnemy(re1);
				hitEnemy(re2);
				if (re1.getAlive() == true || re2.getAlive() == true) {
					enemyHitPlayer(p, projectile2, re2);
					projectile2.tick();
					projectile1.tick();
					enemyHitPlayer(p, projectile1, re1);
				}
			}
			if (Level2 == true) {
				hitEnemy(re1);
				hitEnemy(re2);
				hitEnemy(re3);
				hitEnemy(re4);
				if (re1.getAlive() == true || re2.getAlive() == true || 
					re3.getAlive() == true || re4.getAlive() == true) {
					enemyHitPlayer(p, projectile2, re2);
					projectile2.tick();
					projectile1.tick();
					enemyHitPlayer(p, projectile1, re1);
					enemyHitPlayer(p, projectile3, re3);
					enemyHitPlayer(p, projectile4, re4);
					projectile3.tick();
					projectile4.tick();
				}
			}
			if (Level3 == true) {
				hitEnemy(re1);
				hitEnemy(re2);
				hitEnemy(re3);
				hitEnemy(re4);
				hitEnemy(re5);
				hitEnemy(re6);
				if (re1.getAlive() == true || re2.getAlive() == true || 
					re3.getAlive() == true || re4.getAlive() == true || 
					re5.getAlive() == true || re6.getAlive() == true) {
					enemyHitPlayer(p, projectile2, re2);
					projectile2.tick();
					projectile1.tick();
					enemyHitPlayer(p, projectile1, re1);
					enemyHitPlayer(p, projectile3, re3);
					enemyHitPlayer(p, projectile4, re4);
					enemyHitPlayer(p, projectile5, re5);
					enemyHitPlayer(p, projectile6, re6);
					projectile3.tick();
					projectile4.tick();
					projectile5.tick();
					projectile6.tick();
				}
			}
			if (Level4 == true) {
				hitEnemy(re1);
				hitEnemy(re2);
				hitEnemy(re3);
				hitEnemy(re4);
				hitEnemy(re5);
				hitEnemy(re6);
				hitEnemy(re7);
				hitEnemy(re8);
				if (re1.getAlive() == true || re2.getAlive() == true || 
					re3.getAlive() == true || re4.getAlive() == true || 
					re5.getAlive() == true || re6.getAlive() == true || 
					re7.getAlive() == true || re8.getAlive() == true) {
					enemyHitPlayer(p, projectile2, re2);
					projectile2.tick();
					projectile1.tick();
					enemyHitPlayer(p, projectile1, re1);
					enemyHitPlayer(p, projectile3, re3);
					enemyHitPlayer(p, projectile4, re4);
					enemyHitPlayer(p, projectile5, re5);
					enemyHitPlayer(p, projectile6, re6);
					enemyHitPlayer(p, projectile7, re7);
					enemyHitPlayer(p, projectile8, re8);
					projectile3.tick();
					projectile4.tick();
					projectile5.tick();
					projectile6.tick();
					projectile7.tick();
					projectile8.tick();
				}
			}
			if (Finallevel == true) {
				be.tick();
				hitBoss(be);
				bossHitsPlayer(be);
			}
		}
	}

	/*
	 * Determines if enemy has been hit by the player sets enemies boolean alive
	 * variable to false if health gets to 0
	 */
	private void hitEnemy(Regular_Enemy re) {
		if (re.enemyIsHit(p) == true && re.getAlive() == true) {
			playOnce(0);
			standstill = true;
			pw = new PlayerWeapon(700, 700, 10, 10, this);
			if (re.getEnemyCurrentHealth() <= 0) {
				EnemyDeathSound();
				re.setAlive(false);
			}
		}
	}

	/*
	 * THIS METHOD ALLOWS FOR THE PLAYER WEAPON TO STICK TO THE PLAYER AND MOVE IN
	 * THE DIRECTION THE PLAYER IS FACING
	 */
	private void tickDirection(boolean facingRight, boolean facingLeft, 
	                           boolean facingUp, boolean facingDown) {
		if (state == STATE.GAME && pause == false) {
			if (standstill == false && facingRight == true && facingLeft == false && 
				facingUp == false && facingDown == false) {
				if (pw.isMoving() == true && moveLeft == false && moveUp == false && moveDown == false) {
					moveRight = true;
				} else {
					moveRight = false;
				}
				if (moveLeft == true && moveRight == false && moveDown == false && moveUp == false) {
					pw.moveLeft();
				} else if (moveLeft == false && moveRight == true && moveDown == false && moveUp == false) {
					pw.moveRight();
				} else if (moveLeft == false && moveRight == false && moveDown == true && moveUp == false) {
					pw.moveDown();
				} else if (moveLeft == false && moveRight == false && moveDown == false && moveUp == true) {
					pw.moveUp();
				} else if (facingRight == true && moveLeft == false && moveRight == false && moveDown == false
						&& moveUp == false) {
					pw.moveRight();
				}
			}

			else if (standstill == false && facingRight == false && facingLeft == true && facingUp == false
					&& facingDown == false) {
				if (pw.isMoving() == true && moveRight == false && moveUp == false && moveDown == false) {
					moveLeft = true;
				} else {
					moveLeft = false;
				}
				if (moveLeft == true && moveRight == false && moveDown == false && moveUp == false) {
					pw.moveLeft();
				} else if (moveLeft == false && moveRight == true && moveDown == false && moveUp == false) {
					pw.moveRight();
				} else if (moveLeft == false && moveRight == false && moveDown == true && moveUp == false) {
					pw.moveDown();
				} else if (moveLeft == false && moveRight == false && moveDown == false && moveUp == true) {
					pw.moveUp();
				} else if (facingLeft == true && moveLeft == false && moveRight == false && moveDown == false
						&& moveUp == false) {
					pw.moveLeft();
				}
			}

			else if (standstill == false && facingRight == false && facingLeft == false && facingUp == true
					&& facingDown == false) {
				if (pw.isMoving() == true && moveRight == false && moveLeft == false && moveDown == false) {
					moveUp = true;
				} else {
					moveUp = false;
				}
				if (moveLeft == true && moveRight == false && moveDown == false && moveUp == false) {
					pw.moveLeft();
				} else if (moveLeft == false && moveRight == true && moveDown == false && moveUp == false) {
					pw.moveRight();
				} else if (moveLeft == false && moveRight == false && moveDown == true && moveUp == false) {
					pw.moveDown();
				} else if (moveLeft == false && moveRight == false && moveDown == false && moveUp == true) {
					pw.moveUp();
				} else if (facingUp == true && moveLeft == false && moveRight == false && moveDown == false
						&& moveUp == false) {
					pw.moveUp();
				}
			}

			else if (standstill == false && facingRight == false && facingLeft == false && facingUp == false
					&& facingDown == true) {
				if (pw.isMoving() == true && moveRight == false && moveUp == false && moveLeft == false) {
					moveDown = true;
				} else {
					moveDown = false;
				}
				if (moveLeft == true && moveRight == false && moveDown == false && moveUp == false) {
					pw.moveLeft();
				} else if (moveLeft == false && moveRight == true && moveDown == false && moveUp == false) {
					pw.moveRight();
				} else if (moveLeft == false && moveRight == false && moveDown == true && moveUp == false) {
					pw.moveDown();
				} else if (moveLeft == false && moveRight == false && moveDown == false && moveUp == true) {
					pw.moveUp();
				} else if (facingDown == true && moveLeft == false && moveRight == false && moveDown == false
						&& moveUp == false) {
					pw.moveDown();
				}
			}
		}
	}

	/*
	 * Method used to render player components in levels
	 */
	public void renderPlayerComponents(Graphics gr) {
		p.render(gr);
		hb.render(gr);
		pw.render(gr);
	}

	/*
	 * Method used to render our boss components in the boss level
	 */
	public void renderBossComponents(Graphics gr) {
		if (be.getAlive() == true) {
			be.render(gr);
			if (be.hitPrev() == false) {
				be.render(gr);
			}
			bossPlayerInteraction();
		}
	}

	/*
	 * Checks if boss and player interact
	 */
	public void bossPlayerInteraction() {
		if (be.hitPrev() == true) {
			double randX = Math.random() * WIDTH;
			double randY = Math.random() * HEIGHT;
			be.updatePlayerPos(p);
			be = new Boss_Enemy(randX, randY, randX, randY, be.getBossCurrentHealth(), 
					            be.getAlive(), this);
		}
	}

	/*
	 * Shortens length of health bar as the players health drops
	 */
	public void updateHealthBar(Player pl) {
		if (pl.getCurrent() == 10) {
			hb = new HealthBar(0, -35, 1, this);
		}
		if (pl.getCurrent() == 9) {
			hb = new HealthBar(0, -35, 2, this);
		}
		if (pl.getCurrent() == 8) {
			hb = new HealthBar(0, -35, 3, this);
		}
		if (pl.getCurrent() == 7) {
			hb = new HealthBar(0, -35, 4, this);
		}
		if (pl.getCurrent() == 6) {
			hb = new HealthBar(0, -35, 5, this);
		}
		if (pl.getCurrent() == 5) {
			hb = new HealthBar(0, -35, 6, this);
		}
		if (pl.getCurrent() == 4) {
			hb = new HealthBar(0, -35, 7, this);
		}
		if (pl.getCurrent() == 3) {
			hb = new HealthBar(0, -35, 8, this);
		}
		if (pl.getCurrent() == 2) {
			hb = new HealthBar(0, -35, 9, this);
		}
		if (pl.getCurrent() == 1) {
			hb = new HealthBar(0, -35, 10, this);
		}
		if (pl.getCurrent() == 0) {
			hb = new HealthBar(0, -35, 11, this);
		}
	}

	/*
	 * Checks if boss is hit. Sets alive value to false if health is 0
	 */
	private void hitBoss(Boss_Enemy b) {
		if (b.bossIsHit(p) == true) {
			pw = new PlayerWeapon(700, 700, 10, 10, this);
			playOnce(7);
			if (b.getBossCurrentHealth() <= 0) {
				b.setAlive(false);
				playOnce(2);
				state = STATE.ENDSTORY;
			}
		}
	}

	/*
	 * Checks if player is hit by regular_enemy Goes to menu if health of player 0
	 * plays player death theme when health is 0
	 */
	private void enemyHitPlayer(Player p, Projectile po, Regular_Enemy re) {
		if (p.playerIsHit(po, re) == true) {
			if (p.getCurrent() == 0) {
				playOnce(3);
				playerAlive = false;
				state = STATE.MENU;
			}
		}
	}

	/*
	 * Checks if player is hit by regular_enemy Goes to menu if health of player 0
	 * plays player death theme when health is 0
	 */
	private void bossHitsPlayer(Boss_Enemy b) {
		if (p.playerIsHitByBoss(b) == true) {
			if (p.getCurrent() == 0) {
				playOnce(3);
				playerAlive = false;
				Level1 = true;
				Level2 = false;
				Level3 = false;
				Level4 = false;
				Finallevel = false;
				state = STATE.MENU;
			}
		}
	}

	/*
	 * Checks if key is pressed. Passes value through keyboard class
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (state == STATE.GAME && pause == false) {
			if (key == KeyEvent.VK_RIGHT) {
				faceRight = true;
				faceLeft = false;
				faceUp = false;
				faceDown = false;
				p = new Player(300, 265, p.getX(), p.getY(), p.getCurrent(), 
						       p.getDamage(), 1, 2, this);
				p.setVelX(5);
			} else if (key == KeyEvent.VK_LEFT) {
				faceRight = false;
				faceLeft = true;
				faceUp = false;
				faceDown = false;
				p = new Player(300, 265, p.getX(), p.getY(), p.getCurrent(), 
						       p.getDamage(), 2, 1, this);
				p.setVelX(-5);
			} else if (key == KeyEvent.VK_UP) {
				faceRight = false;
				faceLeft = false;
				faceUp = true;
				faceDown = false;
				p = new Player(300, 265, p.getX(), p.getY(), p.getCurrent(), 
						       p.getDamage(), 1, 1, this);
				p.setVelY(-5);
			} else if (key == KeyEvent.VK_DOWN) {
				faceRight = false;
				faceLeft = false;
				faceUp = false;
				faceDown = true;
				p = new Player(300, 265, p.getX(), p.getY(), p.getCurrent(), 
						       p.getDamage(), 2, 2, this);
				p.setVelY(5);
			} else if (key == KeyEvent.VK_SPACE) {
				playOnce(4);
				standstill = false;
				if (faceRight == true && (pw.outOfBounds() == true || 
					pw.hitEnemy(re1) == true)) {
					moveLeft = false;
					moveRight = false;
					moveUp = false;
					moveDown = false;
					pw = new PlayerWeapon(p.getX(), p.getY() + 25, 10, 10, this);
				} else if (faceLeft == true && (pw.outOfBounds() == true || 
						   pw.hitEnemy(re1) == true)) {
					moveLeft = false;
					moveRight = false;
					moveUp = false;
					moveDown = false;
					pw = new PlayerWeapon(p.getX(), p.getY() + 5, 10, 10, this);
				} else if (faceUp == true && (pw.outOfBounds() == true || 
						   pw.hitEnemy(re1) == true)) {
					moveLeft = false;
					moveRight = false;
					moveUp = false;
					moveDown = false;
					pw = new PlayerWeapon(p.getX() + 25, p.getY(), 10, 10, this);
				} else if (faceDown == true && (pw.outOfBounds() == true || 
						   pw.hitEnemy(re1) == true)) {
					moveLeft = false;
					moveRight = false;
					moveUp = false;
					moveDown = false;
					pw = new PlayerWeapon(p.getX() + 8, p.getY(), 10, 10, this);
				}
			}
		}
		if (key == KeyEvent.VK_P) {
			if (pause == false) {
				pause = true;
			} else if (pause == true) {
				pause = false;
			}
		}

	}

	/*
	 * Checks if key is released. Passes value through keyboard class
	 */
	public void keyReleased(KeyEvent e) {
		if (state == STATE.LEVELSPLICE || state == STATE.GAME || state == STATE.MENU) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_RIGHT) {
				p.setVelX(0);
			} else if (key == KeyEvent.VK_LEFT) {
				p.setVelX(0);
			} else if (key == KeyEvent.VK_UP) {
				p.setVelY(0);
			} else if (key == KeyEvent.VK_DOWN) {
				p.setVelY(0);
			} else if (key == KeyEvent.VK_SPACE) {
			}
		}
	}

	/*
	 * Plays theme from the specified index of the array in sound
	 */
	public void playThemes(int m) {
		themes.setFile(m);
		themes.startSound();
		themes.loopSound();
	}

	/*
	 * Stops all themes
	 */
	public void stopThemes(int m) {
		themes.setFile(m);
		themes.stopSound();
	}

	/*
	 * Plays theme from the specified index of the array in sound only once
	 */
	public static void playOnce(int m) {
		themes.setFile(m);
		themes.startSound();
	}

	/*
	 * Plays enemy death sound
	 */
	public void EnemyDeathSound() {
		playOnce(1);
	}

	/*
	 * Main method that actually runs the game
	 */
	public static void main(String[] args) {
		Main game = new Main();
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(game.TITLE);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}

// these allow us to get the sprite sheets of objects in their classes
	public BufferedImage getSpriteSheetP() {
		return spriteSheetP;
	}

	public BufferedImage getSpriteSheetPW() {
		return spriteSheetPW;
	}

	public BufferedImage getSpriteSheetE() {
		return spriteSheetEP;
	}

	public BufferedImage getSpriteSheetRE() {
		return spriteSheetRE;
	}

	public BufferedImage getSpriteSheetEB() {
		return spriteSheetEB;
	}

	public BufferedImage getSpriteSheetHB() {
		return spriteSheetHB;
	}

	public BufferedImage getSpriteSheetHP() {
		return spriteSheetHP;
	}

	public BufferedImage getSpriteSheetDP() {
		return spriteSheetDP;
	}

}
