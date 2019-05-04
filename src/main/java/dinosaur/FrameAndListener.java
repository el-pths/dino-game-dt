package dinosaur;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameAndListener extends JPanel implements Runnable {
	Main main;

	private static final long serialVersionUID = 42L;
	public static JFrame frame;
	public static int score = 0, cactusesBehind = 0;
	public static long startTime = System.currentTimeMillis();
	BufferedImage screen = new BufferedImage(Settings.START_WIDTH, Settings.START_HEIGHT, BufferedImage.TYPE_INT_RGB);

	double position = 0;
	long prevTime = System.currentTimeMillis();

	public FrameAndListener() {
		if (Port.isPortSetted) {
			new Thread(this).start();
			addKeyListener(new KeyBoard());
			Things.setButtonRemovePort();
			Things.isButtonRemoved = false;
			frame.setResizable(true);
		}
		setFocusable(true);
	}

	public static void setFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(Settings.START_WIDTH, Settings.START_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new FrameAndListener());
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (Start.choseAnyPort && Start.choise != "Without")
					Port.app.closePort();
				System.exit(0);
			}
		});
		try {
			Sound.loadSoundFiles();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g1) {
		Graphics g = screen.getGraphics();
		if (Settings.settingWindow) {
			Settings.getSettingsMenu(g);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
			g.drawString("It's deadscreen", 290, 270);
		} else {
			if (Main.inGame) {
			    long timeFromStart = System.currentTimeMillis() - startTime;
				if (timeFromStart > 2500 && (Cactuses.cactusesAmount == 0
						|| Cactuses.distToCactus[Cactuses.cactusesAmount - 1] < (int) (Settings.START_WIDTH))) {
					Cactuses.distToCactus[Cactuses.cactusesAmount] = Generators.generateDistanceToNextCactus()
							+ Settings.START_WIDTH / 2;
					Cactuses.cactusesType[Cactuses.cactusesAmount] = Generators.generateNumberOfThisCactus$sType();
					Cactuses.cactusesAmount++;
				}
				if (Clouds.cloudsAmount == 0
						|| Clouds.distToCloud[Clouds.cloudsAmount - 1] < (int) (Settings.START_WIDTH * 5 / 6)) {
					Clouds.distToCloud[Clouds.cloudsAmount] = Generators.generateFistanceToNextCloud();
					Clouds.cloudHeight[Clouds.cloudsAmount] = Generators.generateCloudHeight();
					Clouds.cloudType[Clouds.cloudsAmount] = Generators.generateCloudTypeNum();
					Clouds.cloudsAmount++;
				}
				Graphic.drawFirstFloor(g);
				Graphic.drawClouds(g);
				Graphic.drawCactuses(g);
				Graphic.drawDino(g);
				Graphic.writeScore(g);
				Things.setButtonGetSettings();
				Settings.drawSettingButton(g);
				if (timeFromStart < 2000) {
				    Graphic.drawCalibration(g, (int) timeFromStart / 1000);
				    if (timeFromStart > 1700) {
				        Port.calibration();
				    }
				}
				// if (Dino.isNowInAir) {
				// g.setColor(Color.RED);
				// g.fillRect(0, (int) (Settings.START_HEIGHT * 0.58) - Dino.jumpHeight,
				// Settings.START_WIDTH, 1);
				// }
			} else {
				GameOver.gameOver(g);
			}
		}
		g1.drawImage(screen, 0, 0, frame.getWidth(), frame.getHeight(), null);
		screen.getGraphics().clearRect(0, 0, Settings.START_WIDTH, Settings.START_HEIGHT);
	}

	private class KeyBoard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent kEvt) {
			Treatment.processedPressedKey(kEvt.getKeyCode());
		}
	}

	void updateState() {
		long t = System.currentTimeMillis();
		long timePassed = t - prevTime;
		position = timePassed / Settings.getFullPassTime();
		Things.koefX = (FrameAndListener.frame.getWidth() / ((double) Settings.START_WIDTH));
		Things.koefY = (FrameAndListener.frame.getHeight() / ((double) Settings.START_HEIGHT));
		if (Main.inGame) {
			Treatment.recordField(position);
			Treatment.recordClouds(position);
			Treatment.ifDinoIsInBounce(position);
		}
		prevTime = t;
	}

	@Override
	public void run() {
		while (true) {
			if (Main.inGame)
				Treatment.check();
			updateState();
			repaint();
			try {
				// not necessary, but we don't want
				// animation happen too often
				Thread.sleep(15);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

}
