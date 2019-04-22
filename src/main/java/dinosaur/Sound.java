package dinosaur;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	public static void loadSoundFiles() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		clip = AudioSystem.getClip();
		inputStream = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream("/jump-sound.wav"));
		clip.open(inputStream);
	}

	private static Clip clip;
	private static AudioInputStream inputStream;

	public static void makeJumpSound() throws LineUnavailableException, IOException, InterruptedException {
		clip.start();
		clip.setFramePosition(0);
	}

}
