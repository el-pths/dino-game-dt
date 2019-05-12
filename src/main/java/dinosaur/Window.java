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

	public static int bufferedImageWidth = 1344, bufferedImageHeight = 540;
	public static double FULL_PASS_TIME = 60.0, NORMAL_FULL_PASS_TIME = FULL_PASS_TIME;

	private static double position = 0;
	private static BImage screen = new BImage(bufferedImageWidth, bufferedImageHeight, BufferedImage.TYPE_INT_RGB);

	private static long smBPreviousTime = 0;
	private static int smBTimeSum = 0;

	private Window(String name) {
		super(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		setSize(1344, 540);
		setLocationRelativeTo(null);
		setFocusable(true);
		setVisible(true);
		addKeyListener(new Keyboard());
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Port.closeAllPorts();
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
			if (Control.state == Control.State.GAMMING_PROCESS || Control.state == Control.State.SETTINGS_MENU) {
				System.out.println("Key pressed");
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
					NORMAL_FULL_PASS_TIME = FULL_PASS_TIME;
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
	}

	private class ContentPane extends JPanel implements Runnable {
		private static final long serialVersionUID = 42L;
		@SuppressWarnings("unused")
		private long startTime = System.currentTimeMillis(), previousTime = System.currentTimeMillis();

		public ContentPane() {
			super();
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
			position = timePassed / FULL_PASS_TIME;
			previousTime = currentTime;
		}
	}

	public void tryRemove(DButton dbutton) {
		if (dbutton != null)
			this.remove(dbutton);
	}

	public void tryRemove(DComboBox dcombobox) {
		if (dcombobox != null)
			this.remove(dcombobox);
	}

	public static void makeBlur(int strengeth) {
		screen.blur(strengeth);
	}

	public static void setFullPassTime(double newFullPassTime) {
		FULL_PASS_TIME = newFullPassTime;
		NORMAL_FULL_PASS_TIME = newFullPassTime;
	}

	public static void makeSmoothBlur(int smBStepsAmount, int smBStep) {
		if (smBPreviousTime == 0) {
			smBPreviousTime = System.currentTimeMillis();
			return;
		}
		if (smBTimeSum < smBStepsAmount * smBStep) {
			smBTimeSum += System.currentTimeMillis() - smBPreviousTime;
			smBPreviousTime = System.currentTimeMillis();
		}
		makeBlur(smBTimeSum / smBStep + 1);
	}

	public static void setSmoothBlurReady() {
		smBPreviousTime = 0;
		smBTimeSum = 0;
	}
}
