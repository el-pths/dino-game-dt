package dinosaur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import dinosaur.DButton.buttonPurpose;
import dinosaur.Control.State;

public class SettingsMenu {

	private static Font font = new Font("Comic Sans MS", Font.BOLD, 70);
	public static State fromWhat;

	public static void setButtons(Window window) {
		switch (fromWhat) {
		case SETTING_S_M:
			DButton.setButton(window, DButton.settingInButton.horizontalIndent, DButton.settingInButton.verticalIndent,
					DButton.settingInButton.width, DButton.settingInButton.height, DImage.settingsButtonImg,
					buttonPurpose.CLOSE_SETTINGS_MENU_S_M);
			break;
		case SETTING_GO_M:
			DButton.setButton(window, DButton.settingInButton.horizontalIndent, DButton.settingInButton.verticalIndent,
					DButton.settingInButton.width, DButton.settingInButton.height, DImage.settingsButtonImg,
					buttonPurpose.CLOSE_SETTINGS_MENU_GO_M);
			break;
		case SETTING_P_M:
			DButton.setButton(window, DButton.settingInButton.horizontalIndent, DButton.settingInButton.verticalIndent,
					DButton.settingInButton.width, DButton.settingInButton.height, DImage.settingsButtonImg,
					buttonPurpose.CLOSE_SETTINGS_MENU_P_M);
			break;
		default:
			System.out.println("Not this " + Control.state);
			break;
		}
		Port.setComboBox(window, 30, 200, 340, 50);
		int buttonHorizontalIndentFromBox = 20, buttonWidth = 40, buttonHeight = 40;
		DButton.setButton(window,
				DComboBox.portsComboBox.leftIndent + DComboBox.portsComboBox.width + buttonHorizontalIndentFromBox,
				DComboBox.portsComboBox.upperIndent, buttonWidth, buttonHeight, DImage.loadImage("/selected2.png"),
				buttonPurpose.SELECT_PORT);
		DButton.setButton(window, Dino.presentable.horizontalIndent + Dino.presentable.width, 450, 60, 60,
				DImage.plusButton, DButton.buttonPurpose.PLUS_GRAVITY);
		DButton.setButton(window, Dino.presentable.horizontalIndent - 60, 450, 60, 60, DImage.minusButton,
				DButton.buttonPurpose.MINUS_GRAVITY);
		DButton.setButton(window, 1000, 50, 200, 200, DImage.chartsButton, DButton.buttonPurpose.OPEN_CHART);
	}

	public static void draw(Graphics graphics) {
		Background.draw(graphics);
		drawLabel(graphics);
		DButton.settingsOutButton.draw(graphics);
		DButton.selectButton.draw(graphics);
		drawChosenPortName(graphics);
		DButton.gMinusButton.draw(graphics);
		DButton.gPlusButton.draw(graphics);
		DButton.openChartButton.draw(graphics);
		Dino.presentable.draw(graphics);
	}

	private static void drawChosenPortName(Graphics graphics) {
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(DComboBox.portsComboBox.leftIndent, DComboBox.portsComboBox.upperIndent,
				DComboBox.portsComboBox.width, DComboBox.portsComboBox.height);
		graphics.setColor(Color.BLACK);
		graphics.setFont(new Font("Comic Sans MS", Font.BOLD, DComboBox.portsComboBox.font.getSize() * 2));
		graphics.drawString(DComboBox.portsComboBox.portName, DComboBox.portsComboBox.leftIndent + 3,
				DComboBox.portsComboBox.height + DComboBox.portsComboBox.upperIndent - 8);
		graphics.drawLine(DComboBox.portsComboBox.leftIndent, DComboBox.portsComboBox.upperIndent,
				DComboBox.portsComboBox.leftIndent,
				DComboBox.portsComboBox.upperIndent + DComboBox.portsComboBox.height);
		graphics.drawLine(DComboBox.portsComboBox.leftIndent, DComboBox.portsComboBox.upperIndent,
				DComboBox.portsComboBox.leftIndent + DComboBox.portsComboBox.width,
				DComboBox.portsComboBox.upperIndent);
		graphics.drawLine(DComboBox.portsComboBox.leftIndent + DComboBox.portsComboBox.width,
				DComboBox.portsComboBox.upperIndent, DComboBox.portsComboBox.leftIndent + DComboBox.portsComboBox.width,
				DComboBox.portsComboBox.upperIndent + DComboBox.portsComboBox.height);
		graphics.drawLine(DComboBox.portsComboBox.leftIndent,
				DComboBox.portsComboBox.upperIndent + DComboBox.portsComboBox.height,
				DComboBox.portsComboBox.leftIndent + DComboBox.portsComboBox.width,
				DComboBox.portsComboBox.upperIndent + DComboBox.portsComboBox.height);
		graphics.setFont(
				new Font("Comic Sans MS", Font.BOLD, (int) ((double) DComboBox.portsComboBox.font.getSize() * 1.5)));
		graphics.setColor(Color.RED);
		if (Port.getSettedPortName() != "Without port") {
			graphics.drawString(("The port " + Port.getSettedPortName() + " is setted"),
					DComboBox.portsComboBox.leftIndent + 3,
					DComboBox.portsComboBox.height * 2 + DComboBox.portsComboBox.upperIndent - 8);
		} else {
			graphics.drawString("No one port is setted", DComboBox.portsComboBox.leftIndent + 3,
					DComboBox.portsComboBox.height * 2 + DComboBox.portsComboBox.upperIndent - 8);
		}
	}

	private static void drawLabel(Graphics graphics) {
		graphics.setColor(Color.RED);
		graphics.setFont(font);
		graphics.drawString("Settings menu", 150, 100);
	}

	public static void correctButtonsTouchableSpace(int windowWidth, int windowHeight) {
		DButton.settingsOutButton.setTouchableLocation(windowWidth, windowHeight);
		DButton.selectButton.setTouchableLocation(windowWidth, windowHeight);
		DButton.gMinusButton.setTouchableLocation(windowWidth, windowHeight);
		DButton.gPlusButton.setTouchableLocation(windowWidth, windowHeight);
		DButton.openChartButton.setTouchableLocation(windowWidth, windowHeight);
	}

	public static void recordPresentableDino(double position) {
		Dino.presentable.record(position);
	}

}