package dinosaur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dinosaur.DButton.buttonPurpose;

public class Pause {

	private static Font font = new Font("Comic Sans MS", Font.BOLD, 90);

	public static void setPauseButtons(Window window) {
		DButton.setButton(window, 100, 100, DImage.continueButtonImg, buttonPurpose.CONTINUE_GAME);
		DButton.setButton(window, 20, 200, 180, 180, DImage.settingsButtonImg, buttonPurpose.GET_SETTINGS_MENU);
		DButton.setButton(window, 20, 200, 180, 180, DImage.settingsButtonImg, buttonPurpose.GET_SETTINGS_MENU);
		SettingsMenu.fromWhat = Control.state;
	}

	public static void draw(Graphics graphics) {
		Background.draw(graphics);
		Field.field.draw(graphics);
		Clouds.clouds.draw(graphics);
		Cactuses.cactuses.draw(graphics);
		Dino.dino.draw(graphics);
		Window.makeSmoothBlur(5, 200);
		drawLabel(graphics);
		DButton.continueButton.draw(graphics);
		DButton.settingInButton.draw(graphics);
	}

	private static void drawLabel(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.setFont(font);
		graphics.drawString("Pause", 550, 160);
	}

	public static void correctButtonsTouchableSpace(int windowWidth, int windowHeight) {
		DButton.continueButton.setTouchableLocation(windowWidth, windowHeight);
		DButton.settingInButton.setTouchableLocation(windowWidth, windowHeight);
	}
}