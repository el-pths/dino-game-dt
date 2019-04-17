package dinosaur;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Settings {
	public static final int START_WIDTH = 1344, START_HEIGHT = 540, PIXEL = 4;
	public static int speed = 60, width = START_WIDTH, height = START_HEIGHT, koefficient_paraboli = 3;
	public static final int againXX = 200, againYY = 60, againX = (width - againXX) / 2, againY = height / 16;
	public static final int closePortXX = 60, closePortYY = 60, closePortX = (int) (width * 57 / 64),
			closePortY = (int) (height * 6 / 32);
	public static final int restartXX = 110, restartYY = 110;
	public static final int gameoverLabelX = Settings.width / 4, gameoverLabelY = Settings.height / 5,
			gameoverLabelXX = Settings.width / 2, gameoverLabelYY = Settings.height / 6;
	public static final int settingsButtonX = 10, settingsButtonY = 10, settingsButtonXX = 50, settingsButtonYY = 50;
	public static final int lessX = 200, lessY = 200, lessXX = 100, lessYY = 100, moreX = 400, moreY = 200,
			moreXX = 100, moreYY = 100;

	public static Image settingButton = Graphic.loadImage("/settingButton.png");
	public static boolean settingWindow = false;
	public static Image lessImg = Graphic.loadImage("/less.png");
	public static Image moreImg = Graphic.loadImage("/more.png");
	public static JButton less = new JButton();
	public static JButton more = new JButton();

	public static int proba = 0;

	public static void getSettingsMenu(Graphics g) {
		g.drawImage(lessImg, lessX, lessY, lessXX, lessYY, null);
		g.drawImage(moreImg, moreX, moreY, moreXX, moreYY, null);
	}

	public static void setButtonsToSetSmth() {
		less.setBounds((int) (((double) lessX) * Things.koefX), (int) (((double) lessY) * Things.koefY),
				(int) (((double) lessXX) * Things.koefX), (int) (((double) lessYY) * Things.koefY));
		less.setOpaque(false);
		less.setContentAreaFilled(false);
		less.setBorderPainted(false);
		less.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proba--;
			}
		});
		FrameAndListener.frame.add(less);

		more.setBounds((int) (((double) moreX) * Things.koefX), (int) (((double) moreY) * Things.koefY),
				(int) (((double) moreXX) * Things.koefX), (int) (((double) moreYY) * Things.koefY));
		more.setOpaque(false);
		more.setContentAreaFilled(false);
		more.setBorderPainted(false);
		more.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				proba++;
			}
		});
		FrameAndListener.frame.add(more);
	}

	public static void drawSettingButton(Graphics g) {
		g.drawImage(settingButton, settingsButtonX, settingsButtonY, settingsButtonXX, settingsButtonYY, null);
	}
}