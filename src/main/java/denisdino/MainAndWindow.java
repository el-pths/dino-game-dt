package denisdino;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainAndWindow extends JPanel implements ActionListener {
	private static final long serialVersionUID = 42L;
	Timer timer = new Timer(1000 / Settings.SPEED, this);

	public MainAndWindow() {
		timer.start();
		addKeyListener(new KeyBoard());
		setFocusable(true);
	}

	private class KeyBoard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent kEvt) {
			int key = kEvt.getKeyCode();
			if (key == KeyEvent.VK_SPACE && !Dino.isNowInAir)
				Dino.isJump = true;
		}
	}

	@Override
	public void paint(Graphics g) {
		if (Logic.inGame) {
			if (Field.objects$Amount == 0 || Field.distToObject[Field.objects$Amount - 1] < Settings.SCREEN_WIDTH / 3) {
				Field.distToObject[Field.objects$Amount] = Cactus.generateDistanceToNextCactus()
						+ Settings.SCREEN_WIDTH / 2;
				Field.object$sType[Field.objects$Amount] = Cactus.generateNumberOfThisCactus$sType();
				Field.objects$Amount++;
			}
			Field.recordField();
			Background.printFirstFloor(g);
			Field.printCactuses(g);
			Logic.ifDinoIsInBounce();
			Dino.printDino(g);
		} else {
			Logic.printGameOver(g);
		}
	}

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setSize(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
		f.setLocationRelativeTo(null);
		f.add(new MainAndWindow());
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (Dino.pedometr >= Settings.FEET_RESTRICION) {
			Dino.pedometr = 0;
			if (Dino.pose == Dino.leftLegRaised)
				Dino.pose = Dino.rightLegRaised;
			else
				Dino.pose = Dino.leftLegRaised;
		}
		repaint();
	}

}