package dinosaur;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FrameAndListener extends JPanel implements ActionListener {
	static final long serialVersionUID = 42L;
	public static JFrame frame = new JFrame("Dino Game");
	private Timer timer = new Timer(1000 / Sets.speed, this);

	public FrameAndListener() {
		timer.start();
		addKeyListener(new KeyBoard());
		setFocusable(true);
	}

	public static void setFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setSize(Sets.START_WIDTH, Sets.START_HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.add(new FrameAndListener());
		frame.setVisible(true);
		frame.setLayout(null);
	}

	@Override
	public void paint(Graphics g) {
		if (Main.inGame) {
			if (Cactuses.cactusesAmount == 0 || Cactuses.distToCactus[Cactuses.cactusesAmount - 1] < Sets.width / 3) {
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
		} else {
			GameOver.gameOver(g, frame);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
