package dinosaur;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {
	private static final long serialVersionUID = 42L;
	private static Window window;
	private static double position = 0;
	private static BImage screen = new BImage(1344, 540, BufferedImage.TYPE_INT_RGB);

	public static double FULL_PASS_TIME = 60.0, NORMALL_FULL_PASS_TIME = FULL_PASS_TIME;

	private Window(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(1344, 540);
		setLocationRelativeTo(null);
		setFocusable(true);
		addKeyListener(new Keyboard());
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// Close port
				System.exit(0);
			}
		});
		setContentPane(new ContentPane());
	}

	public static void setWindowAndStartScript(String name) {
		window = new Window(name);
	}

	private class Keyboard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent kEvt) {
			int keyNum = kEvt.getKeyCode();
			switch (keyNum) {
			case KeyEvent.VK_SPACE:
				Dino.dino.startJump();
				break;
			case KeyEvent.VK_UP:
				FULL_PASS_TIME += 0.05;
				System.out.println(FULL_PASS_TIME);
				break;
			case KeyEvent.VK_DOWN:
				FULL_PASS_TIME -= 0.05;
				System.out.println(FULL_PASS_TIME);
				break;
			case KeyEvent.VK_P:
				if (Control.pause)
					Control.pause = false;
				else
					Control.pause = true;
				break;
			}
		}
	}

	private class ContentPane extends JPanel implements Runnable {
		private static final long serialVersionUID = 42L;
		@SuppressWarnings("unused")
		private long startTime = System.currentTimeMillis(), previousTime = System.currentTimeMillis();
		private double fullPassTime;

		public ContentPane() {
			super();
			fullPassTime = FULL_PASS_TIME;
			new Thread(this).start();
		}

		@Override
		public void paint(Graphics g) {
			Control.setModeDependingConditions(window, screen.getGraphics(), position);
			g.drawImage(screen, 0, 0, this.getWidth(), this.getHeight(), null);
		}

		@Override
		public void run() {
			while (window == null) {
			}
			while (true) {
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

		void updateState() {
			long currentTime = System.currentTimeMillis();
			long timePassed = currentTime - previousTime;
			position = timePassed / fullPassTime;
			previousTime = currentTime;
		}
	}

	public static void makeBlur(int strengeth) {
		screen.blur(strengeth);
	}

}
