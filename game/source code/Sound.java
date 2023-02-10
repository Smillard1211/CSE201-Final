import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	Clip noise;
	URL songs[] = new URL[20];

	// Puts all the themes into an array
	public Sound() {
		songs[0] = getClass().getResource("/EnemyHit.wav");
		songs[1] = getClass().getResource("/EnemyDeath.wav");
		songs[2] = getClass().getResource("/BossDeath.wav");
		songs[3] = getClass().getResource("/PlayerDeath.wav");
		songs[4] = getClass().getResource("/PlayerFire.wav");
		songs[5] = getClass().getResource("/PackDrop.wav");
		songs[6] = getClass().getResource("/TheMainTheme.wav");
		songs[7] = getClass().getResource("/BossHit.wav");
	}

	// sets the index of the file
	public void setFile(int fileIndex) {
		try {
			AudioInputStream au = AudioSystem.getAudioInputStream(songs[fileIndex]);
			noise = AudioSystem.getClip();
			noise.open(au);
		} catch (Exception e) {

		}
	}

	// starts the sound
	public void startSound() {
		noise.start();
	}

	// stops the sound
	public void stopSound() {
		noise.stop();
	}

	// loops the sound
	public void loopSound() {
		noise.loop(noise.LOOP_CONTINUOUSLY);
	}
}
