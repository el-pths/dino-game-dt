package dinosaur;

import java.awt.Graphics;

import dinosaur.DButton.buttonPurpose;

public class StartMenu {

	public static boolean isMenuSetted = false;

	public static void set(Window window) {
		DButton.setButton(window, 200, 200, DImage.loadImage("/start4.png"), DButton.buttonPurpose.START_GAME);
		DButton.setButton(window, 30, 30, 80, 80, DImage.settingsButtonImg, buttonPurpose.GET_SETTINGS_MENU);
		SettingsMenu.fromWhat = Control.state;
		isMenuSetted = true;
	}

	public static void draw(Graphics graphics) {
		Background.draw(graphics);
		Field.field.draw(graphics);
		Clouds.clouds.draw(graphics);
		Cactuses.cactuses.draw(graphics);
		Dino.dino.draw(graphics);
		Window.makeBlur(5);
		DButton.startButton.draw(graphics);
		DButton.settingInButton.draw(graphics);
	}

	public static void correctAndRecord(int windowWidth, int windowHeight, double position) {
		DButton.startButton.setTouchableLocation(windowWidth, windowHeight);
		DButton.settingInButton.setTouchableLocation(windowWidth, windowHeight);
		Field.field.recordField(position);
		Clouds.clouds.record(position);
		Cactuses.cactuses.record(position);
		Dino.dino.record(position);
		Clouds.clouds.record(position);
		PredictLogic.predictCollisionAndReact();
	}

}
