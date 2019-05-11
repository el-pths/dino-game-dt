package dinosaur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;

import dinosaur.DButton.buttonPurpose;

public class StartMenu {

	public static boolean isMenuSetted = false;

	public static void set(JFrame window) {
		Port.setComboBox(window);
		int buttonHorizontalIndentFromBox = 20, buttonWidth = 40, buttonHeight = 40;
		DButton.setButton(window,
				DComboBox.portsComboBox.leftIndent + DComboBox.portsComboBox.width + buttonHorizontalIndentFromBox,
				DComboBox.portsComboBox.upperIndent, buttonWidth, buttonHeight, DImage.loadImage("/selected2.png"),
				buttonPurpose.SELECT_PORT);
		DButton.setButton(window, 200, 200, DImage.loadImage("/start4.png"), DButton.buttonPurpose.START_GAME);
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
		DButton.selectButton.draw(graphics);
		drawChosenPortName(graphics);
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

	public static void correctAndRecord(int windowWidth, int windowHeight, double position) {
		DButton.startButton.setTouchableLocation(windowWidth, windowHeight);
		DButton.selectButton.setTouchableLocation(windowWidth, windowHeight);
		Field.field.recordField(position);
		Clouds.clouds.record(position);
		Cactuses.cactuses.record(position);
		Dino.dino.record(position);
		Clouds.clouds.record(position);
	}

}
