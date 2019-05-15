package dinosaur;

import java.awt.Graphics;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Control {

	public static State state = State.SETTING_S_M;
	public static double recordingStep = 7.5;

	public static enum State {
		SETTING_S_M, START_MENU, PRE_START_GAME, SETTING_G_P, GAMMING_PROCESS, SETTING_ST_M, SETTINGS_MENU, SETTING_P_M,
		PAUSE_MENU, SETTING_GO_M, GAME_OVER, SETTING_CH, SETTING_CHART, CHART;
	}

	public static void main(String[] args) {
		DImage.loadAllImagies();
		try {
			Sound.loadSoundFiles();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		Dino.loadDinoImagies();
		Field.loadFieldPartsImagies();
		Cactuses.loadCactusesImagies();
		Clouds.loadCloudImage();
		startNewGame();
		Window.setWindowAndStartScript("First");
	}

	public static void setModeDependingConditions(Window window, Graphics graphics, double position) {
		switch (state) {
		case SETTING_S_M:
			StartMenu.set(window);
			state = State.START_MENU;
			Window.setFullPassTime(60.0);
			break;
		case START_MENU:
			StartMenu.correctAndRecord(window.getWidth(), window.getHeight(), position);
			StartMenu.draw(graphics);
			break;
		case PRE_START_GAME:
			Countdown.drawCountdown(graphics);
			break;
		case SETTING_G_P:
			Window.setFullPassTime(22.0);
			GamingProcess.setPauseButton(window);
			state = State.GAMMING_PROCESS;
			break;
		case GAMMING_PROCESS:
			GamingProcess.draw(graphics);
			if (!Dino.dino.isJumpMeaningStartingGameFinished) {
				Dino.dino.record(position);
			} else
				GamingProcess.checkAndRecord(position, window.getWidth(), window.getHeight());
			break;
		case SETTING_ST_M:
			SettingsMenu.setButtons(window);
			state = State.SETTINGS_MENU;
			Window.setFullPassTime(25.0);
			break;
		case SETTINGS_MENU:
			SettingsMenu.draw(graphics);
			SettingsMenu.correctButtonsTouchableSpace(window.getWidth(), window.getHeight());
			SettingsMenu.recordPresentableDino(position);
			break;
		case SETTING_P_M:
			Window.setSmoothBlurReady();
			Pause.setPauseButtons(window);
			state = State.PAUSE_MENU;
			break;
		case PAUSE_MENU:
			Pause.draw(graphics);
			Pause.correctButtonsTouchableSpace(window.getWidth(), window.getHeight());
			break;
		case SETTING_GO_M:
			Window.setSmoothBlurReady();
			GameOver.setRestartButton(window);
			state = State.GAME_OVER;
			break;
		case GAME_OVER:
			GameOver.draw(graphics);
			GameOver.correctButtonsTouchableSpace(window.getWidth(), window.getHeight());
			break;
		case SETTING_CHART:
			Chart.set(window);
			state = State.CHART;
			break;
		case CHART:
			Chart.draw(graphics);
			Chart.correctButtonsTouchableSpace(window.getWidth(), window.getHeight());
			if (Port.app != null && Port.app.areWeReadingFile)
				Chart.readFromFile();
			if (!DataInterpretation.isNormalGravityValueSetted())
				Analisys.setGravityValueIfSELets(Filter.filter);
		default:
			break;
		}
	}

	public static void startNewGame() {
		Dino.setDino("my", 7, 134, 340);
		Field.setField(70, 21, Dino.dino.height + Dino.dino.verticalIndent - 40, 54);
		Cactuses.setCactuses();
		Clouds.setClouds();
		Pteros.setPteros();
	}

}
