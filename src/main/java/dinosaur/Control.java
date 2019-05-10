package dinosaur;

import java.awt.Graphics;

import javax.swing.JFrame;

public class Control {

	public static boolean pause = false;
	public static State state = State.START_MENU;
	public static double recordingStep = 7.5;

	public static enum State {
		PRE_START_GAME, GAMMING_PROCESS, SETTINGS_MENU, PAUSE, START_MENU, GAME_OVER;
	}

	public static void main(String[] args) {
		Dino.loadDinoImagies();
		Dino.setDino("my", 7, 134, 340, 4.45);
		Field.loadFieldPartsImagies();
		Field.setField(70, 21, Dino.dino.height + Dino.dino.verticalIndent - 40, 54);
		Cactuses.loadCactusesImagies();
		Cactuses.setCactuses();
		Clouds.loadCloudImage();
		Clouds.setClouds();
		Window.setWindowAndStartScript("First");
	}

	public static void setModeDependingConditions(JFrame window, Graphics graphics, double position) {
		if (state == State.PRE_START_GAME) {
		} else if (state == State.GAMMING_PROCESS) {
		} else if (state == State.SETTINGS_MENU) {
		} else if (state == State.PAUSE) {
		} else if (state == State.GAME_OVER) {
		} else {
			if (!StartMenu.isMenuSetted)
				StartMenu.set(window);
			StartMenu.drawStartMenu(graphics);
			StartMenu.correctAndRecord(graphics, window.getWidth(), window.getHeight(), position);
		}
	}

	public static void startNewGame() {

	}

}
