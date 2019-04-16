package dinosaur;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FrameAndListener extends JPanel implements ActionListener {
	Main main;

	private static final long serialVersionUID = 42L;
	public static JFrame frame;
	private Timer timer = new Timer(1000 / Settings.speed, this);
	public static int score = 0, cactusesBehind = 0;
	BufferedImage screen = new BufferedImage(Settings.START_WIDTH, Settings.START_HEIGHT, BufferedImage.TYPE_INT_RGB);

	public FrameAndListener() {
		if (Port.isPortSetted) {
			timer.start();
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
					System.out.println('\n' + "Closing...");
				Port.app.closePort();
				System.exit(0);
			}
		});
	}

	@Override
	public void paint(Graphics g1) {
		Graphics g = screen.getGraphics();
		if (Main.inGame) {
			if (Cactuses.cactusesAmount == 0
					|| Cactuses.distToCactus[Cactuses.cactusesAmount - 1] < (int) (Settings.START_WIDTH)) {
				Cactuses.distToCactus[Cactuses.cactusesAmount] = Generators.generateDistanceToNextCactus()
						+ Settings.START_WIDTH / 2;
				Cactuses.cactusesType[Cactuses.cactusesAmount] = Generators.generateNumberOfThisCactus$sType();
				Cactuses.cactusesAmount++;
			}
			if (Clouds.cloudsAmount == 0
					|| Clouds.distToCloud[Clouds.cloudsAmount - 1] < (int) (Settings.START_WIDTH * 5 / 6)) {
				Clouds.distToCloud[Clouds.cloudsAmount] = Generators.generateFistanceToNextCloud();
				Clouds.cloudHeight[Clouds.cloudsAmount] = Generators.generateCloudHeight();
				Clouds.cloudsAmount++;
			}
			Treatment.recordField();
			Graphic.drawFirstFloor(g);
			Graphic.drawClouds(g);
			Graphic.drawCactuses(g);
			Treatment.recordClouds();
			Treatment.ifDinoIsInBounce();
			Graphic.drawDino(g);
			Graphic.writeScore(g);
		} else {
			GameOver.gameOver(g);
		}
		g1.drawImage(screen, 0, 0, frame.getWidth(), frame.getHeight(), null);
		screen.getGraphics().clearRect(0, 0, Settings.START_WIDTH, Settings.START_HEIGHT);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Main.inGame)
			Treatment.check();
		repaint();
	}

	private class KeyBoard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent kEvt) {
			Treatment.processedPressedKey(kEvt.getKeyCode());
		}
	}

}
