package dinosaur;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Things {

	public static JButton removePort = new JButton("");
	public static boolean isButtonRemoved = true;
	public static Image remove = Graphic.loadImage("/delite.png");
	public static JButton replay = new JButton("");
	public static JButton getSettings = new JButton();
	public static double koefX = 1, koefY = 1;

	public static void setButtonGetSettings() {
		getSettings.setBounds((int) (((double) Settings.settingsButtonX) * koefX),
				(int) (((double) Settings.settingsButtonY) * koefY),
				(int) (((double) Settings.settingsButtonXX) * koefX),
				(int) (((double) Settings.settingsButtonYY) * koefY));
		getSettings.setOpaque(false);
		getSettings.setContentAreaFilled(false);
		getSettings.setBorderPainted(false);
		getSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getSettings.removeActionListener(this);
				FrameAndListener.frame.remove(getSettings);
				MyBlur.setFon(FrameAndListener.screen);
				Settings.settingWindow = true;
				Settings.setButtonsToSetSmth();
			}
		});
		FrameAndListener.frame.add(getSettings);
	}

	public static void setButtonRemovePort() {
		removePort.setBounds((int) (((double) Settings.closePortX) * koefX),
				(int) (((double) Settings.closePortY) * koefY), (int) (((double) Settings.closePortXX) * koefX),
				(int) (((double) Settings.closePortYY) * koefY));
		removePort.setOpaque(false);
		removePort.setContentAreaFilled(false);
		removePort.setBorderPainted(false);
		removePort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Port.app.closePort();
				removePort.removeActionListener(this);
				FrameAndListener.frame.remove(removePort);
				isButtonRemoved = true;
			}
		});
		FrameAndListener.frame.add(removePort);
	}

	public static void setButtonReplay() {
		replay.setBounds(
				(int) (koefX
						* (double) (Settings.gameoverLabelX + (Settings.gameoverLabelXX - Settings.restartXX) / 2)),
				(int) (koefY * (double) (Settings.gameoverLabelY + Settings.gameoverLabelYY + Settings.height / 16)),
				(int) (koefX * ((double) Settings.restartXX)), (int) (koefY * (double) Settings.restartYY));
		replay.setOpaque(false);
		replay.setContentAreaFilled(false);
		replay.setBorderPainted(false);
		replay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removePort.removeActionListener(this);
				FrameAndListener.frame.remove(replay);
				Main.startGame();
			}
		});
		FrameAndListener.frame.getContentPane().add(replay);
	}

}
