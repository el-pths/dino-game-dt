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
	public static BufferedImage screen = new BufferedImage(1344, 540, BufferedImage.TYPE_INT_RGB);

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
			if (keyNum == KeyEvent.VK_SPACE)
				Dino.dino.startJump();
		}
	}

	private class ContentPane extends JPanel implements Runnable {
		private static final long serialVersionUID = 42L;
		@SuppressWarnings("unused")
		private long startTime = System.currentTimeMillis(), previousTime = System.currentTimeMillis(),
				fullPassTime = 200;

		public ContentPane() {
			super();
			new Thread(this).start();
		}

		@Override
		public void paint(Graphics g) {
			g.drawImage(screen, 0, 0, this.getWidth(), this.getHeight(), null);
		}

		@Override
		public void run() {
			while (true) {
				if (window == null)
					continue;
				Control.setModeDependingConditions(window, screen.getGraphics(), position);
				repaint();
				updateState();
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

}
