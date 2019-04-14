package dinosaur;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FrameAndListener extends JPanel implements ActionListener {
	private static final long serialVersionUID = 42L;
	public static JFrame frame;
	private Timer timer = new Timer(1000 / Sets.speed, this);
	public static int score = 0, cactusesBehind = 0;

	public FrameAndListener() {
		if (Port.isPortSetted) {
			timer.start();
			addKeyListener(new KeyBoard());
			Things.setButtonRemovePort();
			Things.isButtonRemoved = false;
		}
		setFocusable(true);
	}

	public static void setFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setSize(Sets.START_WIDTH, Sets.START_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.add(new FrameAndListener());
		frame.setVisible(true);
		frame.setLayout(null);
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
	public void paint(Graphics g) {
		if (Port.isPortSetted) {
			if (Main.inGame) {
				if (Cactuses.cactusesAmount == 0
						|| Cactuses.distToCactus[Cactuses.cactusesAmount - 1] < (int) (Sets.width * 2 / 3)) {
					Cactuses.distToCactus[Cactuses.cactusesAmount] = Generators.generateDistanceToNextCactus()
							+ Sets.width / 2;
					Cactuses.cactusesType[Cactuses.cactusesAmount] = Generators.generateNumberOfThisCactus$sType();
					Cactuses.cactusesAmount++;
				}
				Treatment.recordField();
				Graphic.drawFirstFloor(g);
				Graphic.drawCactuses(g);
				Treatment.ifDinoIsInBounce();
				Graphic.drawDino(g);
				Graphic.writeScore(g);
			} else {
				GameOver.gameOver(g, frame);
			}
		} else
			Start.haveToChoosePort(frame);
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
