package dinosaur;

import java.awt.Graphics;

import javax.swing.JFrame;

public class Control {

	public static State state = State.START_MENU;

	public static enum State {
		PRE_START_GAME, GAMMING_PROCESS, SETTINGS_MENU, PAUSE, START_MENU, GAME_OVER;
	}

	public static void main(String[] args) {
		Dino.dino = new Dino("my");
		Field.field = new Field();
		Window.setWindowAndStartScript("First");
	}

	public static void setModeDependingConditions(JFrame window, Graphics graphics, double position) {
		if (state == State.PRE_START_GAME) {
		} else if (state == State.GAMMING_PROCESS) {
		} else if (state == State.SETTINGS_MENU) {
		} else if (state == State.PAUSE) {
		} else if (state == State.GAME_OVER) {
		} else {
			StartMenu.showStartMenu(graphics, window, position);
		}
	}

	public static void startNewGame() {
		
	}
	
}
