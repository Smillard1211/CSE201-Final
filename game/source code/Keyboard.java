import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {

	Main game;

	// passes out main class
	public Keyboard(Main game) {
		this.game = game;
	}

	// will invoke our KeyPressed function of the same name in the main class
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}

	// will invoke our KeyReleased function of the same name in the main class
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}

}