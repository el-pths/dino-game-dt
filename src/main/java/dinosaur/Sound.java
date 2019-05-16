package dinosaur;

import java.io.IOException;
import java.io.BufferedInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	public static boolean isSettedOn = true;

	private static Clip clip;
	private static AudioInputStream inputStream;

	public static void loadSoundFiles() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		clip = AudioSystem.getClip();
		inputStream = AudioSystem.getAudioInputStream(
			new BufferedInputStream(Sound.class.getResourceAsStream("/jump-sound.wav")));
		clip.open(inputStream);
	}

	public static void makeJumpSound() throws LineUnavailableException, IOException, InterruptedException {
		clip.start();
		clip.setFramePosition(0);
	}

}
