package dinosaur;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class DButton extends JButton {
	private static final long serialVersionUID = 42L;
	public static DButton startButton, selectButton, restartButton;

	private static double horizontalStretch = 1.0, verticalStretch = 1.0;

	public int horizontalIndent, verticalIndent, width, height;
	public Image icon;
	public buttonPurpose purpose;

	public static enum buttonPurpose {
		START_GAME, SELECT_PORT, RESTART_GAME;
	}

	private DButton(int horizontalIndent, int verticalIndent, int width, int height, Image icon,
			buttonPurpose purpose) {
		super();
		this.horizontalIndent = horizontalIndent;
		this.verticalIndent = verticalIndent;
		this.width = width;
		this.height = height;
		this.icon = icon;
		this.purpose = purpose;

		setFocusable(false);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
		addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				removeActionListener(this);
				switch (purpose) {
				case START_GAME:
					Control.startNewGame();
					DComboBox.portsComboBox.hide();
					startButton.hide();
					Control.state = Control.State.PRE_START_GAME;
					break;
				case SELECT_PORT:
					System.out.println("Port is selecting");
					Port.setPort(DComboBox.portsComboBox.portName);
					break;
				case RESTART_GAME:
					Control.startNewGame();
					Window.setSmoothBlurReady();
					Control.state = Control.State.PRE_START_GAME;
					break;
				}
			}
		});
	}

	public static void setButton(JFrame window, int width, int height, Image icon, buttonPurpose purpose) {
		setButton(window, 672 - width / 2, 270 - height / 2, width, height, icon, purpose);
	}

	public static void setButton(JFrame window, int horizontalIndent, int verticalIndent, int width, int height,
			Image icon, buttonPurpose purpose) {
		setButtonDependingPurpose(new DButton(horizontalIndent, verticalIndent, width, height, icon, purpose), window);
	}

	private static void setButtonDependingPurpose(DButton currentButton, JFrame window) {
		switch (currentButton.purpose) {
		case START_GAME:
			startButton = currentButton;
			window.add(startButton);
			break;
		case SELECT_PORT:
			selectButton = currentButton;
			window.add(selectButton);
			break;
		case RESTART_GAME:
			restartButton = currentButton;
			window.add(restartButton);
			break;
		}
	}

	public void draw(Graphics graphics) {
		graphics.drawImage(icon, horizontalIndent, verticalIndent, width, height, null);
	}

	public void setTouchableLocation(int windowWidth, int windowHeight) {
		calibrateStretchParams(windowWidth, windowHeight);
		setBounds((int) ((double) (this.horizontalIndent) * horizontalStretch),
				(int) ((double) (this.verticalIndent) * verticalStretch),
				(int) ((double) (this.width) * horizontalStretch), (int) ((double) (this.height) * verticalStretch));
	}

	private static void calibrateStretchParams(int windowWidth, int windowHeight) {
		horizontalStretch = windowWidth / 1344.0;
		verticalStretch = windowHeight / 540.0;
	}
	
}
