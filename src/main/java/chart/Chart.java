package chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

import dinosaur.Filter;

public class Chart extends JPanel implements ActionListener {

	private static Scanner sc;
	private static final long serialVersionUID = 42L;
	private static JFrame frame;
	private static int speed = 50;
	private Timer timer = new Timer(1000 / speed, this);
	private static int width = 500, height = 400;
	private static BufferedImage screen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private static boolean pause = false;
	static int receivedPoints = 0;

	public static void main(String... args) {
		try {
			if (args.length > 1) {
				sc = new Scanner(new File(args[1]));
			} else {
				sc = new Scanner(Chart.class.getResourceAsStream("/jumps.txt"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		App.main();
		setFrame();
	}

	public static void setFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(new Chart());
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				sc.close();
				if (App.any)
					App.closePort();
				System.exit(0);
			}
		});
	}

	public Chart() {
		timer.start();
		setFocusable(true);
		addKeyListener(new KeyBoard());
	}

	void drawSignal(Graphics g, int[] data, int y0, Color color) {
		int rad = 30;
		g.drawLine(0, y0, width, y0);
		for (int i = 0; i < 8; i++)
			g.drawLine((i + 1) * speed + 20, y0 - rad / 2, (i + 1) * speed + 20, y0 + rad / 2);
		int prev = 0;
		g.setColor(color);
		for (int i = 0; i < data.length; i++) {
			g.drawLine(i, y0 - prev, i + 1, y0 - data[i] / 2);
			prev = data[i] / 2;
		}
		g.setColor(Color.BLACK);

	}

	@Override
	public void paint(Graphics g1) {
		if (!pause) {
			Graphics g = screen.getGraphics();
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
			((Graphics2D) g).setStroke(new BasicStroke(2));
			g.setColor(Color.BLACK);
			g.drawLine(20, 0, 20, height);
			drawSignal(g, Data.rms, 9 * height / 13, Color.GREEN);
			drawSignal(g, Data.jump, height - 5, Color.GRAY);
			drawSignal(g, Data.dX, 2 * height / 13, Color.YELLOW);
			drawSignal(g, Data.dY, 4 * height / 13, Color.BLUE);
			drawSignal(g, Data.dZ, 6 * height / 13, Color.RED);
		}
		g1.drawImage(screen, 0, 0, this.getWidth(), this.getHeight(), null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (sc.hasNext() && !pause) {
			if (!App.any) {
				Data.filter.newPoint(sc.nextInt(), sc.nextInt(), sc.nextInt(), receivedPoints * 30L);
				receivedPoints += 1;
				if (receivedPoints == 10) {
				    Data.filter.calibrate();
				}
				Data.nextPoint();
			}
			repaint();
		}
	}

	private static class KeyBoard extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent kEvt) {
			int key = kEvt.getKeyCode();
			if (key == KeyEvent.VK_SPACE)
				if (pause)
					pause = false;
				else
					pause = true;
		}
	}

}

class Data {

	public static Filter filter = new Filter();

	public static int[] dX = new int[430], dY = new int[430], dZ = new int[430];
	public static int[] rms = new int[430], jump = new int[430];

	public static void record(int newX, int newY, int newZ, int r, int j) {
		for (int i = 0; i < dX.length - 1; i++)
			dX[i] = dX[i + 1];
		dX[dX.length - 1] = newX;
		for (int i = 0; i < dY.length - 1; i++)
			dY[i] = dY[i + 1];
		dY[dY.length - 1] = newY;
		for (int i = 0; i < dZ.length - 1; i++)
			dZ[i] = dZ[i + 1];
		dZ[dZ.length - 1] = newZ;
		for (int i = 0; i < rms.length - 1; i++)
			rms[i] = rms[i + 1];
		rms[rms.length - 1] = r;
		for (int i = 0; i < jump.length - 1; i++)
			jump[i] = jump[i + 1];
		jump[jump.length - 1] = j;
		System.out.println(j);
	}

	static void nextPoint() {
		int x = filter.getX() * 50 / filter.getG();
		int y = filter.getY() * 50 / filter.getG();
		int z = filter.getZ() * 50 / filter.getG();
		Data.record(x, y, z, filter.getRMS(), filter.jumpDetected());
	}

}

class App {

	private static final int BAUD_RATE = Integer.parseInt(System.getProperty("baud", "115200"));

	private Scanner input;
	private SerialPort port;
	private StringBuffer incomingLine = new StringBuffer();
	private static App app = new App();
	public static boolean any = true;

	public static void main(String... args) {
		try {
			app.init();
		} catch (Exception e) {
			System.out.println("Error during initialization: " + e.getMessage());
			return;
		}

	}

	private void init() {
		input = new Scanner(System.in);
		String portName = choosePort();
		if (portName == "None") {
			closePort();
			any = false;
		} else {
			port = openPort(portName);
			setClosePortHook();
			addReceiveHandler();
		}
	}

	public static void closePort() {
		try {
			if (app.port != null && app.port.isOpened()) {
				System.out.println("Closing port...");
				app.port.closePort();
			}
		} catch (SerialPortException e) {
			throw new RuntimeException(e);
		}
	}

	private String choosePort() {
		int choice;
		String[] ports = SerialPortList.getPortNames();
		if (ports.length >= 1) {
			for (int i = 0; i < ports.length; i++) {
				System.out.printf("%d) %s%n", i + 1, ports[i]);
			}
		}
		System.out.println((ports.length + 1) + ") File");
		choice = input.nextInt();
		if (choice == ports.length + 1) {
			return "None";
		} else {
			if (choice < 1 || choice > ports.length) {
				throw new RuntimeException("Choose better next time");
			}
			return ports[choice - 1];
		}
	}

	private SerialPort openPort(String name) {
		SerialPort port = new SerialPort(name);
		try {
			if (!port.openPort()) {
				throw new RuntimeException("Port opening returns 'false'");
			}
			if (!port.setParams(BAUD_RATE, 8, 1, 0)) {
				throw new RuntimeException("Setting port params returns 'false'");
			}
		} catch (SerialPortException e) {
			throw new RuntimeException("Oh, what a boolsheet: " + e);
		}
		return port;
	}

	private void addReceiveHandler() {
		try {
			port.addEventListener(event -> {
				try {
					if (event.getEventType() != SerialPortEvent.RXCHAR) {
						return;
					}
					receiveBytes(port.readBytes());
				} catch (SerialPortException e) {
					throw new RuntimeException("Exception on receiving: " + e);
				}
			});
		} catch (SerialPortException e) {
			throw new RuntimeException("Oh no, so much boolsheet: " + e);
		}
	}

	private void setClosePortHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			closePort();
		}));
	}

	private void receiveBytes(byte[] b) {
		if (b != null) {
			String s = new String(b);
			incomingLine.append(s);
			int lineEndPos = incomingLine.indexOf("\n");
			if (lineEndPos >= 0) {
				String str = incomingLine.substring(0, lineEndPos);
				Scanner line = new Scanner(str);
				try {
					Data.filter.newPoint(line.nextInt(), line.nextInt(), line.nextInt());
					Chart.receivedPoints += 1;
					if (Chart.receivedPoints == 10) {
					    Data.filter.calibrate();
					}
					Data.nextPoint();
				} catch (Exception e) {
					// broken line, do nothing about it
				}
				line.close();
				incomingLine.delete(0, lineEndPos + 1);
			}
		}
	}
}
