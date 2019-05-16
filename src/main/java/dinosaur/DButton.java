package dinosaur;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import dinosaur.Control.State;

public class DButton extends JButton {
	private static final long serialVersionUID = 42L;
	public static DButton startButton, selectButton, restartButton, continueButton, pauseButton, settingInButton,
			settingsOutButton, gPlusButton, gMinusButton, openChartButton, closeChartButton, sound, recalibrate;

	private static double horizontalStretch = 1.0, verticalStretch = 1.0;

	public int horizontalIndent, verticalIndent, width, height;
	public Image icon;
	public buttonPurpose purpose;

	public static enum buttonPurpose {
		START_GAME, SELECT_PORT, RESTART_GAME, CONTINUE_GAME, PAUSE, GET_SETTINGS_MENU, CLOSE_SETTINGS_MENU_S_M,
		CLOSE_SETTINGS_MENU_P_M, CLOSE_SETTINGS_MENU_GO_M, PLUS_GRAVITY, MINUS_GRAVITY, OPEN_CHART, CLOSE_CHART, SOUND,
		RECALIBRATE;
	}

	private DButton(Window window, int horizontalIndent, int verticalIndent, int width, int height, Image icon,
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
			public void actionPerformed(ActionEvent e) {
				switch (purpose) {
				case START_GAME:
					Control.startNewGame();
					removeAll(window);
					Control.state = Control.State.PRE_START_GAME;
					break;
				case SELECT_PORT:
					Port.setPort(DComboBox.portsComboBox.portName);
					break;
				case RESTART_GAME:
					Control.startNewGame();
					removeAll(window);
					Control.state = Control.State.PRE_START_GAME;
					break;
				case CONTINUE_GAME:
					removeAll(window);
					Control.state = State.PRE_START_GAME;
					break;
				case GET_SETTINGS_MENU:
					removeAll(window);
					Control.state = State.SETTING_ST_M;
					break;
				case PAUSE:
					removeAll(window);
					Control.state = State.SETTING_P_M;
					break;
				case CLOSE_SETTINGS_MENU_GO_M:
					removeAll(window);
					Dino.dino.setJumpKoef(Dino.getJumpKoef());
					Control.state = State.SETTING_GO_M;
					break;
				case CLOSE_SETTINGS_MENU_P_M:
					removeAll(window);
					Dino.dino.setJumpKoef(Dino.getJumpKoef());
					Control.state = State.SETTING_P_M;
					break;
				case CLOSE_SETTINGS_MENU_S_M:
					removeAll(window);
					Dino.dino.setJumpKoef(Dino.getJumpKoef());
					Control.state = State.SETTING_S_M;
					break;
				case PLUS_GRAVITY:
					Dino.presentable.changeDinoKoefParab(true);
					break;
				case MINUS_GRAVITY:
					Dino.presentable.changeDinoKoefParab(false);
					break;
				case OPEN_CHART:
					removeAll(window);
					Control.state = State.SETTING_CHART;
					break;
				case CLOSE_CHART:
					removeAll(window);
					Control.state = State.SETTING_ST_M;
					break;
				case SOUND:
					if (Sound.isSettedOn) {
						Sound.isSettedOn = false;
						sound.icon = DImage.soundOffImg;
					} else {
						Sound.isSettedOn = true;
						sound.icon = DImage.soundOnImg;
					}
					break;
				case RECALIBRATE:
					Filter.filter.isCalibrated = false;
					Control.startNewGame();
					removeAll(window);
					Control.state = Control.State.PRE_START_GAME;
					break;
				default:
					break;

				}
			}
		});
	}

	private static void removeAll(Window window) {
		window.tryRemove(DComboBox.portsComboBox);
		window.tryRemove(gMinusButton);
		window.tryRemove(gPlusButton);
		window.tryRemove(openChartButton);
		window.tryRemove(sound);
		window.tryRemove(settingsOutButton);
		window.tryRemove(pauseButton);
		window.tryRemove(settingInButton);
		window.tryRemove(continueButton);
		window.tryRemove(restartButton);
		window.tryRemove(startButton);
		window.tryRemove(selectButton);
		window.tryRemove(closeChartButton);
		window.tryRemove(recalibrate);
	}

	public static void setButton(Window window, int width, int height, Image icon, buttonPurpose purpose) {
		setButton(window, 672 - width / 2, 270 - height / 2, width, height, icon, purpose);
	}

	public static void setButton(Window window, int horizontalIndent, int verticalIndent, int width, int height,
			Image icon, buttonPurpose purpose) {
		if (returnButtonByPurpose(purpose) == null)
			setButtonDependingPurpose(
					new DButton(window, horizontalIndent, verticalIndent, width, height, icon, purpose), window);
		else
			setButtonDependingPurpose(returnButtonByPurpose(purpose), window);
	}

	private static DButton returnButtonByPurpose(buttonPurpose purpose) {
		switch (purpose) {
		case CLOSE_SETTINGS_MENU_GO_M:
			return settingsOutButton;
		case CLOSE_SETTINGS_MENU_P_M:
			return settingsOutButton;
		case CLOSE_SETTINGS_MENU_S_M:
			return settingsOutButton;
		case CONTINUE_GAME:
			return continueButton;
		case GET_SETTINGS_MENU:
			return settingInButton;
		case PAUSE:
			return pauseButton;
		case RESTART_GAME:
			return restartButton;
		case SELECT_PORT:
			return selectButton;
		case START_GAME:
			return startButton;
		case OPEN_CHART:
			return openChartButton;
		case CLOSE_CHART:
			return closeChartButton;
		case SOUND:
			return sound;
		case RECALIBRATE:
			return recalibrate;
		default:
			return null;
		}
	}

	private static void setButtonDependingPurpose(DButton currentButton, Window window) {
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
		case CONTINUE_GAME:
			continueButton = currentButton;
			window.add(continueButton);
			break;
		case GET_SETTINGS_MENU:
			settingInButton = currentButton;
			window.add(settingInButton);
			break;
		case PAUSE:
			pauseButton = currentButton;
			window.add(pauseButton);
			break;
		case CLOSE_SETTINGS_MENU_GO_M:
			settingsOutButton = currentButton;
			window.add(settingsOutButton);
			break;
		case CLOSE_SETTINGS_MENU_P_M:
			settingsOutButton = currentButton;
			window.add(settingsOutButton);
			break;
		case CLOSE_SETTINGS_MENU_S_M:
			settingsOutButton = currentButton;
			window.add(settingsOutButton);
			break;
		case MINUS_GRAVITY:
			gPlusButton = currentButton;
			window.add(gPlusButton);
			break;
		case PLUS_GRAVITY:
			gMinusButton = currentButton;
			window.add(gMinusButton);
			break;
		case OPEN_CHART:
			openChartButton = currentButton;
			window.add(openChartButton);
			break;
		case CLOSE_CHART:
			closeChartButton = currentButton;
			window.add(closeChartButton);
			break;
		case SOUND:
			sound = currentButton;
			window.add(sound);
			break;
		case RECALIBRATE:
			recalibrate = currentButton;
			window.add(recalibrate);
			break;
		default:
			break;
		}
	}

	public void draw(Graphics graphics) {
		if (verticalIndent < 100)
			graphics.drawImage(icon, horizontalIndent, verticalIndent + 10, width, height, null);
		else
			graphics.drawImage(icon, horizontalIndent, verticalIndent + 25, width, height, null);
	}

	public void setTouchableLocation(int windowWidth, int windowHeight) {
		calibrateStretchParams(windowWidth, windowHeight);
		setBounds((int) ((double) (this.horizontalIndent) * horizontalStretch),
				(int) ((double) (this.verticalIndent) * verticalStretch),
				(int) ((double) (this.width) * horizontalStretch), (int) ((double) (this.height) * verticalStretch));
	}

	private static void calibrateStretchParams(int windowWidth, int windowHeight) {
		horizontalStretch = (double) windowWidth / 1344.0;
		verticalStretch = (double) windowHeight / 540.0;
	}

}
