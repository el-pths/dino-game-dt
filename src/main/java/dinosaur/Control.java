package dinosaur;

import java.awt.Graphics;

import javax.swing.JFrame;

public class Control {

	public static boolean pause = false;
	public static State state = State.STARTMENU_SETTING;
	public static double recordingStep = 7.5;

	public static enum State {
		PRE_START_GAME, GAMMING_PROCESS, SETTINGS_MENU, PAUSE, START_MENU, GAME_OVER, STARTMENU_SETTING,
		GAME_OVER_SETTING;
	}

	public static void main(String[] args) {
		DImage.loadAllImagies();
		Dino.loadDinoImagies();
		Field.loadFieldPartsImagies();
		Cactuses.loadCactusesImagies();
		Clouds.loadCloudImage();
		startNewGame();
		Window.setWindowAndStartScript("First");
	}

	public static void setModeDependingConditions(JFrame window, Graphics graphics, double position) {
		switch (state) {
		case PRE_START_GAME:
			Countdown.drawCountdown(graphics);
			break;
		case GAMMING_PROCESS:
			GamingProcess.draw(graphics);
			if (!Dino.dino.isJumpMeaningStartingGameFinished) {
				Dino.dino.record(position);
			} else
				GamingProcess.checkAndRecord(position, window.getWidth(), window.getHeight());
			break;
		case SETTINGS_MENU:
			break;
		case PAUSE:
			break;
		case GAME_OVER_SETTING:
			GameOver.setRestartButton(window);
			state = State.GAME_OVER;
			break;
		case GAME_OVER:
			GameOver.draw(graphics);
			GameOver.correctButtonsTouchableSpace(window.getWidth(), window.getHeight());
			break;
		case STARTMENU_SETTING:
			StartMenu.set(window);
			state = State.START_MENU;
			break;
		case START_MENU:
			StartMenu.draw(graphics);
			StartMenu.correctAndRecord(window.getWidth(), window.getHeight(), position);
			break;
		}
	}

	public static void startNewGame() {
		Dino.setDino("my", 7, 134, 340, 4.45);
		Field.setField(70, 21, Dino.dino.height + Dino.dino.verticalIndent - 40, 54);
		Cactuses.setCactuses();
		Clouds.setClouds();
	}

}
